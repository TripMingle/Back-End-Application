package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.board.UpdateBoardReqDTO;
import com.example.tripmingle.dto.req.board.CreateBoardReqDTO;
import com.example.tripmingle.dto.res.board.*;
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

    Page<GetBoardsResDTO> searchBoard(String keyword, Pageable pageable);

    void getBoardsWithinRange();

    ToggleStateResDTO toggleBoardBookMark(Long boardId);

    Page<GetBoardsResDTO> getMyBookMarkedBoards(Pageable pageable);

    ToggleStateResDTO toggleBoardLikes(Long boardId);

    Page<GetBoardsResDTO> getMyLikedBoards(Pageable pageable);

    Page<GetBoardsResDTO> getMyBoards(Pageable pageable);
}
