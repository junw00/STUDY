package db.study.dto.cartItem;

import db.study.domain.CartItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemResponseDto {
    private int cartItemId;
    private int bookId;
    private int cartId;
    private int quantity;
    private String bookName;
    private int stock;
    private int price;
    private String imagePath;
    private int totalPrice;

}
