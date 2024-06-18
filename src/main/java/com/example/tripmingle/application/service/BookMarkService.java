package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BookMark;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BookMarkPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final UserPersistPort userPersistPort;
    private final BookMarkPersistPort bookMarkPersistPort;
    public void toggleBookMark(Board board) {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        if(bookMarkPersistPort.existsBookMarkByUserAndBoard(currentUser,board)){
            bookMarkPersistPort.findByUserAndBoard(currentUser,board).toggleBookMark();
        }
        else{
            BookMark bookMark = BookMark.builder()
                    .user(currentUser)
                    .board(board)
                    .isActive(true)
                    .build();
            bookMarkPersistPort.saveBookMark(bookMark);
        }
    }


    public List<BookMark> getMyBookMarkedBoards(User user) {
        return bookMarkPersistPort.getBookMarksByUser(user);
    }
}
