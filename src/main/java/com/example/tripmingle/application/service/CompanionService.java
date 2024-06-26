package com.example.tripmingle.application.service;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.LeaderCannotLeaveException;
import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.Companion;
import com.example.tripmingle.entity.Position;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.CompanionPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanionService {
    private final UserUtils userUtils;
    private final CompanionPersistPort companionPersistPort;

    public void registerLeader(Board board, User user){
        Companion companion = Companion.builder()
                .user(user)
                .board(board)
                .position(Position.LEADER)
                .build();
        companionPersistPort.saveCompanion(companion);
    }

    public void confirmUsers(Board board, User currentUser, List<User> userList) {
        userUtils.validateMasterUser(board.getUser().getId(),currentUser.getId());

        userList.forEach(user ->{
            if (!user.getId().equals(board.getUser().getId())) {
                Companion companion = Companion.builder()
                        .user(user)
                        .board(board)
                        .position(Position.MEMBER)
                        .build();
                companionPersistPort.saveCompanion(companion);
            }
        }
        );

    }


    public void leaveCompanion(Board board, User currentUser) {
        Companion companion = companionPersistPort.getCompanionByBoardAndUser(board,currentUser);
        if(companion.getPosition().equals(Position.LEADER)){
            throw new LeaderCannotLeaveException("leader can't leave.", ErrorCode.LEADER_CANT_LEAVE);
        }
        companionPersistPort.deleteCompanion(companion);
    }

    public List<Companion> getCompanionsByBoardId(Long boardId) {
        return companionPersistPort.getCompanionsByBoardId(boardId);
    }

    public Page<Companion> getCompanionsByUser(User currentUser, Pageable pageable) {
        return companionPersistPort.getCompanionsByUserId(currentUser.getId(),pageable);
    }

    public boolean isParticipatingBoard(User currentUser, Board board) {
        return companionPersistPort.existsByUserIdAndBoardId(currentUser.getId(),board.getId());
    }
}
