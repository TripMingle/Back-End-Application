package com.example.tripmingle.application;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.repository.BoardCommentRepository;
import com.example.tripmingle.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class PessimisticLockTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        executorService.execute(()-> pessimisticLock());

        executorService.execute(()-> {
            Board board = boardRepository.findById(4L)
                    .orElseThrow(()-> new RuntimeException("board not found")); // 비관적 락이 아닌 트랜젝션 시작
            board.increaseCommentCount(); //
        });


        executorService.shutdown();

    }

    @Transactional
    void pessimisticLock(){
        try {
            boardRepository.findByIdWithPessimisticLock(4L); //비관적 락 트랜젝션 시작
            Thread.sleep(10000); // 비관적 락을 건 이후 10초간 대기
        }
        catch (InterruptedException e) {}
    }


}
