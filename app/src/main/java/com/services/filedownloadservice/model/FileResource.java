package com.services.filedownloadservice.model;

public class FileResource {
    private String name;

    public FileResource(String path) {
        this.name = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
