package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class CommentController {

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Integer id) {
//        Comments comments = commentService.getComments(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable Integer id, @RequestBody CreateOrUpdateComment comment) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Integer adId, @PathVariable Integer commentId,
                                              @RequestBody CreateOrUpdateComment comment) {
        return ResponseEntity.ok().build();
    }
}
