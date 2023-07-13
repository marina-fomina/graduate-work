package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(User.class);
    @Value("${path.to.data.file}")
    private String pathToImage;
    private final UserRepository userRepository;

    private final String imagePrefix = "/users/image?path=/";

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
    public UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone) {
        return new UpdateUserDTO(newFirstName, newLastName, newPhone);
    }

    @Override
    public String updateUserImage(MultipartFile image, String username) {

// TODO: Вынести работу с картинками в отдельный класс

        String uuid = UUID.randomUUID() + "." +
                StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(pathToImage, uuid);
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(image.getBytes());
        } catch (IOException e) {
            logger.warn("WARN! Failed to upload file '{}' to the repository at '{}'", image.getOriginalFilename(), pathToImage);
            throw new RuntimeException(e);
        }

        User user = userRepository.getUserByUsername(username);

        user.setImage(String.valueOf(path));
        userRepository.save(user);
        return path.toString();
    }




    @Override
    public Image getImage(String pathToImage) {
        Image image = new Image();
        try {
            switch (Objects.requireNonNull(StringUtils.getFilenameExtension(pathToImage))) {
                case "png":
                    image.setMediaType(MediaType.IMAGE_PNG);
                    image.setBytes(Files.readAllBytes(Path.of(pathToImage)));
                    break;
                case "jpg":
                    image.setMediaType(MediaType.IMAGE_JPEG);
                    image.setBytes(Files.readAllBytes(Path.of(pathToImage)));
                    break;
            }
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        if (user.getImage() != null && !user.getImage().isBlank()) {
            user.setImage(imagePrefix + userDTO.getImage().replace("\\", "/"));
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
        if (user.getImage() != null && !user.getImage().isBlank()) {
            user.setImage(imagePrefix + image.replace("\\", "/"));
        }

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
            userDTO.setImage(imagePrefix + user.getImage().replace("\\", "/"));
        }

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
}
