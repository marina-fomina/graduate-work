package ru.skypro.homework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class AdDTO {
    Integer author;
    Integer pk;
    String image;
    Integer price;
    String title;
}
