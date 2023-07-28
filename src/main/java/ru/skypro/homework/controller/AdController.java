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
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.utils.AdMapping;

import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdController {
    @Autowired
    AdService adService;
    @Autowired
    AdMapping adMapping;
    @Autowired
    ImageService imageService;


    @GetMapping
    public ResponseEntity<AdsDTO> getAds() {
        AdsDTO ads = adService.getAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<AdDTO> addAd(@RequestPart(name = "properties") CreateOrUpdateAdDTO createOrUpdateAdDTO,
                                       @RequestPart MultipartFile image) {
        // Сохранение image в репозиторий пользователя
        String imageLink = imageService.saveImage(image);
        ExtendedAdDTO extendedAdDTO = adMapping.mapCreateOrUpdateAdToExtendedAd(createOrUpdateAdDTO, imageLink);
        return new ResponseEntity<>(adService.addAd(extendedAdDTO), HttpStatus.CREATED);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(String id) {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok().contentType(image.getMediaType()).body(image.getBytes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable Integer id) {
        Optional<ExtendedAdDTO> extendedAdDTO = Optional.ofNullable(adService.getAdById(id));
        return extendedAdDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(Authentication authentication,
                                      @PathVariable Integer id) {
        String username = authentication.getName();
        return adService.deleteAd(username, id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> patchAd(Authentication authentication,
                                         @PathVariable Integer id,
                                         @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        String username = authentication.getName();
        AdDTO adDTO = adService.patchAd(username, id, createOrUpdateAdDTO);
        return Objects.nonNull(adDTO) ? ResponseEntity.ok(adDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getUserAd() {
        AdsDTO adsDTO = adService.getUserAds();
        return ResponseEntity.ok(adsDTO);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<String> updateImage(@PathVariable Integer id,
                                              @RequestParam MultipartFile image) {
        String imageLink = adService.updateAdImage(id, image);
        return imageLink == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(imageLink);
    }
}
