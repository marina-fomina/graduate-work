package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

import java.util.Optional;


public interface UserService {

    /**
     * Set new user password
     *
     * @param passwordDTO includes new and current passwords
     * @param username    current user login
     * @return true (if new password was set) or false (if not)
     */
    boolean setNewPassword(PasswordDTO passwordDTO,
                           String username);

    /**
     * Get user by username
     *
     * @param username current user login
     * @return instance of {@link UserDTO} class that includes user id, first name, last name, role, email, phone number and avatar
     */
    UserDTO getUser(String username);

    /**
     * Get current user
     *
     * @return instance of {@link User} class wrapped in {@link Optional} class
     */
    Optional<User> getUser();

    /**
     * Update information about user
     *
     * @param newFirstName new user first name
     * @param newLastName  new user last name
     * @param newPhone     new user phone number
     * @return instance of {@link UpdateUserDTO} class that includes user first name, last name and phone number
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


    /**
     * Load user
     *
     * @param username current user login
     * @return instance of {@link ru.skypro.homework.model.UserPrincipal} class that implements {@link UserDetails} interface
     * @throws UsernameNotFoundException is thrown when user is not found in the database
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Checking if user exists
     *
     * @param username current user login
     * @return true or false
     */
    boolean userExists(String username);
}
