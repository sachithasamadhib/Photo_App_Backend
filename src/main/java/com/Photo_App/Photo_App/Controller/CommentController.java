package com.Photo_App.Photo_App.Controller;

import com.Photo_App.Photo_App.Model.Comment;
import com.Photo_App.Photo_App.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{imageId}")
    public ResponseEntity<?> addComment(
            @PathVariable Long imageId,
            @RequestParam String content,
            @RequestParam String username
    ) {
        try {
            Comment comment = commentService.addComment(imageId, content, username);
            return ResponseEntity.ok(comment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<List<Comment>> getCommentsByImageId(@PathVariable Long imageId) {
        return ResponseEntity.ok(commentService.getCommentsByImageId(imageId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}