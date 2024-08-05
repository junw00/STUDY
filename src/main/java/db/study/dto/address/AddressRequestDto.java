package db.study.dto.address;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressRequestDto {

    private int postalCode;
    private String basicAddress;
    private String detailAddress;

}
