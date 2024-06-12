package com.example.tripmingle.entity;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgeRange {
    UNDER_TEN("0-9"),
    TEENS("10-19"),
    TWENTIES("20-29"),
    THIRTIES("30-39"),
    FORTIES("40-49"),
    FIFTIES("50-59"),
    SIXTIES("60-69"),
    SEVENTIES("70-79"),
    EIGHTIES("80-89"),
    NINETIES("90-99"),
    OVER_HUNDRED("100+");
    private final String range;
}
