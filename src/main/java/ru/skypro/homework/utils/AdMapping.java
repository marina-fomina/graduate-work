package ru.skypro.homework.utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdMapping {
    private final String imagePrefix = "/ads/image?path=/";
    public AdDTO mapEntityToAdDto(Ad ad) {
        AdDTO adDto = new AdDTO();
        adDto.setPk(ad.getId());
        adDto.setAuthor(ad.getAuthor());
        if (adDto.getImage() != null && !adDto.getImage().isBlank()) {
            adDto.setImage(imagePrefix + ad.getImage().replace("\\", "/"));
        }

        System.out.println(adDto.getImage());
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
        if (extendedAdDto.getImage() != null && !extendedAdDto.getImage().isBlank()) {
            extendedAdDto.setImage(imagePrefix + ad.getImage().replace("\\", "/"));
        }


        extendedAdDto.setTitle(ad.getTitle());
        extendedAdDto.setPrice(ad.getPrice());

        // TODO: получить данные пользователя
        // Optional<User> user = userRepository.findById(adEntity.get().getAuthor());
//        if (user.isPresent()) {
        extendedAdDto.setAuthorFirstName(null);
        extendedAdDto.setAuthorLastName(null);
        extendedAdDto.setPhone(null);
        extendedAdDto.setEmail(null);
//        }
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
        // TODO: дописать
        ad.setAuthor(1);

        ad.setDescription(extendedAdDTO.getDescription());
        ad.setTitle(extendedAdDTO.getTitle());
        ad.setPrice(extendedAdDTO.getPrice());
        ad.setImage(extendedAdDTO.getImage());
        return ad;
    }
}
