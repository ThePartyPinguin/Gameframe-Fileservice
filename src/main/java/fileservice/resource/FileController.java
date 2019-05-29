package fileservice.resource;

import fileservice.model.dto.FileUploadResponse;
import fileservice.model.entity.FileType;
import fileservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

@RestController
public class FileController {

    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/download/{imageTypeOrdinal}/{requestorUserId}/{fileId}")
    @ResponseBody
    public ResponseEntity<Resource> requestFile (@PathVariable String imageTypeOrdinal, @PathVariable String requestorUserId, @Nullable @PathVariable String fileId){

        int typeOrdinal = Integer.parseInt(imageTypeOrdinal);
        FileType imageType = FileType.values()[typeOrdinal];

        long requestorId = Long.parseLong(requestorUserId);
        long imageIdLong = -1;

        if(fileId != null)
            imageIdLong = Long.parseLong(fileId);

        Resource file = this.storageService.loadAsResource(imageType, imageIdLong, requestorId);


        if(file == null)
            return null;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
