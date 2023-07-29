package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
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
        UserDTO userDTO = userService.getUser(authentication.getName());
        return ResponseEntity.ok().body(userDTO);
    }


    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(updateUserDTO.getFirstName(),
                updateUserDTO.getLastName(), updateUserDTO.getPhone()));
    }


    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(Authentication authentication,
                                                  @RequestParam MultipartFile image) {
        userService.updateUserImage(image, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(String id) {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok().contentType(image.getMediaType()).body(image.getBytes());
    }
}
