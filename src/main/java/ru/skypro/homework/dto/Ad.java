package ru.skypro.homework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ad {
    Integer author;
    Integer pk;
    String image;
    Integer price;
    String title;
}
