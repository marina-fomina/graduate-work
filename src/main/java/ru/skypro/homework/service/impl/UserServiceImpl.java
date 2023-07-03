package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(User.class);
    @Value("${path.to.data.file}")
    private String pathToImage;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean setNewPassword(String newPassword, String currentPassword) {
        if (newPassword != null && !newPassword.equals(currentPassword)) {
            PasswordDTO passwordDTO = new PasswordDTO(newPassword, currentPassword);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDTO getUser(Long id) {
        return mapToUserDTO(userRepository.getUserById(id));
    }

    @Override
    public UpdateUserDTO updateUser(String newFirstName, String newLastName, String newPhone) {
        return new UpdateUserDTO(newFirstName, newLastName, newPhone);
    }

    @Override
    public String updateUserImage(MultipartFile image) {
//        Path dir = Paths.get(image); // ???
//        String filename = image.getName();
//        Path file;
//
//        try {
//            if (!Files.exists(dir)) {
//                Files.createDirectory(dir);
//            }
//            file = Files.createFile(Paths.get(dir.toString(), filename));
//
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()));
//            oos.writeObject(image);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return filename;

        String uuid = UUID.randomUUID() + "." +
                StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(pathToImage, uuid);
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(image.getBytes());
        } catch (IOException e) {
            logger.warn("WARN! Failed to upload file '{}' to the repository at '{}'", image.getOriginalFilename(), pathToImage);
            throw new RuntimeException(e);
        }
        return path.toString();
    }



    private User mapToUserFromUserDTO(UserDTO userDTO, String password, Role role) {
        User user = new User();

        user.setId(Long.valueOf(userDTO.getId()));
        user.setUsername(userDTO.getEmail());
        user.setPassword(password);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setRole(role);
        user.setImage(userDTO.getImage());

        return user;
    }

    private User mapToUserFromUpdateUserDTO(UpdateUserDTO updateUserDTO, Long id, String username,
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

        userDTO.setId(Math.toIntExact(user.getId()));
        userDTO.setEmail(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setImage(user.getImage());

        return userDTO;
    }

    private UpdateUserDTO mapToUpdateUserDTO(User user) {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();

        updateUserDTO.setFirstName(user.getFirstName());
        updateUserDTO.setLastName(user.getLastName());
        updateUserDTO.setPhone(user.getPhone());

        return updateUserDTO;
    }
}
