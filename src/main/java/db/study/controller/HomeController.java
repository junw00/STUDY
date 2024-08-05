package db.study.controller;

import db.study.dto.book.BookResponseDto;
import db.study.dto.member.MemberResponseDto;
import db.study.service.BookService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final BookService bookService;

    /**
     * Main Page, 전체 책 조회
     */
    @GetMapping("/")
    public String homeForm(Model model, HttpSession session) {
        List<BookResponseDto> bookResponseDto = null;

        try {
            MemberResponseDto userInfo = (MemberResponseDto) session.getAttribute("userInfo");
            if(userInfo != null) {
                model.addAttribute("userInfo", userInfo);
            }
            bookResponseDto = bookService.findBooks();
            model.addAttribute("books", bookResponseDto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "home";
    }
}
