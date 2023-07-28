package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.skypro.homework.utils.UserMapping;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(User.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserMapping userMapping;

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
    public UserDTO getUser(String username) {
        return userMapping.mapToUserDTO(userRepository.getUserByUsername(username));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(userMapping.mapToUserAuthDTO(user));
    }

    @Override
    public boolean userExists(String username) {
        return Objects.nonNull(userRepository.getUserByUsername(username));
    }

}
