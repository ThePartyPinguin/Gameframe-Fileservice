package fileservice.model.entity.filetype;

import fileservice.model.entity.SavedFile;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "PROFILE_PICTURES")
public class ProfilePicture extends SavedFile {

    @Column(name = "USER_ID")
    private long userId;

    @Transient
    @Value("files.profile_image.suffix")
    private static String FILE_PATH_SUFFIX;


    @Override
    public String createAndSetFilePath(String fileName, String localBasePath) {

        this.localFilePath = localBasePath + FILE_PATH_SUFFIX + userId + '/' + fileName;

        return this.localFilePath;
    }
}
