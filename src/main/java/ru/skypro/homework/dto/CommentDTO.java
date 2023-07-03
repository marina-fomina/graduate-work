package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer author; // id автора комментария
    private String authorImage; // ссылка на аватар автора комментария
    private String authorFirstName; // имя создателя комментария
    private Long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    private Integer pk; // pk - primary key, т.е. id комментария
    private String text; // текст комментария
}
