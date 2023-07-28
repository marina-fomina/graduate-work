package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.AdMapping;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    AdRepository adRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdMapping adMapping;
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;
    Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);

    @Override
    public AdsDTO getUserAds() {
        List<Ad> entityList = adRepository.findAllByAuthor(userService.getUser().orElseThrow());
        return adMapping.mapEntityListToAdsDto(entityList);
    }

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
    public boolean deleteAd(String username, Integer id) {
        User user = userRepository.getUserByUsername(username);
        if (adRepository.existsById(id) && Objects.nonNull(user)) {
            if (isAuthor(username, id) || user.getRole().equals(Role.ADMIN)) {
                adRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public AdDTO patchAd(String username, Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        User user = userRepository.getUserByUsername(username);
        Optional<Ad> ad = adRepository.findById(id);
        ExtendedAdDTO adDto = ad.map(a -> adMapping.mapEntityToExtendedAdDto(a)).orElse(null);
        if (Objects.nonNull(adDto) && Objects.nonNull(user)) {
            if (user.getRole().equals(Role.ADMIN) || isAuthor(username, id)) {
                adDto.setTitle(createOrUpdateAdDTO.getTitle());
                adDto.setPrice(createOrUpdateAdDTO.getPrice());
                adDto.setDescription(createOrUpdateAdDTO.getDescription());
                return adMapping.mapEntityToAdDto(adRepository.save(adMapping.mapExtendedAdToAdEntity(adDto)));
            }
        }
        return null;
    }

    @Override
    public String updateAdImage(Integer id, MultipartFile file) {
        Optional<Ad> entity = adRepository.findById(id);
        if (entity.isPresent()) {
            imageService.deleteImage(entity.get().getImage());
            Path path = Paths.get(imageService.saveImage(file));
            entity.get().setImage(path.toString());
            adRepository.save(entity.get());
            return path.toString();
        } else {
            logger.info("Not found Ad with id: {}", id);
            return null;
        }
    }

    private boolean isAuthor(String username, Integer id) {
        return adRepository.getAdById(id).getAuthor().getId().equals(userRepository.getUserByUsername(username).getId());
    }
}
