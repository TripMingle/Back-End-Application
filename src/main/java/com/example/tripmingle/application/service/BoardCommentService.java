package com.example.tripmingle.application.service;

import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.board.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardCommentReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardCommentPersistPort;
import com.example.tripmingle.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentPersistPort boardCommentPersistPort;
    private final UserUtils userUtils;
    private final RedissonClient redissonClient;

    public List<BoardComment> getBoardCommentsByBoardId(Long boardId) {
        return boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
    }


    public BoardComment createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO, Board board, User currentUser) {
        BoardComment parentBoardComment = getParentBoardComment(createBoardCommentReqDTO.getParentBoardCommentId());

        board.increaseCommentCount();

        return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                .parentBoardComment(parentBoardComment)
                .user(currentUser)
                .board(board)
                .content(createBoardCommentReqDTO.getContent())
                .build());
    }

    /*
    @Transactional
    public synchronized BoardComment createBoardCommentBySynchronized(CreateBoardCommentReqDTO createBoardCommentReqDTO, Board board, User currentUser) {
        BoardComment parentBoardComment = getParentBoardComment(createBoardCommentReqDTO.getParentBoardCommentId());

        board.increaseCommentCount();

        return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                .parentBoardComment(parentBoardComment)
                .user(currentUser)
                .board(board)
                .content(createBoardCommentReqDTO.getContent())
                .build());

    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BoardComment createBoardCommentBySerializable(CreateBoardCommentReqDTO createBoardCommentReqDTO, Board board, User currentUser) {
        BoardComment parentBoardComment = getParentBoardComment(createBoardCommentReqDTO.getParentBoardCommentId());

        board.increaseCommentCount();

        return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                .parentBoardComment(parentBoardComment)
                .user(currentUser)
                .board(board)
                .content(createBoardCommentReqDTO.getContent())
                .build());
    }

    private final BoardRepository boardRepository;

    @Transactional
    public BoardComment createBoardCommentWithPessimisticLock(CreateBoardCommentReqDTO createBoardCommentReqDTO, Long boardId, User currentUser) {
        // 비관적 락을 사용하여 Board 엔티티를 조회
        Board board = boardRepository.findByIdWithPessimisticLock(boardId);

        // 부모 댓글 설정
        BoardComment parentBoardComment = getParentBoardComment(createBoardCommentReqDTO.getParentBoardCommentId());

        // 댓글 수 증가
        board.increaseCommentCount();
        boardRepository.save(board);

        // 댓글 생성 및 저장
        return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                .parentBoardComment(parentBoardComment)
                .user(currentUser)
                .board(board)
                .content(createBoardCommentReqDTO.getContent())
                .build());
    }

    @Transactional
    public BoardComment createBoardCommentByRedisson(CreateBoardCommentReqDTO createBoardCommentReqDTO, Board board, User currentUser) {
        String lockKey = "boardCommentLock:" + board.getId();
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLockAcquired;

        try {
            isLockAcquired = lock.tryLock(10, 5, TimeUnit.SECONDS);

            if (isLockAcquired) {
                try {
                    BoardComment parentBoardComment = getParentBoardComment(createBoardCommentReqDTO.getParentBoardCommentId());

                    board.increaseCommentCount();

                    return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                            .parentBoardComment(parentBoardComment)
                            .user(currentUser)
                            .board(board)
                            .content(createBoardCommentReqDTO.getContent())
                            .build());
                } finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("Could not acquire lock for creating board comment.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lock acquisition interrupted.", e);
        }
    }
     */


    private boolean isParent(Long id) {
        return id == -1;
    }

    public int deleteBoardComment(Long commentId, User currentUser) {
        int commentCount = 0;
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        userUtils.validateMasterUser(boardComment.getUser().getId(), currentUser.getId());
        commentCount++;
        if (boardComment.isParentBoardCommentNull()) {
            List<BoardComment> childBoardComments = boardCommentPersistPort.getBoardCommentByParentBoardId(boardComment.getId());
            commentCount += childBoardComments.size();
            childBoardComments.forEach(boardCommentPersistPort::deleteBoardComment);
        }
        boardCommentPersistPort.deleteBoardComment(boardComment);

        return commentCount;
    }

    public BoardComment updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId, User currentUser) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        userUtils.validateMasterUser(boardComment.getUser().getId(),currentUser.getId());
        boardComment.update(updateBoardCommentReqDTO);

        return boardCommentPersistPort.saveBoardComment(boardComment);
    }


    public void deleteBoardCommentByBoardId(Long boardId) {
        List<BoardComment> boardComments = boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
        boardComments.forEach(boardCommentPersistPort::deleteBoardComment);
    }

    public Board getBoardByCommentId(Long commentId) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        return boardComment.getBoard();
    }



    private BoardComment getParentBoardComment(Long parentBoardCommentId) {
        return isParent(parentBoardCommentId) ? null : boardCommentPersistPort.getBoardCommentById(parentBoardCommentId);
    }

}
