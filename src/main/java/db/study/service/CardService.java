package db.study.service;

import db.study.domain.Card;
import db.study.dto.card.CardRequestDto;
import db.study.dto.card.CardResponseDto;
import db.study.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public void addCard(CardRequestDto cardRequestDto, String userId) throws SQLException {
        cardRepository.saveCard(cardRequestDto, userId);
    }

    public List<CardResponseDto> findCards(String userId) throws SQLException {
        List<Card> cardByMemberId = cardRepository.findCardByMemberId(userId);
        List<CardResponseDto> cardResponseDtos = new ArrayList<>();
        for (Card card : cardByMemberId) {
            cardResponseDtos.add(card.toDto());
        }
        return cardResponseDtos;
    }

}
