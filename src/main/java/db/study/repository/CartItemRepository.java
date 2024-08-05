package db.study.repository;

import db.study.dbconnector.DBConnector;
import db.study.domain.Book;
import db.study.domain.CartItem;
import db.study.dto.cartItem.CartItemResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static db.study.dbconnector.DBConnector.close;
import static db.study.dbconnector.DBConnector.getConnection;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CartItemRepository {

    private final DataSource dataSource;

    public List<CartItemResponseDto> findCartItemByCartId(int cartId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from cartItem inner join book on cartItem.book_id = book.book_id where cartItem.cart_id = ?";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            rs = pstmt.executeQuery();
            List<CartItemResponseDto> cartItems = new ArrayList<>();
            while (rs.next()) {
                CartItemResponseDto cartItem = new CartItemResponseDto();
                cartItem.setCartItemId(rs.getInt("cartItem_id"));
                cartItem.setCartId(rs.getInt("cart_id"));
                cartItem.setBookId(rs.getInt("book_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setBookName(rs.getString("book_name"));
                cartItem.setPrice(rs.getInt("price"));
                cartItem.setImagePath("/images/book" + (cartItem.getBookId() - 3) + ".png");
                cartItems.add(cartItem);
            }
            return cartItems;
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }

    public Optional<CartItem> findCartItemByCartIdAndBookId(int cartId, int bookId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from cartItem where cart_id = ? and book_id = ?";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            pstmt.setInt(2, bookId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setBookId(rs.getInt("book_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setCartItemId(rs.getInt("cartItem_id"));
                cartItem.setCartId(rs.getInt("cart_id"));
                return Optional.of(cartItem);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }

    public void save(int cartId, int bookId, int quantity) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            String sql = "insert into cartItem(book_id, cart_id, quantity) values (?, ?, ?)";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, cartId);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);

        }
    }

    public Optional<CartItem> findBookByCartIdAndBookId(int cartId, int bookId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from cartItem where cart_id = ? and book_id = ?";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            pstmt.setInt(2, bookId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setBookId(rs.getInt("book_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setCartItemId(rs.getInt("cartItem_id"));

                return Optional.of(cartItem);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }

    public void updateCartItemByCartIdAndBookId(int quantity, int cartId, int bookId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "update cartItem set quantity = ? where cart_id = ? and book_id = ?";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);
            pstmt.setInt(3, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);

        }
    }

    public void updateCartItem() {

    }
}