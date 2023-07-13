package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.NoSuchAdException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.AdMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    AdRepository adRepository;
    UserRepository userRepository;

    AdMapping adMapping;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentsDTO getComments(Integer id) {
        AdEntity ad = adRepository.findById(id).orElseThrow(NoSuchAdException::new);
        List<Comment> allByAd = commentRepository.findAllByAd(ad);
        return mapToCommentsDTO(allByAd);
    }

    @Override
    public CreateOrUpdateCommentDTO addComment(Integer id, CreateOrUpdateCommentDTO commentDTO) {
        AdEntity ad = adRepository.findById(id).orElseThrow(NoSuchAdException::new);
        return ad.addComment(mapToCommentFromCreateOrUpdateCommentDTO(commentDTO));
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        if (!adRepository.existsById(adId)) {
            return false;
        } else if (!commentRepository.existsById(commentId)) {
            return false;
        } else {
            commentRepository.deleteById(commentId);
            return true;
        }
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Optional<AdEntity> ad = adRepository.findById(adId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        ExtendedAdDTO adDto = ad.map(a -> adMapping.mapEntityToExtendedAdDto(a)).orElse(null);
        CreateOrUpdateCommentDTO commentDTO = comment.map(c -> mapToCreateOrUpdateCommentDTO(c)).orElse(null);
        if (Objects.nonNull(adDto) && Objects.nonNull(commentDTO)) {
            commentDTO.setText(createOrUpdateCommentDTO.getText());
            return commentRepository.save(mapToCommentFromCreateOrUpdateCommentDTO(commentDTO));
        }
        return null;

//        if (!adRepository.existsById(adId)) {
//            throw new NoSuchAdException();
//        } else if (!commentRepository.existsById(commentId)) {
//            throw new NoSuchCommentException();
//        }
//        Comment comment = commentRepository.getCommentById(commentId);
//        comment.setText(textOfNewComment);
//        commentRepository.save(comment);
//        return comment;
    }


    private Comment mapToCommentFromCommentDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        User author= userRepository.findById(commentDTO.getAuthor().longValue()).orElse(null);
        comment.setAuthor(author);
        comment.setText(commentDTO.getText());
        comment.setId(commentDTO.getPk());
        return comment;
    }

    private Comment mapToCommentFromCreateOrUpdateCommentDTO(CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment comment = new Comment();
        comment.setText(createOrUpdateCommentDTO.getText());
        return comment;
    }

    private CommentsDTO mapToCommentsDTO(List<Comment> comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setComments(comments);
        commentsDTO.setCount(comments.size());
        return commentsDTO;
    }

    private CreateOrUpdateCommentDTO mapToCreateOrUpdateCommentDTO(Comment comment) {
        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText(comment.getText());
        return createOrUpdateCommentDTO;
    }

}
