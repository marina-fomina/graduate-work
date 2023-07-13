package ru.skypro.homework.entity;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ads")
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "author_id")
    private Integer author;
    @Column
    private String image;
    @Column
    private Integer price;
    @Column(length = 100)
    private String title;
    @Column(length = 1024)
    private String description;

    @OneToMany(mappedBy="ad")
    private List<Comment> comments;

    //  метод для установки связи
    public CreateOrUpdateCommentDTO addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setAd(this);
        return null;
    }
}
