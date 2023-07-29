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

    /**
     * Mapping from Ad to AdDto
     *
     * @param ad instance of {@link Ad} class
     * @return instance of {@link AdDTO} class
     */
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

    /**
     * Mapping from collection of ads to AdsDto
     *
     * @param ad collection of ads
     * @return instance of {@link AdsDTO} class
     */
    public AdsDTO mapEntityListToAdsDto(Collection<Ad> ad) {
        AdsDTO adsDto = new AdsDTO();
        adsDto.setCount(ad.size());
        adsDto.setResults(ad.stream().map(this::mapEntityToAdDto).collect(Collectors.toList()));
        return adsDto;
    }

    /**
     * Mapping from Ad to ExtendedAdDTO
     *
     * @param ad instance of {@link Ad} class
     * @return instance of {@link ExtendedAdDTO} class
     */
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

    /**
     * Mapping from CreateOrUpdateAdDTO to ExtendedAdDTO
     *
     * @param createOrUpdateAdDTO instance of {@link CreateOrUpdateAdDTO} class
     * @param imageLink link to ad image
     * @return instance of {@link ExtendedAdDTO} class
     */
    public ExtendedAdDTO mapCreateOrUpdateAdToExtendedAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, String imageLink) {
        ExtendedAdDTO extendedAdDTO = new ExtendedAdDTO();
        extendedAdDTO.setTitle(createOrUpdateAdDTO.getTitle());
        extendedAdDTO.setPrice(createOrUpdateAdDTO.getPrice());
        extendedAdDTO.setDescription(createOrUpdateAdDTO.getDescription());
        extendedAdDTO.setImage(imageLink);
        return extendedAdDTO;
    }

    /**
     * Mapping from ExtendedAdDTO to Ad
     *
     * @param extendedAdDTO instance of {@link ExtendedAdDTO} class
     * @return instance of {@link Ad} class
     */
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
