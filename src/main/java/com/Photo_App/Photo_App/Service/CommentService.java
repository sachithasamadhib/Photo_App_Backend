package com.Photo_App.Photo_App.Service;

import com.Photo_App.Photo_App.Model.Comment;
import com.Photo_App.Photo_App.Model.Image;
import com.Photo_App.Photo_App.Model.User;
import com.Photo_App.Photo_App.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    public Comment addComment(Long imageId, String content, String username) {
        Image image = imageService.getImageById(imageId);
        User user = userService.findByUsername(username);

        Comment comment = new Comment();
        comment.setImage(image);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByImageId(Long imageId) {
        return commentRepository.findByImageId(imageId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
