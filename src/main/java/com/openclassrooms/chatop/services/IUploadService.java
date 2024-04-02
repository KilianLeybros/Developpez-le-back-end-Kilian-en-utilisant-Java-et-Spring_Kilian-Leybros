package com.openclassrooms.chatop.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    String uploadFile(MultipartFile file) throws IOException;

    ByteArrayResource getFileByteByName(String file) throws IOException;
}
