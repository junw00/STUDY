package db.study.dto.card;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CardResponseDto {

    private String cardNum;
    private String valid;
    private String company;
    private String userId;
}
