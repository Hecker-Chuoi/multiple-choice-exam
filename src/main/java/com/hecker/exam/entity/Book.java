package com.hecker.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    int id;
    String title;
    int quantity;
    double price;
    double totalMoney;
}
