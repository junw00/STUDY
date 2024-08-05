package db.study.repository;

import db.study.domain.Member;
import db.study.dto.cartItem.CartItemResponseDto;
import db.study.dto.order.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static db.study.dbconnector.DBConnector.close;
import static db.study.dbconnector.DBConnector.getConnection;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {

    private final DataSource dataSource;

    public void orderCartItem(List<CartItemResponseDto> cartItemResponseDtoList, OrderRequestDto orderRequestDto, String memberId, int cartId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            String sql = "insert into " +
                    "orders(card_number, price, card_company, card_valid, postal_code, basic_address, detail_address, order_date, user_id)" +
                    " values(?, ?, ?, ? ,? ,?, ?, now(), ?)";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, orderRequestDto.getCardNum());
            pstmt.setInt(2, orderRequestDto.getPrice());
            pstmt.setString(3, orderRequestDto.getCardCompany());
            pstmt.setString(4, orderRequestDto.getCardValid());
            pstmt.setInt(5, orderRequestDto.getPostalCode());
            pstmt.setString(6, orderRequestDto.getBasicAddress());
            pstmt.setString(7, orderRequestDto.getDetailAddress());
            pstmt.setString(8, memberId);
            pstmt.executeUpdate();

            try {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    log.info("orderId -> {}",orderId);
                    sql = "insert into orderItem(order_id, book_id, quantity) values (?, ?, ?)";
                    for (CartItemResponseDto cartItemResponseDto : cartItemResponseDtoList) {
                        pstmt = con.prepareStatement(sql);
                        pstmt.setInt(1, orderId);
                        pstmt.setInt(2, cartItemResponseDto.getBookId());
                        pstmt.setInt(3, cartItemResponseDto.getQuantity());
                        pstmt.executeUpdate();
                    }
                }
                try {
                    sql = "delete from cart where cart_id = ?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, cartId);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    throw e;
                }

            } catch (SQLException e) {
                throw e;
            }


        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }
}
