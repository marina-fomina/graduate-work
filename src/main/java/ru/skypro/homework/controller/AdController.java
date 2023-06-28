package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdController {
    @GetMapping
    private ResponseEntity<Ads> getAds() {
        Ads ads = new Ads();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    private ResponseEntity<Ad> addAd(Authentication authentication,
                                     @RequestParam MultipartFile image,
                                     @RequestParam Double price,
                                     @RequestParam String title) {
        Ad ad = new Ad();
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ExtendedAd> getAdFromId(@PathVariable Integer id) {
        ExtendedAd extendedAd = new ExtendedAd();
        return ResponseEntity.ok(extendedAd);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Ad> patchAd(@PathVariable Integer id,
                                       @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    private ResponseEntity<Ads> getUserAd(Authentication authentication) {
        Ads ads = new Ads();
        return ResponseEntity.ok(ads);
    }

    @PatchMapping("/{id}/image")
    private ResponseEntity<String> updateImage(@PathVariable Integer id,
                                               @RequestParam MultipartFile image) {
        return ResponseEntity.ok().build();
    }
}
