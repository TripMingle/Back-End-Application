package com.example.tripmingle.application.service;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.UserNotFoundException;
import com.example.tripmingle.dto.req.board.CreateBoardCommentReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.repository.BoardRepository;
import com.example.tripmingle.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@ActiveProfiles("test") // 'test' 프로파일을 활성화하여 테스트 환경 설정을 사용
//@Transactional
@Slf4j
public class BoardCommentServiceTest {

    @Autowired
    private BoardCommentService boardCommentService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    //@Transactional(isolation = Isolation.SERIALIZABLE)
    public void testAddComment() throws InterruptedException{
        int threadCount = 10; // 사용할 스레드 수
        int commentsPerThread = 10; // 각 스레드당 생성할 댓글 수

        // User와 Board 엔티티를 데이터베이스에서 조회
        User user = userRepository.findById(1L)
                .orElseThrow(()->new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND));
        //Board board = boardRepository.findById(6L)
        //        .orElseThrow(()->new BoardNotFoundException("board not found", ErrorCode.BOARD_NOT_FOUND));

        // CreateBoardCommentReqDTO 객체 생성 및 초기화
        CreateBoardCommentReqDTO DTO = new CreateBoardCommentReqDTO();
        DTO.setBoardId(6L);
        DTO.setParentBoardCommentId(-1L);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount); // 고정된 스레드 풀 생성
        CountDownLatch latch = new CountDownLatch(threadCount); // 모든 스레드가 작업을 완료할 때까지 기다리기 위한 CountDownLatch

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    for (int j = 0; j < commentsPerThread; j++) {// 댓글 생성 서비스 호출
                        //boardCommentService.createBoardCommentBySynchronized(DTO, board, user);
                        //boardCommentService.createBoardComment(DTO, board, user);
                        //boardCommentService.createBoardCommentBySerializable(DTO, board, user);
                        boardCommentService.createBoardCommentWithPessimisticLock(DTO, 6L, user);
                    }
                } finally {
                    latch.countDown(); // 작업 완료 후 CountDownLatch 감소
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 완료할 때까지 대기
        executorService.shutdown(); // ExecutorService 종료

        Board board = boardRepository.findByIdWithPessimisticLock(6L);

        int expectedCount = threadCount * commentsPerThread; // 예상 댓글 수
        int actualCount = board.getCommentCount(); // 실제 댓글 수


        System.out.println("expectedCount : " + expectedCount);
        System.out.println("actualCount : " + actualCount);

        // 예상 댓글 수와 실제 댓글 수가 일치하는지 확인
        assertEquals(expectedCount, actualCount, "The comment count should not match the expected value.");

    }


}