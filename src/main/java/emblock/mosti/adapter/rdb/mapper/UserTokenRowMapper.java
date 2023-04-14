package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.adapter.ramda.dto.response.RamdaMintResponseDto;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.UserToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserTokenRowMapper implements RowMapper<UserToken> {
    @Override
    public UserToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        TokenInfo tokenInfo = new TokenInfo(
                rs.getLong("token_id"),
                rs.getString("type"),
                rs.getString("meta_data"),
                rs.getString("token_owner"),
                rs.getString("contract_type").charAt(0),
                rs.getTimestamp("created_on").toLocalDateTime()
        );

        return new UserToken(
                rs.getString("address"),
                tokenInfo,
                rs.getTimestamp("deleted_on").toLocalDateTime()
        );
    }
}
