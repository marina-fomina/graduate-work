package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;

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
     * Deleting an ad by primary key
     *
     * @param username username of ad author
     * @param id       primary key
     * @return true (if ad was found and deleted) or false (if not)
     */
    boolean deleteAd(String username, Integer id);


    /**
     * Get ad by primary key
     *
     * @param id primary key
     * @return information about ad and author
     */
    ExtendedAdDTO getAdById(Integer id);

    /**
     * Changing the information in the ad
     *
     * @param username            username of ad author
     * @param id                  primary key
     * @param createOrUpdateAdDTO includes information about name, price and description of the ad
     * @return information about ad (primary key, author (id), image, price, title)
     */
    AdDTO patchAd(String username, Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    /**
     * Update ad image
     *
     * @param id   primary key of ad
     * @param file new image
     * @return UUID
     */
    String updateAdImage(Integer id, MultipartFile file);
}
