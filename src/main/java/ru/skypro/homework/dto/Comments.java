package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Comments {
    private int count;
    private List comments = new ArrayList(); //после создания модели нужно указать тип списка
}
