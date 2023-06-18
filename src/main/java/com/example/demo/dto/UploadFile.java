package com.example.demo.dto;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;
    private String storedFileName;

    public UploadFile(String uploadFileName, String storedFileName) {
        this.uploadFileName = uploadFileName;
        this.storedFileName = storedFileName;
    }
}
