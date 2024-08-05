package db.study.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Order {
    private int orderId;
    private String cardNum;
    private int price;
    private String cardCompany;
    private String cardValid;
    private int postalCode;
    private String basicAddress;
    private String detailAddress;
    private Timestamp orderDate;
    private String userId;
}
