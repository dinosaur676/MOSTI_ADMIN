package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.UserToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTokenRowMapper implements RowMapper<UserToken> {
    @Override
    public UserToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserToken(
                rs.getLong("token_id"),
                rs.getLong("id"),
                rs.getString("meta_data"),
                rs.getString("wallet_name"),
                rs.getString("wallet_tag"),
                rs.getString("user_name"),
                rs.getTimestamp("created_on").toLocalDateTime(),
                rs.getTimestamp("deleted_on").toLocalDateTime()
        );
    }
}
