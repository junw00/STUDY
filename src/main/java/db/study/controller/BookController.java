package db.study.controller;


import db.study.dto.Result;
import db.study.dto.book.BookResponseDto;
import db.study.dto.member.MemberRequestDto;
import db.study.dto.member.MemberResponseDto;
import db.study.service.BookService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("book/{book_id}")
    public String bookDetailForm(@PathVariable(value = "book_id") int bookId, Model model, HttpSession session) {
        try {
            MemberResponseDto memberRequestDto = (MemberResponseDto) session.getAttribute("userInfo");
            BookResponseDto bookResponseDto = bookService.findBook(bookId).get();
            model.addAttribute("book", bookResponseDto);
            model.addAttribute("userInfo", memberRequestDto);
            return "/book/bookDetail";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
