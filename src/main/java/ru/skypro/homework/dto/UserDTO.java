package ru.skypro.homework.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Integer id;
    private String email; //логин
    private String firstName;
    private String lastName;
    private String phone;
    // TODO: обновление спецификации v19 (https://github.com/BizinMitya/front-react-avito/blob/v1.19/openapi.yaml)
    private Role role;
    private String image; //ссылка на аватар пользователя
}
