package db.study.controller;

import db.study.domain.Member;
import db.study.dto.Result;
import db.study.dto.member.MemberRequestDto;
import db.study.dto.member.MemberResponseDto;
import db.study.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 페이지
     */
    @GetMapping("/member/new")
    public String createMemberForm() {
        return "/member/createMemberForm";
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/member")
    public String signinForm() {
        return "/member/validateMember";
    }

    /**
     * 회원가입
     */
    @PostMapping("/member/new")
    @ResponseBody
    public ResponseEntity<?> create(@ModelAttribute MemberRequestDto memberRequestDto) {
        try {
            memberService.join(memberRequestDto);
            // 리디렉션을 위해 HTTP 응답 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/"); // 리디렉션할 URL 설정
            return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found 상태 코드
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 로그인
     */
    @PostMapping("/member")
    @ResponseBody
    public ResponseEntity<?> signin(@RequestBody MemberRequestDto memberRequestDto, HttpSession session) {
        Optional<MemberResponseDto> memberResponseDto = null;
        HttpHeaders headers = null;
        try {
            memberResponseDto = memberService.findMember(memberRequestDto);
            if (memberResponseDto.isPresent()) {
                session.setAttribute("userInfo", memberResponseDto.get());
                return ResponseEntity.status(HttpStatus.FOUND).body(new Result<>("success", memberResponseDto));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result<>("fail", memberRequestDto));
        }
        return null;
    }

    /**
     * 로그아웃
     */
    @PostMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
