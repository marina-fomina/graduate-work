package ru.skypro.homework.utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserAuthDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

@Service
public class UserMapping {
    private final String imagePrefix = "/users/image?id=";

    /**
     * Mapping User to UserAuthDTO
     *
     * @param user entity
     * @return user id, username, password, role
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
     * Mapping User to UserAuthDTO
     *
     * @param user entity
     * @return user id, first name, last name, role, email, phone number and avatar
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

    // TODO: Проверить, нужны ли
//    private User mapToUserFromUserDTO(UserDTO userDTO, String password, Role role) {
//        User user = new User();
//
//        user.setId(userDTO.getId());
//        user.setUsername(userDTO.getEmail());
//        user.setPassword(password);
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setPhone(userDTO.getPhone());
//        user.setRole(role);
//        if (userDTO.getImage() != null && !userDTO.getImage().isBlank()) {
//            user.setImage(userDTO.getImage());
//        }
//
//        return user;
//    }
//
//    private User mapToUserFromUpdateUserDTO(UpdateUserDTO updateUserDTO, Integer id, String username,
//                                            String password, Role role, String image) {
//        User user = new User();
//
//        user.setId(id);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setFirstName(updateUserDTO.getFirstName());
//        user.setLastName(updateUserDTO.getLastName());
//        user.setPhone(user.getPhone());
//        user.setRole(role);
//        user.setImage(image);
//        return user;
//    }
//
//
//
//    private UpdateUserDTO mapToUpdateUserDTO(User user) {
//        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
//
//        updateUserDTO.setFirstName(user.getFirstName());
//        updateUserDTO.setLastName(user.getLastName());
//        updateUserDTO.setPhone(user.getPhone());
//
//        return updateUserDTO;
//    }
}
