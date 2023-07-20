package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.utils.AdMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdServiceImpl implements AdService {
    @Value("${file.repository}")
    String pathToFileRepository;
    @Autowired
    AdRepository adRepository;
    @Autowired
    AdMapping adMapping;
    Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);

    // TODO: дописать сервис для данного эндпоинта
    //    @GetMapping("/me")
    //    public ResponseEntity<Ads> getUserAd(Authentication authentication) {
    //        Ads ads = new Ads();
    //        return ResponseEntity.ok(ads);
    //    }
    @Override
    public AdsDTO getAds() {
        List<Ad> entityList = adRepository.findAll();
        return adMapping.mapEntityListToAdsDto(entityList);
    }

    @Override
    public AdDTO addAd(ExtendedAdDTO extendedAdDTO) {
        Ad ad = adRepository.save(adMapping.mapExtendedAdToAdEntity(extendedAdDTO));
        return adMapping.mapEntityToAdDto(ad);
    }

    @Override
    public ExtendedAdDTO getAdById(Integer id) {
        Optional<Ad> adEntity = adRepository.findById(id);
        return adEntity.map(ad -> adMapping.mapEntityToExtendedAdDto(ad)).orElse(null);
    }

    @Override
    public boolean deleteAd(Integer id) {
        boolean flag = adRepository.existsById(id);
        if (flag) {
            adRepository.deleteById(id);
        }
        return flag;
    }
    @Override
    public AdDTO patchAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        Optional<Ad> ad = adRepository.findById(id);
        ExtendedAdDTO adDto = ad.map(a -> adMapping.mapEntityToExtendedAdDto(a)).orElse(null);
        if (Objects.nonNull(adDto)) {
            adDto.setTitle(createOrUpdateAdDTO.getTitle());
            adDto.setPrice(createOrUpdateAdDTO.getPrice());
            adDto.setDescription(createOrUpdateAdDTO.getDescription());
            return adMapping.mapEntityToAdDto(adRepository.save(adMapping.mapExtendedAdToAdEntity(adDto)));
        }
        return null;
    }

    @Override
    public String saveImage(MultipartFile file) {
        String uuid = UUID.randomUUID() + "." +
                StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path path = Paths.get(pathToFileRepository, uuid);
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            logger.warn("WARN! Failed to upload file '{}' to the repository at '{}'", file.getOriginalFilename(), pathToFileRepository);
            throw new RuntimeException(e);
        }
        return path.toString();
    }
    @Override
    public String saveImage(Integer id, MultipartFile file) {
        Optional<Ad> entity = adRepository.findById(id);
        if (entity.isPresent()) {
            deleteImage(entity.get().getImage());
            Path path = Paths.get(saveImage(file));
            entity.get().setImage(path.toString());
            adRepository.save(entity.get());
            return path.toString();
        } else {
            logger.info("Not found Ad with id: {}", id);
            return null;
        }
    }
    @Override
    public Image getImage(String path) {
        // TODO: отследить null
        Image image = new Image();
        try {
            switch(StringUtils.getFilenameExtension(path)) {
                case "png":
                    image.setMediaType(MediaType.IMAGE_PNG);
                    image.setBytes(Files.readAllBytes(Path.of(path)));
                    break;
                case "jpg":
                    image.setMediaType(MediaType.IMAGE_JPEG);
                    image.setBytes(Files.readAllBytes(Path.of(path)));
                    break;
            }
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void deleteImage(String image) {
        try {
            Files.deleteIfExists(Path.of(image));
            logger.info("Delete image: {}", image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
