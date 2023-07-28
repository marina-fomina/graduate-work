package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;

import java.util.Optional;


public interface UserService {

    /**
     * Set new user password
     *
     * @param passwordDTO include new and current password
     * @param username    current user login
     * @return true or false
     */
    boolean setNewPassword(PasswordDTO passwordDTO,
                           String username);

    /**
     * Get user by username
     *
     * @param username current user login
     * @return user id, first name, last name, role, email, phone number and avatar
     */
    UserDTO getUser(String username);

    /**
     * Get current user
     *
     * @return optional of user
     */
    Optional<User> getUser();

    /**
     * Update user information
     *
     * @param newFirstName user first name
     * @param newLastName  user last name
     * @param newPhone     user phone
     * @return user first name, last name and phone number
     */
    UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone);

    /**
     * Update user avatar
     *
     * @param username current user login
     * @param image    new avatar
     * @return UUID
     */
    String updateUserImage(MultipartFile image, String username);


//    UserAuthDTO mapToUserAuthDTO(User user);

    /**
     * Load user
     *
     * @param username current user login
     * @return UserDetails service
     * @throws UsernameNotFoundException throws when the user is not found in the database
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Checking if the user exists
     *
     * @param username current user login
     * @return true or false
     */
    boolean userExists(String username);
}
