package db.study.controller;

import db.study.domain.Cart;
import db.study.dto.cartItem.CartItemResponseDto;
import db.study.dto.member.MemberResponseDto;
import db.study.dto.order.OrderRequestDto;
import db.study.service.CartItemService;
import db.study.service.CartService;
import db.study.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartItemService cartItemService;
    private final CartService cartService;

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<?> order(@RequestBody OrderRequestDto orderRequestDto, HttpSession session) {

        try {
            MemberResponseDto memberResponseDto = (MemberResponseDto) session.getAttribute("userInfo");
            Optional<Cart> cart = cartService.findCart(memberResponseDto);
            List<CartItemResponseDto> cartItems = cartItemService.findCartItems(cart.get());
            orderService.orderProcess(cartItems, orderRequestDto, memberResponseDto.getMemberId(), cart.get().getCartId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
