package db.study.dto.book;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BookResponseDto {

    private int bookId;
    private String bookName;
    private int stock;
    private int price;
    private String imagePath;
}
