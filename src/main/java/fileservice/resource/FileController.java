package fileservice.resource;

import fileservice.model.dto.FileUploadResponse;
import fileservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/public")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> requestFile (@PathVariable String filename){
        Resource file = this.storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }

    @PostMapping
    public FileUploadResponse uploadFile(@RequestParam("file")MultipartFile file, @RequestHeader("X-user-id") String userId){
        return this.storageService.storeFile(file, Long.parseLong(userId));
    }
}
