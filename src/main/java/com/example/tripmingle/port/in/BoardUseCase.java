package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.PostBoardReqDTO;
import com.example.tripmingle.dto.res.GetBoardInfoResDTO;
import com.example.tripmingle.dto.res.GetBoardsResDTO;
import com.example.tripmingle.dto.res.PostBoardResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardUseCase {
    List<GetBoardsResDTO> getRecentBoards(String countryName);

    void getClusteredBoards();

    Page<GetBoardsResDTO> getAllBoards(String country, String gender, String language, Pageable pageable);

    GetBoardInfoResDTO getBoard(Long boardId);

    PostBoardResDTO createBoard(PostBoardReqDTO postBoardReqDTO);

    void updateBoard();

    void deleteBoard();

    void searchBoard();

    void getBoardsWithinRange();
}
