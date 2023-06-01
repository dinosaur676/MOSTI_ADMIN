package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.School;
import emblock.mosti.application.domain.TokenInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolRowMapper implements RowMapper<School> {

    @Override
    public School mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new School(
                rs.getString("school_name"),
                rs.getLong("school_token_id")
        );
    }
}