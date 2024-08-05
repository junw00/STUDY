package db.study.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private String cardNum;
    private int price;
    private String cardCompany;
    private String cardValid;
    private int postalCode;
    private String basicAddress;
    private String detailAddress;
    private String userId;


}
