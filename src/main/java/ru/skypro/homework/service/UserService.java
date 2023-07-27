package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;

import java.util.Optional;


public interface UserService {

    boolean setNewPassword(PasswordDTO passwordDTO,
                           String username);

    UserDTO getUser(Integer id);

    UserDTO getUser(String username);

    Optional<User> getUser();

    UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone);

    String updateUserImage(MultipartFile image, String username);

    User mapToUserAndSave(RegisterReq req);

    UserAuthDTO mapToUserAuthDTO(User user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean userExists(String username);
}
