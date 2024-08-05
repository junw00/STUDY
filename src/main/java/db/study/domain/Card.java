package db.study.domain;

import db.study.dto.card.CardResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Card {

    private String cardNum;
    private String valid;
    private String company;
    private String userId;

    public CardResponseDto toDto() {
        CardResponseDto cardResponseDto = new CardResponseDto();
        cardResponseDto.setCardNum(cardNum);
        cardResponseDto.setValid(valid);
        cardResponseDto.setUserId(userId);
        cardResponseDto.setCompany(company);
        return cardResponseDto;
    }
}
