package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;

import java.nio.file.Path;

public interface ImageService {
    String saveImage(MultipartFile file);

    Image getImage(String id);

    void deleteImage(String image);

    Path pathOfImage(String id);
}
