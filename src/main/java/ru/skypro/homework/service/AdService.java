package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

public interface AdService {
    Ads getAds();

    void addAd(Ad ad);

    Ad patchAd(Integer id, CreateOrUpdateAd createOrUpdateAd);

    String saveImage(MultipartFile file);

    ExtendedAd getAdById(Integer id);

    boolean deleteAd(Integer id);

    String saveImage(Integer id, MultipartFile file);
}
