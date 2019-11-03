package com.services.filedownloadservice.controller;

import com.services.filedownloadservice.model.FileResource;
import com.services.filedownloadservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files/{id}")
    public ResponseEntity getFiles(@PathVariable String id, HttpServletRequest request) {
        log.info("Get a Files: {}", id);
        Resource resource = fileService.getFile(id);
        if (isNull(resource)) {
            return ResponseEntity.notFound().build();
        } else {
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                log.info("Could not determine resource type.");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
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