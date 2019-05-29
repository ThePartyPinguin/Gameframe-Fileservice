package fileservice.service;

import fileservice.model.dto.FileUploadResponse;
import fileservice.model.entity.FileType;
import fileservice.model.entity.SavedFile;
import fileservice.repository.LocalFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public class FileStorageService {

    @Value("${files.store.local.path}")
    private String localBaseFileStorePath;

    @Value("${files.download.host.url}")
    private String currentHostUrl;

    @Value("${files.download.host.url.prefix}")
    private String currentHostUrlPrefix;

    @Value("${files.download.url.suffix}")
    private String downloadSuffix;

    private final LocalFileStorage profilePictureStorage;

    private Logger logger;

    public FileStorageService(@Autowired LocalFileStorage profilePictureStorage) {
        this.profilePictureStorage = profilePictureStorage;
        this.logger = LoggerFactory.getLogger(FileStorageService.class.getName());
    }

    public Resource loadAsResource(FileType fileType, long imageId, long requestorUserId) {

        return this.profilePictureStorage.loadFileAsResource(imageId, requestorUserId);
    }

    public FileUploadResponse storeFile(HttpServletRequest request, MultipartFile file, long userId, FileType fileType){

        String downloadUrl = this.currentHostUrlPrefix +
                this.currentHostUrl +
                this.downloadSuffix +
                fileType.ordinal();

        SavedFile sf = this.profilePictureStorage.storeFile(file, downloadUrl, userId, fileType);

        if(sf == null)
            return null;

        return new FileUploadResponse(sf.getId(), sf.getDownloadUrl());
    }


}
