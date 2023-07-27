package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {

    AdService adService;

    CommentService commentService;


//    @DeleteMapping("/{adId}/comments/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,
//                                              @PathVariable Integer commentId) {
//        if (commentService.deleteComment(adId, commentId)) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

//    @PatchMapping("/{adId}/comments/{commentId}")
//    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId,
//                                                 @PathVariable Integer commentId,
//                                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
//        Comment comment = commentService.updateComment(adId, commentId, createOrUpdateCommentDTO);
//        if (Objects.nonNull(comment)) {
//            return ResponseEntity.ok(comment);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
}