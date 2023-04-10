package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.TokenInfo;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenInfoRowMapper implements RowMapper<TokenInfo> {

    @Override
    public TokenInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TokenInfo(
                rs.getLong("token_id"),
                rs.getLong("type"),
                rs.getString("meta_data"),
                rs.getString("token_owner_address"),
                rs.getString("contract_type").charAt(0),
                rs.getTimestamp("created_on").toLocalDateTime()
        );
    }
}
