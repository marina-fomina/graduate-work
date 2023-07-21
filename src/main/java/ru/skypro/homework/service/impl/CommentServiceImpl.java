package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.NoSuchAdException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.AdMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    @Autowired
    AdRepository adRepository;
    @Autowired
    UserRepository userRepository;

    AdMapping adMapping;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentsDTO getComments(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(NoSuchAdException::new);
        List<Comment> list = ad.getComments();
        return mapToCommentsDTO(list);
    }

    @Override
    public CreateOrUpdateCommentDTO addComment(Integer id, CreateOrUpdateCommentDTO commentDTO) {
        Optional<Ad> ad =  adRepository.findById(id);
        Comment comment = mapToCommentFromCreateOrUpdateCommentDTO(commentDTO);
        if(ad.isPresent()) {
            comment.setAd(ad.get());
            // TODO: переписать под пользователя
            comment.setAuthor(userRepository.getUserById(1));
            commentRepository.save(comment);
            return new CreateOrUpdateCommentDTO(comment.getText());
        }
        return null;
    }

    // Метод для AdminController
    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        if (!adRepository.existsById(adId) || !commentRepository.existsById(commentId)) {
            return false;
        } else {
            commentRepository.deleteById(commentId);
            return true;
        }
    }

    // Метод для CommentController
    @Override
    public boolean deleteComment(String username, Integer adId, Integer commentId) {
        if (!ifExists(adId, commentId) && !isAuthor(username, commentId)) {
            return false;
        } else {
            commentRepository.deleteById(commentId);
            return true;
        }
    }


    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Optional<Ad> ad = adRepository.findById(adId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        AdDTO adDTO = ad.map(a -> adMapping.mapEntityToAdDto(a)).orElse(null);
        CreateOrUpdateCommentDTO commentDTO = comment.map(c -> mapToCreateOrUpdateCommentDTO(c)).orElse(null);

        if (Objects.nonNull(adDTO) && Objects.nonNull(commentDTO)) {
            commentDTO.setText(createOrUpdateCommentDTO.getText());
            return commentRepository.save(mapToCommentFromCreateOrUpdateCommentDTO(commentDTO));
        }
        return null;
    }

    @Override
    public Comment updateComment(String username, Integer adId, Integer commentId,
                                 CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        if (ifExists(adId, commentId) && isAuthor(username, commentId)) {
            updateComment(adId, commentId, createOrUpdateCommentDTO);
        }
        return null;
    }

    private boolean ifExists(Integer adId, Integer commentId) {
        return adRepository.existsById(adId) && commentRepository.existsById(commentId);
    }

    private boolean isAuthor(String username, Integer commentId) {
        return commentRepository.getCommentById(commentId).getAuthor().getId().equals(userRepository.getUserByUsername(username).getId());
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

    private CommentDTO mapCommentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(commentDTO.getAuthor());
        commentDTO.setAuthorImage(comment.getAuthor().getImage());
        commentDTO.setAuthorFirstName(comment.getAuthor().getFirstName());
        // TODO: дописать дату/время
        commentDTO.setCreatedAt(1L);
        commentDTO.setPk(comment.getId());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }

    private CommentsDTO mapToCommentsDTO(List<Comment> comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setComments(comments.stream().map(this::mapCommentToCommentDTO).collect(Collectors.toList()));
        commentsDTO.setCount(comments.size());
        return commentsDTO;
    }

    private CreateOrUpdateCommentDTO mapToCreateOrUpdateCommentDTO(Comment comment) {
        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText(comment.getText());
        return createOrUpdateCommentDTO;
    }

}
