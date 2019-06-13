package fileservice.service;

import fileservice.model.dto.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class FileStorageService {



    private Path fileStorageLocation;

    public Resource loadAsResource(String filename) {


        return null;

    }

    public FileUploadResponse storeFile(MultipartFile file, long userId){

        byte[] fileBytes = null;
        try {
             fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new FileUploadResponse();
        }

        return new FileUploadResponse();


    }
}
