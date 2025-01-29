package com.Photo_App.Photo_App.Controller;

import com.Photo_App.Photo_App.Model.Image;
import com.Photo_App.Photo_App.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:5173")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("tags") List<String> tags,
            @RequestParam("username") String username
    ) {
        try {
            Image savedImage = imageService.saveImage(file, name, description, category, tags, username);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Could not upload the file: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Image>> getUserImages(@PathVariable Long userId) {
        return ResponseEntity.ok(imageService.getImagesByUserId(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Image>> searchImages(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String query
    ) {
        return ResponseEntity.ok(imageService.searchImages(category, query));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateImage(
            @PathVariable Long id,
            @RequestBody Image updatedImage
    ) {
        try {
            Image image = imageService.updateImage(id, updatedImage.getName(), updatedImage.getDescription(), updatedImage.getCategory(), updatedImage.getTags());
            return ResponseEntity.ok(image);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        try {
            imageService.deleteImage(id);
            return ResponseEntity.ok("Image deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeImage(@PathVariable Long id) {
        try {
            Image image = imageService.likeImage(id);
            return ResponseEntity.ok(image);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}