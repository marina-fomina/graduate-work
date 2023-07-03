package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;


public interface UserService {
    boolean setNewPassword(String newPassword, String currentPassword);

    UserDTO getUser(Long id);

    UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone);

    String updateUserImage(MultipartFile image);
}
