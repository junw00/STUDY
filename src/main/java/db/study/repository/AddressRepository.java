package db.study.repository;

import db.study.domain.Address;
import db.study.domain.Book;
import db.study.dto.address.AddressRequestDto;
import db.study.dto.address.AddressResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class AddressRepository {

    private final DataSource dataSource;

    public void saveAddress(AddressRequestDto addressRequestDto, String userId) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "insert into address(postal_code, basic_address, detail_address, user_id) values(?,?,?,?)";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, addressRequestDto.getPostalCode());
            pstmt.setString(2, addressRequestDto.getBasicAddress());
            pstmt.setString(3, addressRequestDto.getDetailAddress());
            pstmt.setString(4, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }

        public List<Address> findAddressByMemberId(String userId) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from address where user_id = ?";
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (rs.next()) {
                Address address = new Address();
                address.setAddressId(rs.getInt("adress_id"));
                address.setPostalCode(rs.getInt("postal_code"));
                address.setDetailAddress(rs.getString("detail_address"));
                address.setUserId(rs.getString("user_id"));
                address.setBasicAddress(rs.getString("basic_address"));
                addresses.add(address);
            }
            return addresses;
        } catch (SQLException e) {
            throw e;
        }finally {
            close(con, pstmt, rs, dataSource);
        }
    }
}
