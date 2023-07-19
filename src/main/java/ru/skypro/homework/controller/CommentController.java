package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.Objects;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class CommentController {
    CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Integer id) {
        CommentsDTO comments = commentService.getComments(id);
        return Objects.nonNull(comments) ? ResponseEntity.ok(comments) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CreateOrUpdateCommentDTO> addComment(@PathVariable Integer id,
                                                               @RequestBody CreateOrUpdateCommentDTO comment) {
        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = commentService.addComment(id, comment);
        return Objects.nonNull(createOrUpdateCommentDTO) ? ResponseEntity.ok(createOrUpdateCommentDTO) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(Authentication authentication,
                                                 @PathVariable Integer adId,
                                                 @PathVariable Integer commentId) {
        String username = authentication.getName();
        if (commentService.deleteComment(username, adId, commentId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId,
                                                 @PathVariable Integer commentId,
                                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment comment = commentService.updateComment(adId, commentId, createOrUpdateCommentDTO);
        if (Objects.nonNull(comment)) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
