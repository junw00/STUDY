package db.study.dto.address;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter @Setter
public class AddressResponseDto {
    private int addressId;
    private int postalCode;
    private String basicAddress;
    private String detailAddress;
    private String userId;
}
