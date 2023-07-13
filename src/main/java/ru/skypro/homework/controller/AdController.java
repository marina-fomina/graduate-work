package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.service.AdService;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdController {

    @Autowired
    AdService adService;


    @GetMapping
    public ResponseEntity<AdsDTO> getAds() {
        AdsDTO ads = adService.getAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<AdDTO> addAd(Authentication authentication,
                                       @RequestParam MultipartFile image,
                                       @RequestParam Integer price,
                                       @RequestParam String title) {
        // Сохранение image в репозиторий пользователя
        String imageLink = adService.saveImage(image);

        // Формируем DTO
        AdDTO adDto = new AdDTO();
        adDto.setTitle(title);
        adDto.setPrice(price);
        adDto.setImage(imageLink);

        //Передача в service
        adService.addAd(adDto);
        return new ResponseEntity<>(adDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable Integer id) {
        ExtendedAdDTO extendedAdDTO = adService.getAdById(id);
        return ResponseEntity.ok(extendedAdDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (adService.deleteAd(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> patchAd(@PathVariable Integer id,
                                         @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        adService.patchAd(id, createOrUpdateAdDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getUserAd(Authentication authentication) {
        AdsDTO ads = new AdsDTO();
        return ResponseEntity.ok(ads);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<String> updateImage(@PathVariable Integer id,
                                               @RequestParam MultipartFile image) {
        String imageLink = adService.saveImage(id, image);
        return imageLink == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(imageLink);
    }
}
