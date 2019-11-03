package com.services.filedownloadservice.repository;

import com.services.filedownloadservice.model.FileResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Slf4j
@Repository
public class FileRepository {

    private final File file;

    public FileRepository(@Value("${storagePath}") String storagePath) {
        log.info("File storage path: {}", storagePath);
        FileSystemResource fileSystemResource = new FileSystemResource(storagePath);
        file = fileSystemResource.getFile();
    }

    public Resource getFile(String fileName) {
        try {
            Path filePath = Paths.get(file.toURI()).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    public List<FileResource> getFiles() {
        return ofNullable(file)
                .map(File::listFiles)
                .map(Arrays::stream)
                .map(this::toFiles)
                .orElse(asList());
    }

    private List<FileResource> toFiles(Stream<File> file) {
        return file.map(this::toFileStorage)
                .collect(toList());
    }

    private FileResource toFileStorage(File file) {
        return new FileResource(file.getPath());
    }
}
