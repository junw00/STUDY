package db.study.domain;

import lombok.*;

@Getter @Setter
public class CartItem {
    private int cartItemId;
    private int bookId;
    private int cartId;
    private int quantity;
}
