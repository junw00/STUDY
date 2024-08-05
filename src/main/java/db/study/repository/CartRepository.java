package db.study.repository;

import db.study.dbconnector.DBConnector;
import db.study.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static db.study.dbconnector.DBConnector.close;
import static db.study.dbconnector.DBConnector.getConnection;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final DataSource dataSource;

    public Optional<Cart> findByMemberId(String memberId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from cart where user_id = ?";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getString("user_id"));
                cart.setCreateDate(rs.getTimestamp("create_date"));

                return Optional.of(cart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }

    public void saveCart(String memberId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "insert into cart(user_id, create_date) values(?, now())";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }
}
