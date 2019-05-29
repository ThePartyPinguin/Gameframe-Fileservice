package fileservice.resource;

import fileservice.service.InternalFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalFileController {

    private final InternalFileStorageService fileStorageService;

    public InternalFileController(InternalFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/profilepicture/{userId}")
    public String getUserProfilePicture(@PathVariable String userId){
        long userIdLong = Long.parseLong(userId);

        return this.fileStorageService.getProfilePictureUrlByUserId(userIdLong);
    }


}
