package ru.skypro.homework.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    String image;
    Integer price;
    @Column(nullable = false, length = 100)
    String title;
    @Column(length = 1024)
    String description;

    @OneToMany(mappedBy="ad", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
}
