package db.study.controller;

import db.study.dto.Result;
import db.study.dto.card.CardRequestDto;
import db.study.dto.card.CardResponseDto;
import db.study.dto.member.MemberResponseDto;
import db.study.service.CardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CardController {

    private final CardService cardService;

    @GetMapping("/card")
    public String cardForm() {


        return "/card/card";
    }

    @PostMapping("/card")
    @ResponseBody
    public ResponseEntity<?> put(@ModelAttribute CardRequestDto cardRequestDto, HttpSession session) {

        try {
            MemberResponseDto memberResponseDto = (MemberResponseDto) session.getAttribute("userInfo");
            cardService.addCard(cardRequestDto, memberResponseDto.getMemberId());
            // 리디렉션을 위해 HTTP 응답 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/cart"); // 리디렉션할 URL 설정
            return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found 상태 코드
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getCard(String userId) {
        List<CardResponseDto> cards = null;
        try {
             cards = cardService.findCards(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(new Result<>("success", cards));
    }

}
