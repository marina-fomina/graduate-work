package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.Optional;

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
//        Optional<CommentsDTO> commentsDTO = Optional.ofNullable(commentService.getComments(id));
//        return extendedAdDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        // добавить сценарий для ошибки 404
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CreateOrUpdateCommentDTO> addComment(@PathVariable Integer id,
                                                               @RequestBody CreateOrUpdateCommentDTO comment) {
        System.out.println("START");
        commentService.addComment(id, comment);
        return ResponseEntity.ok().build();
        // добавить сценарий для ошибки 404
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        if (commentService.deleteComment(adId, commentId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CreateOrUpdateCommentDTO> updateComment(@PathVariable Integer adId,
                                                                  @PathVariable Integer commentId,
                                                                  @RequestBody CreateOrUpdateCommentDTO comment) {
        commentService.updateComment(adId, commentId, comment.getText());
        return ResponseEntity.ok().build();
        // добавить сценарий для ошибки 403
    }
}
