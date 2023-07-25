package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Image;

import java.io.FileNotFoundException;

public interface AdService {
    /**
     * Get all ads for current user
     *
     * @return the number of ads and their list
     */
    AdsDTO getUserAds();

    /**
     * Get all ads
     *
     * @return the number of ads and their list
     */
    AdsDTO getAds();

    /**
     * Add Ad
     *
     * @param extendedAdDTO includes information about ad and author
     * @return information about ad (primary key, author (id), image, price, title)
     */
    AdDTO addAd(ExtendedAdDTO extendedAdDTO);

    /**
     * Changing the information in the ad
     *
     * @param id                  primary key
     * @param createOrUpdateAdDTO includes information about name, price and description of the ad
     * @return information about ad (primary key, author (id), image, price, title)
     */
    AdDTO patchAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    /**
     * Get ad by primary key
     *
     * @param id primary key
     * @return information about ad and author
     */
    ExtendedAdDTO getAdById(Integer id);

    /**
     * Deleting an ad by primary key
     *
     * @param id primary key
     * @return true or false
     */
    boolean deleteAd(Integer id);

    String updateAdImage(Integer id, MultipartFile file);
}
