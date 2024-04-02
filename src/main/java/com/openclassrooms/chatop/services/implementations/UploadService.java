package com.openclassrooms.chatop.services.implementations;

import com.openclassrooms.chatop.controller.handler.exception.InvalidFileFormatException;
import com.openclassrooms.chatop.services.IUploadService;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class UploadService implements IUploadService {

    private final String[] ALLOWED_CONTENT_TYPE = { "image/png", "image/jpeg" };


    public String uploadFile(MultipartFile file) throws IOException {
        if(Arrays.stream(ALLOWED_CONTENT_TYPE).noneMatch(contentType -> contentType.equals(file.getContentType()))) {
            throw new InvalidFileFormatException("Le format de fichier \"" + file.getContentType() + "\" n'est pas accepté");
        }

        String uploadPath = System.getProperty("user.dir")+"\\upload\\";
        Path filePath = Paths.get(uploadPath + file.getOriginalFilename());

        File directory = new File(uploadPath);
        File existingFile = new File(filePath.toString());

        if(!directory.exists()){
            new File(uploadPath).mkdirs();
        }
        else if(existingFile.exists()){
            existingFile.delete();
        }

        byte[] fileBytes = file.getBytes();
        Files.write(filePath, fileBytes);

        return file.getOriginalFilename();

    }

    public ByteArrayResource getFileByteByName(String file) throws IOException {
            String uploadPath = System.getProperty("user.dir")+"\\upload\\";
            Path filePath = Paths.get(uploadPath + file);
            if(!Files.exists(filePath))
                throw new FileNotFoundException("Le fichier recherché n'a pas été trouvé");
            return new ByteArrayResource(Files.readAllBytes(filePath));
    }

}
