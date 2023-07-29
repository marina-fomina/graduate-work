package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;

public interface AdService {

    /**
     * Get all ads of current user
     *
     * @return instance of {@link AdsDTO} class that includes the number of ads of current user and their list
     */
    AdsDTO getUserAds();

    /**
     * Get all ads
     *
     * @return instance of {@link AdsDTO} class that includes the number of ads and their list
     */
    AdsDTO getAds();

    /**
     * Add an ad
     *
     * @param extendedAdDTO includes information about ad and its author
     * @return instance of {@link AdDTO} class that includes information about the ad
     * (primary key, author id, ad image, price and title)
     */
    AdDTO addAd(ExtendedAdDTO extendedAdDTO);

    /**
     * Delete an ad by its primary key
     *
     * @param username username of ad author
     * @param id       ad primary key
     * @return true (if ad was found and deleted) or false (if not)
     */
    boolean deleteAd(String username, Integer id);


    /**
     * Get ad by its primary key
     *
     * @param id ad primary key
     * @return instance of {@link ExtendedAdDTO} class that includes information about an ad and its author
     */
    ExtendedAdDTO getAdById(Integer id);

    /**
     * Change the information in the ad
     *
     * @param username            username of ad author
     * @param id                  primary key
     * @param createOrUpdateAdDTO includes information about title, price and description of the ad
     * @return instance of {@link AdDTO} class that includes information about ad (primary key, author id,
     * ad image, price and title)
     */
    AdDTO patchAd(String username, Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    /**
     * Update ad image
     *
     * @param id   ad primary key
     * @param file new image
     * @return UUID
     */
    String updateAdImage(Integer id, MultipartFile file);
}
