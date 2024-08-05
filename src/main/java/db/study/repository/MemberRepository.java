package db.study.repository;

import db.study.domain.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

import static db.study.dbconnector.DBConnector.*;

@Repository
@AllArgsConstructor
@Slf4j
public class MemberRepository {

    private DataSource dataSource;

    public void save(Member member) throws SQLException {
        String sql = "insert into user(user_id, password, name) values (?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            close(con, pstmt, rs, dataSource);
        }
    }

    public Optional<Member> findByMemberId(String memberId) throws SQLException {
        String sql = "select * from user where user_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            Member m = new Member();
            if(rs.next()){
                m.setId(rs.getString("user_id"));
                m.setPassword(rs.getString("password"));
                m.setName(rs.getString("name"));
                return Optional.of(m);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }
}
