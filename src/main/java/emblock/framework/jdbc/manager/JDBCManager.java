package emblock.framework.jdbc.manager;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.Mappers;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JDBCManager {

    private JdbcTemplate jdbcTemplate;
    private final JdbcCommand jdbcCommand;

    public JDBCManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
    }

    public JdbcCommand getJdbcCommand() {
        return this.jdbcCommand;
    }

    public <T> JdbcQuery<T> getJdbcQuery(Class<T> type) {
        return new JdbcQuery<>(jdbcTemplate, new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return getMapRow(type, rs, rowNum);
            }
        });
    }

    private <T> T getMapRow(Class<T> type, ResultSet rs, int rowNum) throws SQLException {
        if (type.equals(UserToken.class)) {
            return (T) Mappers.getUserTokenMapper(rs, rowNum);
        }
        else if (type.equals(TokenInfo.class)) {
            return (T) Mappers.getTokenInfoMapper(rs, rowNum);
        }
        else if (type.equals(TokenType.class)) {
            return (T) Mappers.getTokenTypeMapper(rs, rowNum);
        }

        return null;
    }
}
