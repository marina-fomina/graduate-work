package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;

public interface CommentService {
    /**
     * Get all comments on the ad
     *
     * @param id primary key of ad
     * @return the number of comments and their list
     */
    CommentsDTO getComments(Integer id);

    /**
     * Add comment
     *
     * @param id         primary key of ad
     * @param commentDTO contains information about the author of the comment and its text
     * @return text comment
     */
    CreateOrUpdateCommentDTO addComment(Integer id, CreateOrUpdateCommentDTO commentDTO);

    /**
     * Delete comment
     *
     * @param adId primary key of ad
     * @param commentId primary key of comment
     * @return true or false
     */
    boolean deleteComment(Integer adId, Integer commentId);

    /**
     * Update comment
     * @param adId primary key of ad
     * @param commentId primary key of comment
     * @param createOrUpdateCommentDTO comment text
     * @return
     */
    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}
