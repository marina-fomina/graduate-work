package ru.skypro.homework.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private String phone;
}
