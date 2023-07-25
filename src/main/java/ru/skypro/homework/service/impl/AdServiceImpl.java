package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdRepository;
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

    /**
     * Update ad image
     * @param id primary key of ad
     * @param file new image
     * @return UUID
     */
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
}
