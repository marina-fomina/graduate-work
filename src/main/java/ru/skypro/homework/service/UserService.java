package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.model.Image;

import java.nio.file.FileAlreadyExistsException;


public interface UserService {

    boolean setNewPassword(PasswordDTO passwordDTO,
                           String username);

    UserDTO getUser(String username);

    UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone);

    String updateUserImage(MultipartFile image, String username);

    Image getImage(String pathToImage) throws FileAlreadyExistsException;

    boolean userExists(String username);

    User mapToUserAndSave(RegisterReq req);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
