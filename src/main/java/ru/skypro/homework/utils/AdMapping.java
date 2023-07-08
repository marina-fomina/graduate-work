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
    public AdDTO mapEntityToAdDto(Ad ad) {
        AdDTO adDto = new AdDTO();
        adDto.setPk(ad.getId());
        adDto.setAuthor(ad.getAuthor());
        adDto.setImage(ad.getImage());
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
        extendedAdDto.setImage(ad.getImage());
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

    public CreateOrUpdateAdDTO mapEntityToCreateOrUpdateAd(Ad ad) {
        CreateOrUpdateAdDTO createOrUpdateAdDTO = new CreateOrUpdateAdDTO();
        createOrUpdateAdDTO.setTitle(ad.getTitle());
        createOrUpdateAdDTO.setPrice(ad.getPrice());
        createOrUpdateAdDTO.setDescription(ad.getDescription());
        return createOrUpdateAdDTO;
    }

    public Ad mapCreateOrUpdateAdToEntity(CreateOrUpdateAdDTO ad) {
        Ad adEntity = new Ad();
        adEntity.setTitle(ad.getTitle());
        adEntity.setPrice(ad.getPrice());
        adEntity.setDescription(ad.getDescription());
        return adEntity;
    }

    public Ad mapDtoToAdEntity(AdDTO adDTO) {
        Ad ad = new Ad();
        // TODO: дописать
        ad.setAuthor(null);
        ad.setDescription(null);

        ad.setTitle(adDTO.getTitle());
        ad.setPrice(adDTO.getPrice());
        ad.setImage(adDTO.getImage());
        return ad;
    }
}
