package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mappers {

    public static UserToken getUserTokenMapper(ResultSet rs, int rowNum) throws SQLException {
        return new UserToken(
                rs.getString("account"),
                rs.getLong("token_id"),
                rs.getString("contract_type").charAt(0),
                rs.getTimestamp("created_on").toLocalDateTime(),
                rs.getTimestamp("deleted_on").toLocalDateTime()
        );
    }

    public static TokenInfo getTokenInfoMapper(ResultSet rs, int rowNum) throws SQLException {
        return new TokenInfo(
                rs.getLong("token_id"),
                rs.getString("type"),
                rs.getString("meta_data"),
                rs.getString("token_owner_address"),
                rs.getString("contract_type").charAt(0),
                rs.getTimestamp("created_on").toLocalDateTime()
        );
    }

    public static TokenType getTokenTypeMapper(ResultSet rs, int rowNum) throws SQLException {
        return new TokenType(
                rs.getInt("token_type"),
                rs.getString("description")
        );
    }
}
