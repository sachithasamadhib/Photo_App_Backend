package com.Photo_App.Photo_App.Repository;

import com.Photo_App.Photo_App.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByImageId(Long imageId);
}
