package db.study.dbconnector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public abstract class DBConnector {

    public static Connection getConnection(DataSource dataSource) {
        return DataSourceUtils.getConnection(dataSource);
    }

    public static void close(Connection con, DataSource dataSource) throws SQLException{
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    public static void close(Connection con, Statement statement, ResultSet rs, DataSource dataSource) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }

        if (con != null) {
            try {
                close(con, dataSource);
            } catch (SQLException e) {
                log.error("error", e);
            }
        }
    }
}
