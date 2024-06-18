package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BookMark;
import com.example.tripmingle.entity.User;

import java.util.List;

public interface BookMarkPersistPort {

    void saveBookMark(BookMark bookMark);

    boolean existsBookMarkByUserAndBoard(User user, Board board);

    BookMark findByUserAndBoard(User user, Board board);

    List<BookMark> getBookMarksByUser(User currentUser);
}
