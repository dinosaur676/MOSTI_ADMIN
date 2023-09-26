package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.TokenInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenInfoRowMapper implements RowMapper<TokenInfo> {
    @Override
    public TokenInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TokenInfo(
                rs.getLong("token_id"),
                rs.getString("meta_data"),
                rs.getString("user_name"),
                rs.getTimestamp("created_on").toLocalDateTime()
        );
    }
}
