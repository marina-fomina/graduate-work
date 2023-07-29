package ru.skypro.homework.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAuthDTO {
    private Integer userId;
    private String username;
    private String password;
    private Role role;
}
