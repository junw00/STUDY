package db.study.domain;

import db.study.dto.address.AddressResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Address {
    private int addressId;
    private int postalCode;
    private String basicAddress;
    private String detailAddress;
    private String userId;

    public AddressResponseDto toEntity() {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setAddressId(addressId);
        addressResponseDto.setPostalCode(postalCode);
        addressResponseDto.setDetailAddress(detailAddress);
        addressResponseDto.setBasicAddress(basicAddress);
        addressResponseDto.setUserId(userId);
        return addressResponseDto;
    }
}
