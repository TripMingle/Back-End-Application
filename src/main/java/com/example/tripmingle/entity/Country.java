package com.example.tripmingle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String continent;
    private String country;
    private String capital;

    private String continentEnglish;
    private String countryEnglish;
    private String capitalEnglish;
}
