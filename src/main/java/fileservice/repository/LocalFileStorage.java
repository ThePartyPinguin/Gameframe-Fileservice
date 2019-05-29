package fileservice.repository;

import fileservice.model.entity.FileType;
import fileservice.model.entity.SavedFile;
import fileservice.repository.jpa.IFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFileStorage {


    @Value("${files.store.local.path}")
    private String localStoreLocation;

    private final IFileRepository fileRepository;

    public LocalFileStorage(IFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }



    public Resource loadFileAsResource(long imageId, long requestorId){

        SavedFile fileToLoad = this.fileRepository.findById(imageId).orElse(null);

        if(fileToLoad == null)
            return null; // FIXME: 28/05/2019 Add proper return value

        if(!fileToLoad.isPublic() && fileToLoad.getUserId() != requestorId)
            return null; // FIXME: 28/05/2019 Add proper return value

        String fileDirectory = formatDirectoryPath(fileToLoad);

        if(fileDirectory.equals(""))
            return null;

        Path p = new File(fileDirectory + '\\' + fileToLoad.getFileName()).toPath().normalize();
        try {
            Resource resource = new UrlResource(p.toUri());
            if(resource.exists()){
                return resource;
            }
            else{
                throw new IOException("File does not exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SavedFile storeFile(MultipartFile file, String downloadUrl, long userId, FileType fileType){

        if(fileType == FileType.PROFILE_PICTURE){

            SavedFile sf = this.fileRepository.findFirstByUserIdAndFileType(userId, fileType.ordinal()).orElse(null);

            if(sf != null){
                removeFile(sf);
            }
        }

        SavedFile savedFile = new SavedFile(file.getOriginalFilename(), true, userId, fileType);

        byte[] fileBytes = null;

        try{
            fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(fileBytes == null)
            return null;

        String fileDirectory = formatDirectoryPath(savedFile);

        boolean fileSaved = this.saveFile(fileDirectory, file.getOriginalFilename(), fileBytes);

        if(!fileSaved){
            return null; //FIXME: 28/05/2019 return response with statuscode
        }

        savedFile.setDownloadUrl(downloadUrl + '/' + savedFile.getId() + '/' + userId);

        this.fileRepository.save(savedFile);

        return savedFile;
    }

    private boolean saveFile(String directoryLocation, String fileName, byte[] fileBytes){
        File directory = new File(directoryLocation);

        boolean directoriesCreated = false;

        BufferedOutputStream bufferedOutputStream = null;

        if(!directory.exists()){
            directoriesCreated = directory.mkdirs();
        }
        else{
            directoriesCreated = true;
        }

        if(!directoriesCreated){
            return false;
        }

        File file = new File(directoryLocation + '\\' + fileName);

        try{

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            bufferedOutputStream.write(fileBytes);

            return true;
        } catch (IOException e) {

            e.printStackTrace();

            return false;
        } finally {

            if(bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String formatDirectoryPath(SavedFile fileToLoad) {

        return this.localStoreLocation + '\\' + fileToLoad.getFileType() + "\\" + fileToLoad.getUserId() + "\\";

    }

    private void removeFile(SavedFile savedFile){
        try {
            File f = new File(formatDirectoryPath(savedFile) + savedFile.getFileName());

            if(f.exists())
                Files.delete(new File(formatDirectoryPath(savedFile) + savedFile.getFileName()).toPath());

            this.fileRepository.delete(savedFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
