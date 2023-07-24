package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.model.Image;

import java.nio.file.FileAlreadyExistsException;


public interface UserService {

    boolean setNewPassword(PasswordDTO passwordDTO,
                           String username);

    UserDTO getUser(Integer id);

    UserDTO getUser(String username);

    UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone);

    String updateUserImage(MultipartFile image, String username);

    Image getImage(String pathToImage) throws FileAlreadyExistsException;

    User mapToUserAndSave(RegisterReq req);

    UserAuthDTO mapToUserAuthDTO(User user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean userExists(String username);
}
