package db.study.repository;


import db.study.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static db.study.dbconnector.DBConnector.close;
import static db.study.dbconnector.DBConnector.getConnection;

@Repository
@Slf4j
public class BookRepository {

    private final DataSource dataSource;

    public BookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> findAll() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from book";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setBook_id(rs.getInt("book_id"));
                book.setBook_name(rs.getString("book_name"));
                book.setStock(rs.getInt("stock"));
                book.setPrice(rs.getInt("price"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw e;
        } finally {
            close(con, pstmt, rs, dataSource);
        }
    }

    public Optional<Book> findByBookId(int bookId) throws SQLException {
        String sql = "select * from book where book_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setBook_id(rs.getInt("book_id"));
                book.setBook_name(rs.getString("book_name"));
                book.setPrice(rs.getInt("price"));
                book.setStock(rs.getInt("stock"));

                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }

}
