package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;

public interface CommentService {
    CommentsDTO getComments(Integer id);

    CreateOrUpdateCommentDTO addComment(Integer id, CreateOrUpdateCommentDTO commentDTO);

    boolean deleteComment(Integer adId, Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, String textOfNewComment);

    Comment mapToCommentFromCommentDTO(CommentDTO commentDTO);
}
