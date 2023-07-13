package ru.skypro.homework.model;

import lombok.Data;
import org.springframework.http.MediaType;

@Data
public class Image {
    private MediaType mediaType;
    private byte[] bytes;
}
