package com.Photo_App.Photo_App.Repository;

import com.Photo_App.Photo_App.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUserId(Long userId);

    @Query("SELECT i FROM Image i WHERE " +
            "(:category IS NULL OR i.category = :category) AND " +
            "(:query IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Image> searchImages(String category, String query);
}
