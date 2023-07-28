package ru.skypro.homework.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdMapping {
    @Autowired
    UserService userService;
    private final String imagePrefix = "/ads/image?id=";

    public AdDTO mapEntityToAdDto(Ad ad) {
        AdDTO adDto = new AdDTO();
        adDto.setPk(ad.getId());
        adDto.setAuthor(ad.getAuthor().getId());
        if (ad.getImage() != null && !ad.getImage().isBlank()) {
            adDto.setImage(imagePrefix + ad.getImage());
        }
        adDto.setTitle(ad.getTitle());
        adDto.setPrice(ad.getPrice());
        return adDto;
    }

    public AdsDTO mapEntityListToAdsDto(Collection<Ad> ad) {
        AdsDTO adsDto = new AdsDTO();
        adsDto.setCount(ad.size());
        adsDto.setResults(ad.stream().map(this::mapEntityToAdDto).collect(Collectors.toList()));
        return adsDto;
    }

    public ExtendedAdDTO mapEntityToExtendedAdDto(Ad ad) {
        ExtendedAdDTO extendedAdDto = new ExtendedAdDTO();
        extendedAdDto.setPk(ad.getId());
        extendedAdDto.setDescription(ad.getDescription());
        if (ad.getImage() != null && !ad.getImage().isBlank()) {
            extendedAdDto.setImage(imagePrefix + ad.getImage());
        }
        extendedAdDto.setTitle(ad.getTitle());
        extendedAdDto.setPrice(ad.getPrice());
        extendedAdDto.setAuthorFirstName(ad.getAuthor().getFirstName());
        extendedAdDto.setAuthorLastName(ad.getAuthor().getLastName());
        extendedAdDto.setPhone(ad.getAuthor().getPhone());
        extendedAdDto.setEmail(ad.getAuthor().getUsername());
        return extendedAdDto;
    }

    public ExtendedAdDTO mapCreateOrUpdateAdToExtendedAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, String imageLink) {
        ExtendedAdDTO extendedAdDTO = new ExtendedAdDTO();
        extendedAdDTO.setTitle(createOrUpdateAdDTO.getTitle());
        extendedAdDTO.setPrice(createOrUpdateAdDTO.getPrice());
        extendedAdDTO.setDescription(createOrUpdateAdDTO.getDescription());
        extendedAdDTO.setImage(imageLink);
        return extendedAdDTO;
    }

    public Ad mapExtendedAdToAdEntity(ExtendedAdDTO extendedAdDTO) {
        Ad ad = new Ad();
        if (extendedAdDTO.getPk() != null) {
            ad.setId(extendedAdDTO.getPk());
        }
        userService.getUser().ifPresent(ad::setAuthor);
        ad.setDescription(extendedAdDTO.getDescription());
        ad.setTitle(extendedAdDTO.getTitle());
        ad.setPrice(extendedAdDTO.getPrice());
        String imageId = extendedAdDTO.getImage().replace(imagePrefix, "");
        ad.setImage(imageId);
        return ad;
    }
}
