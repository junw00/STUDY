package db.study.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int bookId;
    private int quantity;
}
