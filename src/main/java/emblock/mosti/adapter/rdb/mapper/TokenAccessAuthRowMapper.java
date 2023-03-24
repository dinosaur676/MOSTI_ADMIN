package emblock.mosti.adapter.rdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import emblock.mosti.application.domain.TokenAccessAuth;

public class TokenAccessAuthRowMapper implements RowMapper<TokenAccessAuth> {
    @Override
    public TokenAccessAuth mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TokenAccessAuth.Builder.builder찾기(rs.getLong("student_id"))
                .id(rs.getLong("id"))
                .authKey(rs.getString("auth_key"))
                .createOn(rs.getTimestamp("created_on"))
                .expiredOn(rs.getTimestamp("expired_on"))
                .build()
                ;
    }
}