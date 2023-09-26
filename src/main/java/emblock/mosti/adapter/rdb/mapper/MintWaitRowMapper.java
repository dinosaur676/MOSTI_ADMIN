package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.MintWait;
import emblock.mosti.application.domain.TokenInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MintWaitRowMapper implements RowMapper<MintWait> {
    @Override
    public MintWait mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MintWait(
                rs.getString("user_name"),
                rs.getLong("token_id"),
                rs.getBoolean("enable"),
                rs.getTimestamp("created_on").toLocalDateTime()
        );
    }
}
