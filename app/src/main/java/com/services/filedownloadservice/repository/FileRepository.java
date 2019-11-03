package com.services.filedownloadservice.repository;

import com.services.filedownloadservice.model.FileResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Slf4j
@Repository
public class FileRepository {

    private FileSystemResource fileSystemResource;
    private final File file;

    public FileRepository(@Value("${storagePath}") String storagePath) {
        log.info("File storage path: {}", storagePath);
        fileSystemResource = new FileSystemResource(storagePath);
        file = fileSystemResource.getFile();
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
