package com.psicovirtual.community.service.bucket;

import com.psicovirtual.community.exception.CommunityException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Set;
import java.util.UUID;

public interface IBucketOperations {

    Set<File> download(Set<String> keys) throws CommunityException;

    Set<String> upload(Set<MultipartFile> file, String uuid) throws CommunityException;

    void delete(Set<String> key) throws CommunityException;

    boolean isBucketExists(String bucketName);


}
