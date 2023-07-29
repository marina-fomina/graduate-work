package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
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
        return ResponseEntity.ok(comments);
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
    public ResponseEntity<CommentDTO> updateComment(Authentication authentication,
                                                    @PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        String username = authentication.getName();
        CommentDTO commentDTO = commentService.updateComment(username, adId, commentId, createOrUpdateCommentDTO);
        if (Objects.nonNull(commentDTO)) {
            return ResponseEntity.ok(commentDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
