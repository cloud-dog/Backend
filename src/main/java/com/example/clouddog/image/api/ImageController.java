package com.example.clouddog.image.api;

import com.example.clouddog.image.api.dto.response.ImageResDto;
import com.example.clouddog.image.application.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/images", consumes = "multipart/form-data")
    public ResponseEntity<ImageResDto> imageUpload(@RequestPart("image") MultipartFile image) {
        ImageResDto upload = imageService.upload(image);

        return new ResponseEntity<>(upload, HttpStatus.OK);
    }
}
