package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.BoardLikesNotFoundException;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardLikesPersistPort;
import com.example.tripmingle.repository.BoardLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardBoardLikesPersistAdapter implements BoardLikesPersistPort {
    private final BoardLikesRepository boardLikesRepository;
    @Override
    public boolean existsBoardBookMarkByUserAndBoard(User currentUser, Board board) {
        return boardLikesRepository.existsBoardBookMarkByUserAndBoard(currentUser,board);
    }

    @Override
    public BoardLikes findByUserAndBoard(User currentUser, Board board) {
        return boardLikesRepository.findByUserAndBoard(currentUser,board)
                .orElseThrow(()-> new BoardLikesNotFoundException("board likes not found", ErrorCode.BOARD_LIKES_NOT_FOUND));
    }

    @Override
    public void saveBoardLikes(BoardLikes boardLikes) {
        boardLikesRepository.save(boardLikes);
    }

    @Override
    public List<BoardLikes> findBoardLikesByUser(User user) {
        return boardLikesRepository.findBoardLikesByUser(user);
    }
}
