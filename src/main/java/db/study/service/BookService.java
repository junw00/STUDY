package db.study.service;

import db.study.domain.Book;
import db.study.dto.book.BookResponseDto;
import db.study.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public List<BookResponseDto> findBooks() throws SQLException {
        List<Book> books = bookRepository.findAll();
        List<BookResponseDto> bookResponseDtos = new ArrayList<>();
        for(int i = 0; i < books.size(); i++) {
            BookResponseDto bookResponseDto = new BookResponseDto();
            bookResponseDto.setBookId(books.get(i).getBook_id());
            bookResponseDto.setBookName(books.get(i).getBook_name());
            bookResponseDto.setPrice(books.get(i).getPrice());
            bookResponseDto.setStock(books.get(i).getStock());
            bookResponseDto.setImagePath("/images/book" + (i+1) + ".png");
            bookResponseDtos.add(bookResponseDto);
        }
        return bookResponseDtos;
    }

    public Optional<BookResponseDto> findBook(int bookId) throws SQLException {
        Book book = bookRepository.findByBookId(bookId).get();
        return Optional.ofNullable(book.toDto());
    }

}
