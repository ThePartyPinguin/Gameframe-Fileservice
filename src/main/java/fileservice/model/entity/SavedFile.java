package fileservice.model.entity;


import javax.persistence.*;

@Entity
public abstract class SavedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private long id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "LOCAL_FILEPATH")
    protected String localFilePath;

    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    public SavedFile(long id, String fileName, String localFilePath, boolean isPublic) {
        this.id = id;
        this.fileName = fileName;
        this.localFilePath = localFilePath;
        this.isPublic = isPublic;
    }

    public SavedFile() {
    }

    public long getId() {
        return id;
    }

    protected String getFileName() {
        return fileName;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public abstract String createAndSetFilePath(String fileName, String localBasePath);


}
