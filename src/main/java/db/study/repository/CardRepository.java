package db.study.repository;

import db.study.dbconnector.DBConnector;
import db.study.domain.Card;
import db.study.dto.card.CardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

import static db.study.dbconnector.DBConnector.close;
import static db.study.dbconnector.DBConnector.getConnection;

@Repository
@RequiredArgsConstructor
public class CardRepository {

    private final DataSource dataSource;

    public void saveCard(CardRequestDto cardRequestDto, String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "insert into card(card_num, card_valid, card_company, user_id) values(?, ?, ?, ?)";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, cardRequestDto.getCardNum());
            pstmt.setString(2, cardRequestDto.getValid());
            pstmt.setString(3, cardRequestDto.getCompany());

            pstmt.setString(4, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            close(con, pstmt, rs, dataSource);
        }
    }

    public List<Card> findCardByMemberId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from card where user_id = ?";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                Card card = new Card();
                card.setCardNum(rs.getString("card_num"));
                card.setValid(rs.getString("card_valid"));
                card.setCompany(rs.getString("card_company"));
                card.setUserId(rs.getString("user_id"));
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            throw e;
        } finally {
            close(con, pstmt, rs, dataSource);
        }
    }
}
