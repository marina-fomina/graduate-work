package ru.skypro.homework.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {

    private String newPassword; // min 8, max 16 символов
    private String currentPassword; // min 8, max 16 символов
}
