package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;

import java.nio.file.Path;

public interface ImageService {
    /**
     * Save image to file server
     *
     * @param file file for save
     * @return file name (GUID)
     */
    String saveImage(MultipartFile file);

    /**
     * Get image by GUID
     *
     * @param id GUID
     * @return {@link Image}
     */
    Image getImage(String id);

    /**
     * Delete image
     *
     * @param image GUID
     */
    void deleteImage(String image);

    /**
     * Get full path of image in file server by GUID
     *
     * @param id GUID
     * @return path of image
     */
    Path pathOfImage(String id);
}
