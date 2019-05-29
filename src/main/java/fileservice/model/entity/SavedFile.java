package fileservice.model.entity;


import javax.persistence.*;
@Entity
@Table(name = "SAVED_FILES")
public class SavedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private long id;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Column(name = "DOWNLOAD_URL", nullable = false)
    private String downloadUrl;

    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "FILE_TYPE", columnDefinition = "smallint")
    private int fileType;

    public SavedFile(String fileName, boolean isPublic, long userId, FileType fileType) {
        this.fileName = fileName;
        this.isPublic = isPublic;
        this.userId = userId;
        this.fileType = fileType.ordinal();
    }

    public SavedFile() {
    }

    public long getId() {
        return id;
    }


    public boolean isPublic() {
        return isPublic;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getUserId() {
        return userId;
    }

    public String formatLocalStoreDirectoryPath(String localBaseDirectory, String locationSuffix){
        return localBaseDirectory + locationSuffix + '\\' + userId + '\\';
    }

    public FileType getFileType() {
        return FileType.values()[this.fileType];
    }
}
