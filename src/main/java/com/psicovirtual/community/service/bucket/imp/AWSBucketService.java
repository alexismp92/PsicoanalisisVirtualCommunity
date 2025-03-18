package com.psicovirtual.community.service.bucket.imp;

import com.psicovirtual.community.component.S3Properties;
import com.psicovirtual.community.exception.CommunityException;
import com.psicovirtual.community.service.bucket.IBucketOperations;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import static com.psicovirtual.community.utils.Constants.*;
import static com.psicovirtual.community.utils.Utils.getExtension;


@Service
@AllArgsConstructor
@Slf4j
@Profile("!local")
public class AWSBucketService implements IBucketOperations {

    private final S3Properties s3Properties;
    private final S3Client s3Client;

    @Override
    public Set<File> download(Set<String> keys) throws CommunityException {
        Set<File> downloadedFiles = new HashSet<>();
        try{
            isBucketExists(s3Properties.getBucketName());

            for (String key : keys) {
                final var extension = getExtension(key);

                var getObjectResponse = s3Client.getObject(GetObjectRequest.builder()
                        .bucket(s3Properties.getBucketName())
                        .key(key)
                        .build());

                var file = Files.createTempFile(S3, String.join(DOT,extension));
                Files.copy(getObjectResponse, file, StandardCopyOption.REPLACE_EXISTING);
                log.info("File downloaded to: " + file);
                downloadedFiles.add(file.toFile());
            }

        }catch (IOException | S3Exception e){
            log.error("Download failed: " + e.getMessage());
            throw new CommunityException(e.getMessage());
        }
        return downloadedFiles;
    }

    @Override
    public Set<String> upload(Set<MultipartFile> files, String uuid) throws CommunityException {
        Set<String> uploadedFiles = new HashSet<>();
        try{
            isBucketExists(s3Properties.getBucketName());
            for (MultipartFile file : files) {
                    log.info("Uploading file: " + file.getOriginalFilename());
                    Path filePath = generateTmpFile(file, uuid);
                    final var s3Key = String.join(SLASH, uuid,file.getOriginalFilename());
                    s3Client.putObject(PutObjectRequest.builder()
                                    .bucket(s3Properties.getBucketName())
                                    .key(s3Key)
                                    .serverSideEncryption(ServerSideEncryption.AWS_KMS)
                                    .ssekmsKeyId(s3Properties.getKmsKey())
                                    .build(),filePath);
                    log.info("File uploaded: " + s3Key);
                    uploadedFiles.add(s3Key);
            }
        } catch (IOException | S3Exception e) {
            log.error("Upload failed: " + e.getMessage());
            throw new CommunityException(e.getMessage());
        }
        return uploadedFiles;
    }

    @Override
    public void delete(Set<String> keys) throws CommunityException {
        try {
            isBucketExists(s3Properties.getBucketName());

            for (String key : keys) {
                s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(s3Properties.getBucketName())
                        .key(key)
                        .build());
                log.info("File deleted: " + key);
            }
        } catch (S3Exception e) {
            log.error("Delete failed: " + e.getMessage());
            throw new CommunityException(e.getMessage());
        }
    }

    @Override
    public boolean isBucketExists(String bucketName) throws S3Exception{
        try {
            log.info("Checking if bucket exists: " + bucketName);
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
            return true;
        } catch (S3Exception e) {
            log.error("Bucket does not exist: " + e.getMessage());
            throw e;
        }
    }
}
