package fileservice.service;

import fileservice.model.entity.FileType;
import fileservice.model.entity.SavedFile;
import fileservice.repository.jpa.IFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalFileStorageService {

    private final IFileRepository fileRepository;

    public InternalFileStorageService(IFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public String getProfilePictureUrlByUserId(long userId){
        System.out.println(userId);
        SavedFile file = this.fileRepository.findFirstByUserIdAndFileType(userId, 0).orElse(null);
        System.out.println(file);
        if(file != null)
        {
            return file.getDownloadUrl();
        }
        return null;
    }

}
