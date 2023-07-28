package ru.skypro.homework.utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentMapping {
    private static final String imagePrefix = "/ads/image?id=";

    /**
     * Mapping from CreateOrUpdateCommentDTO to Comment
     *
     * @param createOrUpdateCommentDTO instance of {@link CreateOrUpdateCommentDTO} class
     * @return instance of {@link Comment} class
     */
    public Comment mapToCommentFromCreateOrUpdateCommentDTO(CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment comment = new Comment();
        comment.setText(createOrUpdateCommentDTO.getText());
        return comment;
    }

    /**
     * Mapping from Comment to CommentDTO
     *
     * @param comment instance of {@link Comment} class
     * @return instance of {@link CommentDTO} class
     */
    public CommentDTO mapCommentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(comment.getAuthor().getId());
        if (comment.getAuthor().getImage() != null && !comment.getAuthor().getImage().isBlank()) {
            commentDTO.setAuthorImage(imagePrefix + comment.getAuthor().getImage());
        }
        commentDTO.setAuthorFirstName(comment.getAuthor().getFirstName());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setPk(comment.getId());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }

    /**
     * Mapping from list of comments to CommentsDTO
     *
     * @param comments list of ad comments
     * @return instance of {@link CommentsDTO} class with numbers of comments and list of them (ArrayList)
     */
    public CommentsDTO mapToCommentsDTO(List<Comment> comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setResults(comments.stream().map(this::mapCommentToCommentDTO).collect(Collectors.toList()));
        commentsDTO.setCount(comments.size());
        return commentsDTO;
    }
}
