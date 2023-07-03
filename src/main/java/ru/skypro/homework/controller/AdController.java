package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdService;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdController {
    @Autowired
    AdService adService;

    @GetMapping
    public ResponseEntity<Ads> getAds() {
        Ads ads = adService.getAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<Ad> addAd(Authentication authentication,
                                    @RequestParam MultipartFile image,
                                    @RequestParam Integer price,
                                    @RequestParam String title) {
        // Сохранение image в репозиторий пользователя
        String imageLink = adService.saveImage(image);

        // Формируем DTO
        Ad adDto = new Ad();
        adDto.setTitle(title);
        adDto.setPrice(price);
        adDto.setImage(imageLink);

        //Передача в service
        adService.addAd(adDto);
        return new ResponseEntity<>(adDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdById(@PathVariable Integer id) {
        ExtendedAd extendedAd = adService.getAdById(id);
        return ResponseEntity.ok(extendedAd);
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
    public ResponseEntity<Ad> patchAd(@PathVariable Integer id,
                                      @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        adService.patchAd(id, createOrUpdateAd);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getUserAd(Authentication authentication) {
        Ads ads = new Ads();
        return ResponseEntity.ok(ads);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<String> updateImage(@PathVariable Integer id,
                                               @RequestParam MultipartFile image) {
        String imageLink = adService.saveImage(id, image);
        return imageLink == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(imageLink);
    }
}
