package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String username; // =login, =email

    @Column(nullable = false, length = 24)
    private String password;
    // TODO: переименовал имена таблиц и добавил ограничения длинны
    @Column(name = "first_name", length = 32)
    private String firstName;

    @Column(name = "last_name", length = 32)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column
    private Role role;

    @Column
    private String image; //ссылка на аватар пользователя

    @OneToMany(mappedBy="author")
    private List<Comment> comments;

    //  метод для установки связи
    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setAuthor(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
