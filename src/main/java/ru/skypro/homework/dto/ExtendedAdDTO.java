package ru.skypro.homework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExtendedAdDTO {
    Integer pk;
    String authorFirstName;
    String authorLastName;
    String description;
    String email;
    String image;
    String phone;
    Integer price;
    String title;
}
