package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.UpdateBoardReqDTO;
import com.example.tripmingle.dto.req.CreateBoardReqDTO;
import com.example.tripmingle.dto.res.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardUseCase {
    List<GetBoardsResDTO> getRecentBoards(String countryName);

    void getClusteredBoards();

    Page<GetBoardsResDTO> getAllBoards(String country, String gender, String language, Pageable pageable);

    GetBoardInfoResDTO getBoard(Long boardId);

    PostBoardResDTO createBoard(CreateBoardReqDTO createBoardReqDTO);

    UpdateBoardResDTO updateBoard(Long boardId, UpdateBoardReqDTO patchBoardReqDTO);

    void deleteBoard(Long boardId);

    List<GetBoardsResDTO> searchBoard(String keyword);

    void getBoardsWithinRange();

    ToggleStateResDTO toggleBoardBookMark(Long boardId);

    List<GetBoardsResDTO> getMyBookMarkedBoards();

    ToggleStateResDTO toggleBoardLikes(Long boardId);

    List<GetBoardsResDTO> getMyLikedBoards();
}
