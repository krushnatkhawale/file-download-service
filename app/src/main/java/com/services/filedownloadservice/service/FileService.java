package com.services.filedownloadservice.service;

import com.services.filedownloadservice.model.FileResource;
import com.services.filedownloadservice.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileResource> getFiles() {
        return fileRepository.getFiles();
    }
}
