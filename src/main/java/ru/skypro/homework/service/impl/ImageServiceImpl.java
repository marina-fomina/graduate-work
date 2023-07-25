package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.IncorrectFileTypeException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Value("${path.to.data.file.images}")
    String pathToFileRepository;
    @Autowired
    AdRepository adRepository;
    @Autowired
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(ImageService.class);
    @Override
    public String saveImage(MultipartFile file) {
        String uuid = UUID.randomUUID() + "." +
                StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path path = pathOfImage(uuid);
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            logger.warn("WARN! Failed to upload file '{}' to the repository at '{}'", file.getOriginalFilename(), pathToFileRepository);
            throw new RuntimeException(e);
        }
        return uuid;
    }
//    /**
//     * Update ad image
//     * @param id primary key of ad
//     * @param file new image
//     * @return UUID
//     */
//    @Override
//    public String updateAdImage(Integer id, MultipartFile file) {
//        Optional<Ad> entity = adRepository.findById(id);
//        if (entity.isPresent()) {
//            deleteImage(entity.get().getImage());
//            Path path = Paths.get(saveImage(file));
//            entity.get().setImage(path.toString());
//            adRepository.save(entity.get());
//            return path.toString();
//        } else {
//            logger.info("Not found Ad with id: {}", id);
//            return null;
//        }
//    }

//    @Override
//    public String updateUserImage(String username, MultipartFile file) {
//        Optional<User> entity = userRepository.findByUsername(username);
//        if (entity.isPresent()) {
//            deleteImage(entity.get().getImage());
//            Path path = Paths.get(saveImage(file));
//            entity.get().setImage(path.toString());
//            userRepository.save(entity.get());
//            return path.toString();
//        } else {
//            logger.info("Not found User with username: {}", username);
//            return null;
//        }
//    }
    @Override
    public Image getImage(String id) {
        Image image = new Image();
        try {
            switch(Objects.requireNonNull(StringUtils.getFilenameExtension(id))) {
                case "png":
                    image.setMediaType(MediaType.IMAGE_PNG);
                    image.setBytes(Files.readAllBytes(pathOfImage(id)));
                    break;
                case "jpg":
                    image.setMediaType(MediaType.IMAGE_JPEG);
                    image.setBytes(Files.readAllBytes(pathOfImage(id)));
                    break;
                case "gif":
                    image.setMediaType(MediaType.IMAGE_GIF);
                    image.setBytes(Files.readAllBytes(pathOfImage(id)));
                    break;
                default:
                    throw new IncorrectFileTypeException(id);
            }
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteImage(String image) {
        try {
            Files.deleteIfExists(pathOfImage(image));
            logger.info("Delete image: {}", image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override

    public Path pathOfImage(String id) {
        return Path.of(pathToFileRepository, id);
    }

//    private String updateEntity(Optional<?> entity) {
//        if (entity.isPresent() && (entity.get() instanceof Ad || entity.get() instanceof User)) {
//            String uuid;
//            if (entity.get() instanceof Ad) {
//                uuid = (Ad)entity.get().getImage();
//            }
//            deleteImage(entity.get().getImage());
//            Path path = Paths.get(updateAdImage(file));
//            entity.get().setImage(path.toString());
//            adRepository.save(entity.get());
//            return path.toString();
//        }
//    }
}
