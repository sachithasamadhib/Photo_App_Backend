package com.Photo_App.Photo_App.Service;

import com.Photo_App.Photo_App.Model.Image;
import com.Photo_App.Photo_App.Model.User;
import com.Photo_App.Photo_App.Repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserService userService;

    private final Path root = Paths.get("uploads");

    public Image saveImage(MultipartFile file, String name, String description, String category, List<String> tags, String username) throws IOException {
        User user = userService.findByUsername(username);
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.root.resolve(fileName));

        Image image = new Image();
        image.setName(name);
        image.setDescription(description);
        image.setCategory(category);
        image.setFilePath(fileName);
        image.setUploadDate(LocalDateTime.now());
        image.setUser(user);
        image.setTags(tags);

        return imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public List<Image> getImagesByUserId(Long userId) {
        return imageRepository.findByUserId(userId);
    }

    public List<Image> searchImages(String category, String query) {
        return imageRepository.searchImages(category, query);
    }

    public Image updateImage(Long id, String name, String description, String category, List<String> tags) {
        Image image = getImageById(id);
        image.setName(name);
        image.setDescription(description);
        image.setCategory(category);
        image.setTags(tags);
        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        Image image = getImageById(id);
        Path file = root.resolve(image.getFilePath());
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        imageRepository.deleteById(id);
    }

    public Image likeImage(Long id) {
        Image image = getImageById(id);
        image.setLikes(image.getLikes() + 1);
        return imageRepository.save(image);
    }
}
