package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.UserToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserTokenRowMapper implements RowMapper<UserToken> {
    @Override
    public UserToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserToken(
                rs.getString("account"),
                rs.getLong("token_id"),
                rs.getString("contract_type").charAt(0),
                rs.getTimestamp("created_on").toLocalDateTime(),
                rs.getTimestamp("deleted_on").toLocalDateTime()
        );
    }
}
