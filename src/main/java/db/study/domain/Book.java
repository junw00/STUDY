package db.study.domain;

import db.study.dto.book.BookResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Book {
    private int book_id;
    private String book_name;
    private int stock;
    private int price;

    public BookResponseDto toDto() {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setBookId(book_id);
        bookResponseDto.setBookName(book_name);
        bookResponseDto.setStock(stock);
        bookResponseDto.setPrice(price);
        bookResponseDto.setImagePath("/images/book" + (book_id - 3) + ".png");
        return bookResponseDto;
    }
}
