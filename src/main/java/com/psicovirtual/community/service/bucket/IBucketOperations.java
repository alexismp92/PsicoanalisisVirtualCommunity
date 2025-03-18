package com.psicovirtual.community.service.bucket;

import com.psicovirtual.community.exception.CommunityException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static com.psicovirtual.community.utils.Constants.JAVA_IO_TMPDIR;

public interface IBucketOperations {

    /**
     * Download files from the bucket
     *
     * @param keys
     * @return
     * @throws CommunityException
     */
    Set<File> download(Set<String> keys) throws CommunityException;

    /**
     * Upload files to the bucket
     *
     * @param file
     * @param uuid
     * @return
     * @throws CommunityException
     */
    Set<String> upload(Set<MultipartFile> file, String uuid) throws CommunityException;

    /**
     * Delete files from the bucket
     *
     * @param keys
     * @throws CommunityException
     */
    void delete(Set<String> keys) throws CommunityException;

    /**
     * Check if the bucket exists
     *
     * @param bucketName
     * @return
     */
    boolean isBucketExists(String bucketName);

    /**
     * Generate a temporary file
     *
     * @param file
     * @param uuid
     * @return
     * @throws IOException
     */
    default Path generateTmpFile(MultipartFile file, String uuid) throws IOException {
        var tmpFolder = System.getProperty(JAVA_IO_TMPDIR);
        Path dirPath = Path.of(tmpFolder, uuid);
        Files.createDirectories(dirPath);
        Path filePath = dirPath.resolve(file.getOriginalFilename());
        Files.createFile(filePath);
        file.transferTo(filePath);
        return filePath;
    }

}
