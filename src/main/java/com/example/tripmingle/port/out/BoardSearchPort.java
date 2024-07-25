package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;

import java.util.List;

public interface BoardSearchPort {
    void saveBoard(Board board);

    List<Long> searchBoard(String countryName, String keyword);
}
