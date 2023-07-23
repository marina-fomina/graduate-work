package ru.skypro.homework.service.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

//  private final UserDetailsManager manager;

  private final UserService userService;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//    this.manager = manager;
    this.userService = userService;
    this.userRepository = userRepository;
    this.encoder = passwordEncoder;
  }

  @Override
  public boolean login(String userName, String password) {
    if (!userService.userExists(userName)) {
      return false;
    }
    UserDetails userDetails = userService.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    if (userService.userExists(registerReq.getUsername())) {
      return false;
    }
//    manager.createUser(
//        User.builder()
//            .passwordEncoder(this.encoder::encode)
//            .password(registerReq.getPassword())
//            .username(registerReq.getUsername())
//            .roles(String.valueOf(role))
//            .build());
    User user = new User();
    user.setUsername(registerReq.getUsername());
    user.setPassword(encoder.encode(registerReq.getPassword()));
    user.setFirstName(registerReq.getFirstName());
    user.setLastName(registerReq.getLastName());
    user.setPhone(registerReq.getPhone());
    user.setRole(role);
    userRepository.save(user);
    return true;
  }
}
