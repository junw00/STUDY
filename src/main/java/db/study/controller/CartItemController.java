package db.study.controller;

import db.study.domain.Cart;
import db.study.domain.CartItem;
import db.study.dto.Result;
import db.study.dto.book.BookResponseDto;
import db.study.dto.cartItem.CartItemRequestDto;
import db.study.dto.member.MemberRequestDto;
import db.study.dto.member.MemberResponseDto;
import db.study.service.BookService;
import db.study.service.CartItemService;
import db.study.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final BookService bookService;

    @PostMapping("/cartItem")
    @ResponseBody
    public ResponseEntity<?> put(@RequestBody CartItemRequestDto cartItemRequestDto, HttpSession session) {
        log.info("CartItem: {}",cartItemRequestDto.toString() );
        try {
            log.info("cartItemRequestDto -> {}", cartItemRequestDto);
            MemberResponseDto userInfo = (MemberResponseDto) session.getAttribute("userInfo");
            if (userInfo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("로그인 하세요");
            }
            Cart cart = cartService.findCart(userInfo).get();
            cartItemRequestDto.setCartId(cart.getCartId());
            Optional<CartItem> cartItem = cartItemService.findCartItem(cartItemRequestDto);
            if(cartItem.isEmpty()) {
                cartItemService.addCartItem(cartItemRequestDto);
            }else {
                BookResponseDto bookResponseDto = bookService.findBook(cartItemRequestDto.getBookId()).get();
                int bookInCart = cartItem.get().getQuantity() + cartItemRequestDto.getQuantity();
                if (bookInCart >= bookResponseDto.getStock()) {
                    cartItemRequestDto.setQuantity(bookResponseDto.getStock());
                    cartItemService.updateCartItem(cartItemRequestDto);
                }else {
                    cartItemRequestDto.setQuantity(bookInCart);
                    cartItemService.updateCartItem(cartItemRequestDto);
                }
            }
            return ResponseEntity.ok().body(new Result<>("success", true));
//            return ResponeEntity.ok().body()
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("실패");
        }
    }

    @PostMapping("/cartItem/update")
    public ResponseEntity<?> update(@RequestBody CartItemRequestDto cartItemRequestDto) {
        try {
            BookResponseDto bookResponseDto = bookService.findBook(cartItemRequestDto.getBookId()).get();
            if(cartItemRequestDto.getQuantity() >= bookResponseDto.getStock()) {
                cartItemRequestDto.setQuantity(bookResponseDto.getStock());
                cartItemService.updateCartItem(cartItemRequestDto);
            }else {
                cartItemService.updateCartItem(cartItemRequestDto);
            }
            return ResponseEntity.ok().body(new Result<>("success", true));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
