package emblock.framework.jdbc;

import emblock.framework.exception.RepositoryException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class JdbcQuery<T> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<T> rowMapper;

    public JdbcQuery(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcQuery(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<T> 목록조회(String sql, RowMapper<T> rowMapper, Object... args) {
        try {
            return this.jdbcTemplate.query(sql, rowMapper, args);
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    public T 조회(String sql, RowMapper<T> rowMapper, Object... args) {
        try {
            return this.jdbcTemplate.queryForObject(sql, rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    public List<T> 목록조회(String sql, Object... args) {
        try {
            return this.jdbcTemplate.query(sql, this.rowMapper, args);
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    public T 조회(String sql, Object... args) {
        try {
            return this.jdbcTemplate.queryForObject(sql, this.rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }
}
