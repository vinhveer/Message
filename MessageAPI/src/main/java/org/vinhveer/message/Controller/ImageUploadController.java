package org.vinhveer.message.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.vinhveer.message.Services.CloudinaryService;

@RestController
public class ImageUploadController {

    private final CloudinaryService cloudinaryService;

    public ImageUploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("file") MultipartFile file) {
        String url = cloudinaryService.uploadFile(file);
        return ResponseEntity.ok(url);
    }
}
