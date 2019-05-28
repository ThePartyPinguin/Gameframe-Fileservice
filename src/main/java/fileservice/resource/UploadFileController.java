package fileservice.resource;

import fileservice.model.dto.FileUploadResponse;
import fileservice.model.entity.FileType;
import fileservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
public class UploadFileController {

    private final FileStorageService storageService;

    public UploadFileController(@Autowired FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/profilepicture")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestHeader("X-user-id") String userId, HttpServletRequest request){

        return this.storageService.storeFile(request, file, Long.parseLong(userId), FileType.PROFILE_PICTURE);

    }
}
