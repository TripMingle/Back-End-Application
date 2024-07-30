package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardSearchPort {
    void saveBoard(Board board);

    List<Long> searchBoard(String countryName, String keyword, Pageable pageable);

    void deleteBoard(Board board);

    void updateBoard(Board board);
}
