package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.reposoitory.CommentRepository;

public class CommentServiceImpl {
    CommentRepository commentRepository;
    AdRepository adRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentsDTO getComments(Integer id) {
        Ad ad = adRepository.findById(id);
        commentRepository.findAllByAd(ad);

        return null;
    }

    private Comment mapToCommentFromCommentDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();

//        comment.setAuthor();
        return null;
    }
}
