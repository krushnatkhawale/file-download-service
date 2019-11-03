package com.services.filedownloadservice.controller;

import com.services.filedownloadservice.model.FileResource;
import com.services.filedownloadservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files/{id}")
    public ResponseEntity getFiles(@PathVariable String id) {
        log.info("Get a Files: {}", id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/files")
    public ResponseEntity getFiles() {
        log.info("Get all universities");
        List<FileResource> files = fileService.getFiles();
        if (files.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(files);
        }
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity deleteFiles(@PathVariable String id) {
        log.info("Delete Files, id: {}", id);
        return ResponseEntity.ok().build();
    }
}