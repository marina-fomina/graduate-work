package ru.skypro.homework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdDTO {
    Integer author;
    Integer pk;
    String image;
    Integer price;
    String title;
}
