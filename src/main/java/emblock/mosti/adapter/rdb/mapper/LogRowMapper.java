package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.Log;
import emblock.mosti.application.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogRowMapper implements RowMapper<Log> {
    @Override
    public Log mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Log(rs.getLong("user_id"),
                rs.getString("login_id"),
                rs.getString("user_name"),
                rs.getString("label"),
                User.UserType.valueOf(rs.getString("type")),
                User.UserStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_on").toLocalDateTime());
    }
}
