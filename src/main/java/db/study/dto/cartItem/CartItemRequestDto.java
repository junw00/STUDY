package db.study.dto.cartItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class CartItemRequestDto {
    private int bookId;
    private int cartId;
    private int quantity;
}
