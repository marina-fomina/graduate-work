package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Integer id);
    User getUserByUsername(String username);

}
