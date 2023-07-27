package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.model.UserPrincipal;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(User.class);
//    @Value("${path.to.data.file.images}")
//    private String pathToImage;
    private final UserRepository userRepository;
    @Autowired
    ImageService imageService;

    private final String imagePrefix = "/users/image?id=";

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean setNewPassword(PasswordDTO passwordDTO,
                                  String username) {
        User user = userRepository.getUserByUsername(username);
        if (user.getPassword().equals(passwordDTO.getCurrentPassword()) &&
                passwordDTO.getNewPassword() != null &&
                !passwordDTO.getNewPassword().equals(passwordDTO.getCurrentPassword())) {
            user.setPassword(passwordDTO.getNewPassword());
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDTO getUser(Integer id) {
        return mapToUserDTO(userRepository.getUserById(id));
    }

    @Override
    public UserDTO getUser(String username) {
        return mapToUserDTO(userRepository.getUserByUsername(username));
    }

    @Override
    public Optional<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }

    @Override
    public UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone) {
        return new UpdateUserDTO(newFirstName, newLastName, newPhone);
    }

    /**
     * Update user avatar
     *
     * @param username current user
     * @param image    new avatar
     * @return UUID
     */
    @Override
    public String updateUserImage(MultipartFile image, String username) {
        Optional<User> entity = userRepository.findByUsername(username);
        if (entity.isPresent()) {
            imageService.deleteImage(entity.get().getImage());
            Path path = Paths.get(imageService.saveImage(image));
            entity.get().setImage(path.toString());
            userRepository.save(entity.get());
            return path.toString();
        } else {
            logger.info("Not found User with username: {}", username);
            return null;
        }
    }

    private User mapToUserFromUserDTO(UserDTO userDTO, String password, Role role) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setUsername(userDTO.getEmail());
        user.setPassword(password);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setRole(role);
        if (userDTO.getImage() != null && !userDTO.getImage().isBlank()) {
            user.setImage(userDTO.getImage());
        }

        return user;
    }

    private User mapToUserFromUpdateUserDTO(UpdateUserDTO updateUserDTO, Integer id, String username,
                                            String password, Role role, String image) {
        User user = new User();

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setPhone(user.getPhone());
        user.setRole(role);
        user.setImage(image);
        return user;
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        if (user.getImage() != null && !user.getImage().isBlank()) {
            //TODO : заменить
//            userDTO.setImage(imagePrefix + user.getImage().replace("\\", "/"));
            userDTO.setImage(imagePrefix + user.getImage());
        }
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private UpdateUserDTO mapToUpdateUserDTO(User user) {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();

        updateUserDTO.setFirstName(user.getFirstName());
        updateUserDTO.setLastName(user.getLastName());
        updateUserDTO.setPhone(user.getPhone());

        return updateUserDTO;
    }

    @Override
    public User mapToUserAndSave(RegisterReq req) {
        User user = new User();

        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setPhone(req.getPhone());
        user.setRole(req.getRole());
//        user.setImage();

        userRepository.save(user);
        return user;
    }

    @Override
    public UserAuthDTO mapToUserAuthDTO(User user) {
        return new UserAuthDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(mapToUserAuthDTO(user));
    }

    @Override
    public boolean userExists(String username) {
        return Objects.nonNull(userRepository.getUserByUsername(username));
    }

}
