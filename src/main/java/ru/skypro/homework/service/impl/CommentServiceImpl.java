package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.NoSuchAdException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.AdMapping;

import java.time.Instant;
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
    @Autowired
    UserService userService;

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
        comment.setCreatedAt(Instant.now().toEpochMilli());
        if(ad.isPresent()) {
            comment.setAd(ad.get());
            comment.setAuthor(userService.getUser().orElseThrow());
            commentRepository.save(comment);
            return new CreateOrUpdateCommentDTO(comment.getText());
        }
        return null;
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
        Optional<Ad> ad = adRepository.findById(adId);
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
        comment.setAuthor(userService.getUser().orElseThrow());
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
        commentDTO.setAuthor(comment.getAuthor().getId());
        commentDTO.setAuthorImage(comment.getAuthor().getImage());
        commentDTO.setAuthorFirstName(comment.getAuthor().getFirstName());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setPk(comment.getId());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }

    private CommentsDTO mapToCommentsDTO(List<Comment> comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setResults(comments.stream().map(this::mapCommentToCommentDTO).collect(Collectors.toList()));
        commentsDTO.setCount(comments.size());
        return commentsDTO;
    }

    private CreateOrUpdateCommentDTO mapToCreateOrUpdateCommentDTO(Comment comment) {
        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText(comment.getText());
        return createOrUpdateCommentDTO;
    }

}
