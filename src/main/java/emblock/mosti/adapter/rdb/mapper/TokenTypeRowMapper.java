package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.TokenType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenTypeRowMapper implements RowMapper<TokenType> {
    @Override
    public TokenType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TokenType(
                rs.getInt("token_type"),
                rs.getString("description"));
    }
}
