package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Account(rs.getLong("user_id"),
                rs.getString("key"),
                rs.getString("address"),
                rs.getTimestamp("created_on").toLocalDateTime());
    }
}
