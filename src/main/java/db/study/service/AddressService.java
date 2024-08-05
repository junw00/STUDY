package db.study.service;

import db.study.domain.Address;
import db.study.dto.address.AddressRequestDto;
import db.study.dto.address.AddressResponseDto;
import db.study.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public void submitAddress(AddressRequestDto addressRequestDto, String userId) throws SQLException {
        addressRepository.saveAddress(addressRequestDto, userId);
    }

    public List<AddressResponseDto> findAddress(String memberId) throws SQLException {
        List<Address> addressByMemberId = addressRepository.findAddressByMemberId(memberId);
        List<AddressResponseDto> addressResponseDtos = new ArrayList<>();
        for (Address address : addressByMemberId) {
            AddressResponseDto entity = address.toEntity();
            addressResponseDtos.add(entity);
        }

        return addressResponseDtos;
    }

}
