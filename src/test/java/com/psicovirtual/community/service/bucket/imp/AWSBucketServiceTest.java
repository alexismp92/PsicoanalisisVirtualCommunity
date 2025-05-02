package com.psicovirtual.community.service.bucket.imp;

import com.psicovirtual.community.component.S3Properties;
import com.psicovirtual.community.exception.CommunityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AWSBucketServiceTest {

    @Mock
    private S3Properties s3Properties;
    @Mock
    private S3Client s3Client;
    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private AWSBucketService awsBucketService;

    @Test
    public void testDownloadSuccess() throws Exception {
        Set<String> keys = Set.of("logo.jpg");

        ResponseInputStream<GetObjectResponse> objectResponse = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(objectResponse);
        when(s3Properties.getBucketName()).thenReturn("test-bucket");

        Set<File> downloadedFiles = awsBucketService.download(keys);

        assertEquals(1, downloadedFiles.size());
        verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
    }

    @Test
    public void testDownloadS3Exception() {
        Set<String> keys = Set.of("logo.jpg");
        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(S3Exception.class);

        assertThrows(CommunityException.class, () -> awsBucketService.download(keys));
    }

    @Test
    public void testIsBucketExists() {
        assertTrue(awsBucketService.isBucketExists("test-bucket"));
    }

    @Test
    public void testIsBucketNotExists() {
        when(s3Client.headBucket(any(HeadBucketRequest.class))).thenThrow(S3Exception.class);

        assertThrows(S3Exception.class, () -> awsBucketService.isBucketExists("test-bucket"));
    }

    @Test
    public void testUploadSuccess() throws Exception {
        Set<MultipartFile> files = Set.of(multipartFile);
        when(multipartFile.getOriginalFilename()).thenReturn("test-file.txt");
        doNothing().when(multipartFile).transferTo(any(Path.class));

        when(s3Properties.getBucketName()).thenReturn("test-bucket");
        when(s3Properties.getKmsKey()).thenReturn("test-kms-key");

        Set<String> uploadedFiles = awsBucketService.upload(files, UUID.randomUUID().toString());

        assertEquals(1, uploadedFiles.size());
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(Path.class));
    }

    @Test
    public void testUploadS3Exception() {
        Set<MultipartFile> files = Set.of(multipartFile);
        when(multipartFile.getOriginalFilename()).thenReturn("test-fileEx.txt");
        when(s3Properties.getBucketName()).thenReturn("test-bucket");

        assertThrows(CommunityException.class, () -> awsBucketService.upload(files, "test-uuid"));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        Set<String> keys = Set.of("test-file.txt");
        when(s3Properties.getBucketName()).thenReturn("test-bucket");

        awsBucketService.delete(keys);

        verify(s3Client, times(1)).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    public void testDeleteS3Exception() {
        Set<String> keys = Set.of("test-file.txt");
        when(s3Properties.getBucketName()).thenReturn("test-bucket");
        doThrow(S3Exception.class).when(s3Client).deleteObject(any(DeleteObjectRequest.class));

        assertThrows(CommunityException.class, () -> awsBucketService.delete(keys));
    }
}