package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;

public interface CommentService {

    /**
     * Get all comments of the ad
     *
     * @param id ad primary key
     * @return instance of {@link CommentsDTO} class that includes the number of comments and their list
     */
    CommentsDTO getComments(Integer id);

    /**
     * Add comment
     *
     * @param id         ad primary key
     * @param commentDTO contains information about the author of the comment and its text
     * @return text of comment
     */
    CreateOrUpdateCommentDTO addComment(Integer id, CreateOrUpdateCommentDTO commentDTO);

    /**
     * Delete comment
     *
     * @param username  username of comment author
     * @param adId      ad primary key
     * @param commentId comment primary key
     * @return true (if comment was found and deleted) or false (if not)
     */

    boolean deleteComment(String username, Integer adId, Integer commentId);

    /**
     * Update comment
     *
     * @param username                 username of comment author
     * @param adId                     ad primary key
     * @param commentId                comment primary key
     * @param createOrUpdateCommentDTO new text of comment
     * @return instance of {@link CommentDTO} class that includes author id, author image link, author first name,
     * time of comment creation, comment id and its text
     */

    CommentDTO updateComment(String username, Integer adId, Integer commentId,
                             CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}
