package db.study.controller;

import db.study.dto.Result;
import db.study.dto.address.AddressRequestDto;
import db.study.dto.member.MemberResponseDto;
import db.study.service.AddressService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address")
    public String addressForm() {

        return "/address/address";
    }

    @PostMapping("/address")
    public ResponseEntity<?> put(@ModelAttribute AddressRequestDto addressRequestDto, HttpSession session) {

        try {
            MemberResponseDto memberResponseDto = (MemberResponseDto) session.getAttribute("userInfo");
            addressService.submitAddress(addressRequestDto, memberResponseDto.getMemberId());
            // 리디렉션을 위해 HTTP 응답 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/cart"); // 리디렉션할 URL 설정
            return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found 상태 코드
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
