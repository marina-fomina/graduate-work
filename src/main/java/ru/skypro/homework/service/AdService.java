package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Image;

import java.io.FileNotFoundException;

public interface AdService {
    AdsDTO getAds();

    AdDTO addAd(ExtendedAdDTO extendedAdDTO);

    AdDTO patchAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    String saveImage(MultipartFile file);

    ExtendedAdDTO getAdById(Integer id);

    boolean deleteAd(Integer id);

    String saveImage(Integer id, MultipartFile file);

    Image getImage(String path) throws FileNotFoundException;
}
