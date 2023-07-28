package ru.skypro.homework.utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserAuthDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

@Service
public class UserMapping {
    private static final String imagePrefix = "/users/image?id=";

    /**
     * Mapping from User to UserAuthDTO
     *
     * @param user instance of {@link User} class
     * @return instance of {@link UserAuthDTO} class with user's id, username, password and role
     */
    public UserAuthDTO mapToUserAuthDTO(User user) {
        return new UserAuthDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }

    /**
     * Mapping from User to UserAuthDTO
     *
     * @param user instance of {@link User} class
     * @return instance of {@link UserDTO} class with user's id, first name, last name, role, email, phone number and avatar
     */
    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        if (user.getImage() != null && !user.getImage().isBlank()) {
            userDTO.setImage(imagePrefix + user.getImage());
        }
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
