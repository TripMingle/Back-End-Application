package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.BoardBookMarkNotFoundException;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardBookMarkPersistPort;
import com.example.tripmingle.repository.BoardBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardBoardBookMarkPersistAdapter implements BoardBookMarkPersistPort {
    private final BoardBookMarkRepository boardBookMarkRepository;
    @Override
    public void saveBoardBookMark(BoardBookMark boardBookMark) {
        boardBookMarkRepository.save(boardBookMark);
    }

    @Override
    public boolean existsBoardBookMarkByUserAndBoard(User user, Board board) {
        return boardBookMarkRepository.existsBoardBookMarkByUserAndBoard(user, board);
    }

    @Override
    public BoardBookMark findByUserAndBoard(User user, Board board) {
        return boardBookMarkRepository.findBoardBookMarkByUserAndBoard(user,board)
                .orElseThrow(()-> new BoardBookMarkNotFoundException("board bookmark not found", ErrorCode.BOARD_BOOK_MARK_NOT_FOUND));
    }

    @Override
    public Page<BoardBookMark> getBoardBookMarksByUser(User currentUser, Pageable pageable) {
        return boardBookMarkRepository.findBoardBookMarksByUser(currentUser, pageable);
    }

    @Override
    public List<BoardBookMark> findBoardBookMarksByBoardId(Long boardId) {
        return boardBookMarkRepository.findBoardBookMarksByBoardId(boardId);
    }
}
