package emblock.framework.jdbc;

import emblock.framework.exception.RepositoryException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;

public class JdbcCommand {
    private JdbcTemplate jdbcTemplate;
    public JdbcCommand(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int 실행(String sql, Object... args) {
        try {
            return this.jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }
}
