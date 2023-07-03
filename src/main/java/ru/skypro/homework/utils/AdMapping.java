package ru.skypro.homework.utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdMapping {
    public Ad mapEntityToAdDto(AdEntity ad) {
        Ad adDto = new Ad();
        adDto.setPk(ad.getId());
        adDto.setAuthor(ad.getAuthor());
        adDto.setImage(ad.getImage());
        adDto.setTitle(ad.getTitle());
        adDto.setPrice(ad.getPrice());
        return adDto;
    }

    public Ads mapEntityListToAdsDto(Collection<AdEntity> ad) {
        Ads adsDto = new Ads();
        adsDto.setCount(ad.size());
        adsDto.setResults(ad.stream().map(this::mapEntityToAdDto).collect(Collectors.toList()));
        return adsDto;
    }

    public ExtendedAd mapEntityToExtendedAdDto(AdEntity ad) {
        ExtendedAd extendedAdDto = new ExtendedAd();
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

    public CreateOrUpdateAd mapEntityToCreateOrUpdateAd(AdEntity ad) {
        CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd();
        createOrUpdateAd.setTitle(ad.getTitle());
        createOrUpdateAd.setPrice(ad.getPrice());
        createOrUpdateAd.setDescription(ad.getDescription());
        return createOrUpdateAd;
    }

    public AdEntity mapCreateOrUpdateAdToEntity(CreateOrUpdateAd ad) {
        AdEntity adEntity = new AdEntity();
        adEntity.setTitle(ad.getTitle());
        adEntity.setPrice(ad.getPrice());
        adEntity.setDescription(ad.getDescription());
        return adEntity;
    }

    public AdEntity mapDtoToAdEntity(Ad ad) {
        AdEntity adEntity = new AdEntity();
        // TODO: дописать
        adEntity.setAuthor(null);
        adEntity.setDescription(null);

        adEntity.setTitle(ad.getTitle());
        adEntity.setPrice(ad.getPrice());
        adEntity.setImage(ad.getImage());
        return adEntity;
    }
}
