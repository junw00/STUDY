package db.study.service;

import db.study.domain.Book;
import db.study.domain.Cart;
import db.study.domain.CartItem;
import db.study.dto.cartItem.CartItemRequestDto;
import db.study.dto.cartItem.CartItemResponseDto;
import db.study.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItemResponseDto> findCartItems(Cart cart) throws SQLException {
        return cartItemRepository.findCartItemByCartId(cart.getCartId());
    }

    public void addCartItem(CartItemRequestDto cartItemRequestDto) throws SQLException {
        cartItemRepository.save(cartItemRequestDto.getCartId(), cartItemRequestDto.getBookId(), cartItemRequestDto.getQuantity());
    }

    public Optional<CartItem> findCartItem(CartItemRequestDto cartItemRequestDto) throws SQLException {
        return cartItemRepository.findCartItemByCartIdAndBookId(cartItemRequestDto.getCartId(), cartItemRequestDto.getBookId());
    }

    public void updateCartItem(CartItemRequestDto cartItemRequestDto) throws SQLException {
        cartItemRepository.updateCartItemByCartIdAndBookId(
                cartItemRequestDto.getQuantity(),
                cartItemRequestDto.getCartId(),
                cartItemRequestDto.getBookId());
    }

}
