package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.utils.AdMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
    public Ads getAds() {
        List<AdEntity> entityList = adRepository.findAll();
        return adMapping.mapEntityListToAdsDto(entityList);
    }

    @Override
    public void addAd(Ad ad) {
        AdEntity adEntity = new AdMapping().mapDtoToAdEntity(ad);
        adRepository.save(adEntity);
    }

    @Override
    public ExtendedAd getAdById(Integer id) {
        ExtendedAd extendedAd = new ExtendedAd();
        Optional<AdEntity> adEntity = adRepository.findById(id);
        if (adEntity.isPresent()) {
            extendedAd = adMapping.mapEntityToExtendedAdDto(adEntity.get());
        }
        return extendedAd;
    }

    @Override
    public boolean deleteAd(Integer id) {
        boolean flag = adRepository.existsById(id);
        adRepository.deleteById(id);
        return flag;
    }
    @Override
    public Ad patchAd(Integer id, CreateOrUpdateAd createOrUpdateAd) {
        // TODO:
        //  1) разобраться с классами CreateOrUpdateAd и Ad
        //  2) как не затереть лишнее
//        Optional<AdEntity> adEntity = adRepository.findById(id);
//        if (adEntity.isPresent()) {
//            adEntity.get().set
//        }
//        AdEntity adEntity = adMapping.mapCreateOrUpdateAdToEntity(createOrUpdateAd);
//        adRepository.save(adEntity);
//        return adMapping.mapEntityToAdDto(adEntity);
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
        Optional<AdEntity> entity = adRepository.findById(id);
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
    private void deleteImage(String image) {
        try {
            Files.deleteIfExists(Path.of(image));
            logger.info("Delete image: {}", image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
