package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.NoSuchAdException;
import ru.skypro.homework.exception.NoSuchCommentException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    AdRepository adRepository;
    UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentsDTO getComments(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(NoSuchAdException::new);
        List<Comment> allByAd = commentRepository.findAllByAd(ad);
        return mapToCommentsDTO(allByAd);
    }

    @Override
    public CreateOrUpdateCommentDTO addComment(Integer id, CreateOrUpdateCommentDTO commentDTO) {
        Ad ad = adRepository.findById(id).orElseThrow(NoSuchAdException::new);
        return ad.addComment(mapToCommentFromCreateOrUpdateCommentDTO(commentDTO));
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        if (!adRepository.existsById(adId)) {
            throw new NoSuchAdException(); // или return false?
        } else if (!commentRepository.existsById(Long.valueOf(commentId))) {
            throw new NoSuchCommentException(); // или return false?
        }
        commentRepository.deleteById(Long.valueOf(commentId));
        return true;
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, String textOfNewComment) {
        if (!adRepository.existsById(adId)) {
            throw new NoSuchAdException();
        } else if (!commentRepository.existsById(Long.valueOf(commentId))) {
            throw new NoSuchCommentException();
        }
        Comment comment = commentRepository.getCommentById(commentId);
        comment.setText(textOfNewComment);
        return comment;
    }


    @Override
    public Comment mapToCommentFromCommentDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        User author= userRepository.findById(commentDTO.getAuthor().longValue()).orElse(null);
        comment.setAuthor(author);
        comment.setText(commentDTO.getText());
        comment.setId(commentDTO.getPk());
        return comment;
    }

    private Comment mapToCommentFromCreateOrUpdateCommentDTO(CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        // я так понимаю здесь нужно сначала выстроить связь между юзером и объявлением
        // по переданному id найти объявление, потом у этого объявления получить юзера
        // если у этого юзера есть коммент тогда мы его обновляем, а если нет - то создаем новый? Но тогда
        // получается что мы можем только 1 коммент добавлять к обеъявлению?
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


}
