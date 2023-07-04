package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl {
    CommentRepository commentRepository;
    AdRepository adRepository;
    UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentsDTO getComments(Integer id) {
        AdEntity ad = adRepository.findById(id).orElseThrow(()-> new RuntimeException("A such ad not found"));
        List<Comment> allByAd = commentRepository.findAllByAd(ad);
        return mapToCommentsDTO(allByAd);
    }

    public void addComment(Integer id, CreateOrUpdateCommentDTO commentDTO) {
        AdEntity ad = adRepository.findById(id).orElseThrow(()-> new RuntimeException("A such ad not found"));
        ad.addComment(mapToCommentFromCreateOrUpdateCommentDTO(commentDTO));
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
