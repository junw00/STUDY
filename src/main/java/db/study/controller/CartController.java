package db.study.controller;

import db.study.domain.Cart;
import db.study.domain.CartItem;
import db.study.dto.address.AddressResponseDto;
import db.study.dto.card.CardResponseDto;
import db.study.dto.cartItem.CartItemResponseDto;
import db.study.dto.member.MemberRequestDto;
import db.study.dto.member.MemberResponseDto;
import db.study.service.AddressService;
import db.study.service.CardService;
import db.study.service.CartItemService;
import db.study.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CardService cardService;
    private final AddressService addressService;
    /**
     * 장바구니 페이지
     */
    @GetMapping("/cart")
    public String cartForm(Model model, HttpSession session) {

        try {
            MemberResponseDto userInfo = (MemberResponseDto) session.getAttribute("userInfo");
            if(userInfo == null) {
                return "redirect:/member";
            }
            List<CardResponseDto> cards = cardService.findCards(userInfo.getMemberId());
            List<AddressResponseDto> address = addressService.findAddress(userInfo.getMemberId());
            model.addAttribute("address", address);
            model.addAttribute("cards", cards);
            model.addAttribute("userInfo", userInfo);
            Cart cart = cartService.findCart(userInfo).get();
            List<CartItemResponseDto> cartItems = cartItemService.findCartItems(cart);
            int total = 0;
            for(CartItemResponseDto cartItem : cartItems) {
                total += cartItem.getPrice() * cartItem.getQuantity();
                log.info("cartId -> {}, bookId -> {}", cartItem.getCartId(), cartItem.getBookId());
            }
            model.addAttribute("totalPrice", total);
            model.addAttribute("cartItems", cartItems);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "/cart/cart";
    }




}
