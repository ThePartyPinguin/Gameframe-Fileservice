package fileservice.model.dto;

public class FileUploadResponse {

    private long imageId;

    private String imageName;

    public FileUploadResponse(long imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }

    public FileUploadResponse() {
    }

    public long getImageId() {
        return imageId;
    }

    public String getImageName() {
        return imageName;
    }
}
