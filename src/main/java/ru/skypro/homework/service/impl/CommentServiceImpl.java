package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    @Autowired
    AdRepository adRepository;
    @Autowired
    UserRepository userRepository;

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
            comment.setAuthor(userRepository.getUserById(1L));
            commentRepository.save(comment);
            return new CreateOrUpdateCommentDTO(comment.getText());
        }
        return null;
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


}
