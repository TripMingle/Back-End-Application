package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.CompanionNotFound;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.Companion;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.CompanionPersistPort;
import com.example.tripmingle.repository.CompanionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanionPersistAdapter implements CompanionPersistPort {
    private final CompanionRepository companionRepository;

    @Override
    public void saveCompanion(Companion companion) {
        companionRepository.save(companion);
    }

    @Override
    public Companion getCompanionByBoardAndUser(Board board, User currentUser) {
        return companionRepository.findCompanionByBoardIdAndUserId(board.getId(),currentUser.getId())
                .orElseThrow(()-> new CompanionNotFound("companion not found", ErrorCode.COMPANION_NOT_FOUND));
    }

    @Override
    public void deleteCompanion(Companion companion) {
        companion.delete();
    }

    @Override
    public List<Companion> getCompanionsByBoardId(Long boardId) {
        return companionRepository.findCompanionsByBoardId(boardId);
    }

    @Override
    public Page<Companion> getCompanionsByUserId(Long userId, Pageable pageable) {
        return companionRepository.findCompanionsByUserId(userId,pageable);
    }

    @Override
    public boolean existsByUserIdAndBoardId(Long userId, Long boardId) {
        return companionRepository.existsByUserIdAndBoardId(userId,boardId);
    }

}
