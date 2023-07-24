package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.UserService;
import java.nio.file.FileAlreadyExistsException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setNewPassword(Authentication authentication,
                                               @RequestBody PasswordDTO passwordDTO) {
        String username = authentication.getName();
        if (userService.setNewPassword(passwordDTO, username)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }


    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(updateUserDTO.getFirstName(),
                updateUserDTO.getLastName(), updateUserDTO.getPhone()));
    }


    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(Authentication authentication,
                                                  @RequestParam MultipartFile image) {

        String username = authentication.getName();
        userService.updateUserImage(image, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(String path) {
        String filePath = path.substring(1);
        try {
            Image image = userService.getImage(filePath);
            return ResponseEntity.ok().contentType(image.getMediaType()).body(image.getBytes());
        } catch (FileAlreadyExistsException e) { // не FileNotFoundException
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody RegisterReq req,
                                         @RequestParam MultipartFile image) {
        return ResponseEntity.ok(userService.mapToUserAndSave(req));
    }
}
