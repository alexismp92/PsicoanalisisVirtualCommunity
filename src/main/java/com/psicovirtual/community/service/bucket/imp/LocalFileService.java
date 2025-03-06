package com.psicovirtual.community.service.bucket.imp;

import com.psicovirtual.community.exception.CommunityException;
import com.psicovirtual.community.service.bucket.IBucketOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static com.psicovirtual.community.utils.Constants.JAVA_IO_TMPDIR;


@Service
@Slf4j
@Profile({"local"})
public class LocalFileService implements IBucketOperations {

    @Override
    public Set<File> download(Set<String> keys) {
        log.info("download files " + keys.toString());
        var files = new HashSet<File>();

        for(var key : keys){
            var path = Paths.get(key);
            var file = path.toFile();
            if (file.exists() && file.isFile()){
                files.add(file);
                log.info("file downloaded " + file);
            }
        }

        return files;
    }

    @Override
    public Set<String> upload(Set<MultipartFile> files, String uuid) throws CommunityException {
        var keys = new HashSet<String>();
        log.info("upload files " + files.toString());

        var tmpFolder = System.getProperty(JAVA_IO_TMPDIR);

        try {
            for(var file : files){
                Path dirPath = Path.of(tmpFolder, uuid);
                Files.createDirectories(dirPath);
                Path filePath = dirPath.resolve(file.getOriginalFilename());
                Files.createFile(filePath);
                file.transferTo(filePath);
                log.info("file uploaded " + filePath);
                keys.add(filePath.toString());
            }
        } catch (IOException e) {
            throw new CommunityException("Unable to upload the file. Exception: " + e.getMessage());
        }

        return keys;
    }

    @Override
    public void delete(Set<String> keys) throws CommunityException {
        log.info("deleting files " + keys.toString());
        for (String key : keys) {
            try {
                Files.deleteIfExists(Path.of(key));
                log.info("file deleted " + key);
            } catch (IOException e) {
                throw new CommunityException("Unable to delete the file. Exception: " + e.getMessage());
            }
        }

    }

    @Override
    public boolean isBucketExists(String bucketName) {
        return true;
    }
}
