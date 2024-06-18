package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.BookMarkNotFoundException;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BookMark;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BookMarkPersistPort;
import com.example.tripmingle.repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMarkPersistAdapter implements BookMarkPersistPort {
    private final BookMarkRepository bookMarkRepository;
    @Override
    public void saveBookMark(BookMark bookMark) {
        bookMarkRepository.save(bookMark);
    }

    @Override
    public boolean existsBookMarkByUserAndBoard(User user, Board board) {
        return bookMarkRepository.existsBookMarkByUserAndBoard(user, board);
    }

    @Override
    public BookMark findByUserAndBoard(User user, Board board) {
        return bookMarkRepository.findBookMarkByUserAndBoard(user,board)
                .orElseThrow(()-> new BookMarkNotFoundException("bookmark not found", ErrorCode.BOOK_MARK_NOT_FOUND));
    }

    @Override
    public List<BookMark> getBookMarksByUser(User currentUser) {
        return bookMarkRepository.findBookMarksByUser(currentUser);
    }
}
