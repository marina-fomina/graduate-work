package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.NoSuchAdException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.CommentMapping;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    AdRepository adRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    CommentMapping commentMapping;

    @Override
    public CommentsDTO getComments(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(NoSuchAdException::new);
        List<Comment> list = ad.getComments();
        return commentMapping.mapToCommentsDTO(list);
    }

    @Override
    public CreateOrUpdateCommentDTO addComment(Integer id, CreateOrUpdateCommentDTO commentDTO) {
        Optional<Ad> ad = adRepository.findById(id);
        Comment comment = commentMapping.mapToCommentFromCreateOrUpdateCommentDTO(commentDTO);
        comment.setCreatedAt(Instant.now().toEpochMilli());
        if (ad.isPresent()) {
            comment.setAd(ad.get());
            comment.setAuthor(userService.getUser().orElseThrow());
            commentRepository.save(comment);
            return new CreateOrUpdateCommentDTO(comment.getText());
        }
        return null;
    }

    @Override
    public boolean deleteComment(String username, Integer adId, Integer commentId) {
        User user = userRepository.getUserByUsername(username);
        if (ifExists(adId, commentId) && Objects.nonNull(user)) {
            if (isAuthor(username, commentId) || user.getRole().equals(Role.ADMIN)) {
                commentRepository.deleteById(commentId);
                return true;
            }
        }
        return false;
    }

    @Override
    public CommentDTO updateComment(String username, Integer adId, Integer commentId,
                                    CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        User user = userRepository.getUserByUsername(username);
        Optional<Ad> ad = adRepository.findById(adId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (ad.isPresent() && comment.isPresent()) {
            if (isAuthor(user.getUsername(), commentId) || user.getRole().equals(Role.ADMIN)) {
                comment.get().setText(createOrUpdateCommentDTO.getText());
                commentRepository.save(comment.get());
                return commentMapping.mapCommentToCommentDTO(comment.get());
            }
        }
        return null;
    }

    private boolean ifExists(Integer adId, Integer commentId) {
        return adRepository.existsById(adId) && commentRepository.existsById(commentId);
    }

    private boolean isAuthor(String username, Integer commentId) {
        return commentRepository.getCommentById(commentId).getAuthor().getId().equals(userRepository.getUserByUsername(username).getId());
    }

}
