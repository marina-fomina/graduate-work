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
import ru.skypro.homework.utils.AdMapping;

import java.io.FileNotFoundException;
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


    @GetMapping
    public ResponseEntity<AdsDTO> getAds() {
        AdsDTO ads = adService.getAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<AdDTO> addAd(Authentication authentication,
                                       @RequestPart(name = "properties") CreateOrUpdateAdDTO createOrUpdateAdDTO,
                                       @RequestPart MultipartFile image) {
        System.out.println("TEST");
        // Сохранение image в репозиторий пользователя
        String imageLink = adService.saveImage(image);
//        Object principal = authentication.getPrincipal();
//        System.out.println(principal);
        ExtendedAdDTO extendedAdDTO = adMapping.mapCreateOrUpdateAdToExtendedAd(createOrUpdateAdDTO, imageLink);
        return new ResponseEntity<>(adService.addAd(extendedAdDTO), HttpStatus.CREATED);
    }
    // TODO: блокируется cors
    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(String path) {
        String filePath = path.substring(1);
        try {
            Image image = adService.getImage(filePath);
            return ResponseEntity.ok().contentType(image.getMediaType()).body(image.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable Integer id) {
        Optional<ExtendedAdDTO> extendedAdDTO = Optional.ofNullable(adService.getAdById(id));
        return extendedAdDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        return adService.deleteAd(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> patchAd(@PathVariable Integer id,
                                         @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        AdDTO adDTO = adService.patchAd(id, createOrUpdateAdDTO);
        return Objects.nonNull(adDTO) ? ResponseEntity.ok(adDTO) : ResponseEntity.notFound().build();
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
