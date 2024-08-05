package db.study.service;

import db.study.domain.Cart;
import db.study.dto.member.MemberRequestDto;
import db.study.dto.member.MemberResponseDto;
import db.study.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    public Optional<Cart> findCart(MemberResponseDto memberRequestDto) throws SQLException {

        Optional<Cart> cart = cartRepository.findByMemberId(memberRequestDto.getMemberId());
        if (cart.isEmpty()) {
            createCart(memberRequestDto);
            return cartRepository.findByMemberId(memberRequestDto.getMemberId());
        }
        return cart;
    }

    public void createCart(MemberResponseDto memberResponseDto) throws SQLException {
        cartRepository.saveCart(memberResponseDto.getMemberId());
    }

}
