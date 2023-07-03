package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setNewPassword(@RequestBody PasswordDTO passwordDTO) {
        if (userService.setNewPassword(passwordDTO.getNewPassword(), passwordDTO.getCurrentPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(@RequestParam Integer id) {
        return ResponseEntity.ok(userService.getUser(Long.valueOf(id)));
    }


    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(updateUserDTO.getFirstName(),
                updateUserDTO.getLastName(), updateUserDTO.getPhone()));
    }


    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image) {
        userService.updateUserImage(image);
        return ResponseEntity.ok().build();
    }
}
