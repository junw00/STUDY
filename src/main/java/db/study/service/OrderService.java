package db.study.service;

import db.study.dto.cartItem.CartItemResponseDto;
import db.study.dto.order.OrderRequestDto;
import db.study.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void orderProcess(List<CartItemResponseDto> cartItemResponseDtoList, OrderRequestDto orderRequestDto, String memberId, int cartId) throws SQLException {
        orderRepository.orderCartItem(cartItemResponseDtoList, orderRequestDto, memberId, cartId);
    }
}
