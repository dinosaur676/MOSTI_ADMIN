package emblock.mosti.adapter.rdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import emblock.mosti.application.domain.Student;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Student.Builder.builder찾기(rs.getLong("user_id"))
                .userId(rs.getLong("user_id"))
                .name(rs.getString("name"))
                .studentId(rs.getString("student_id"))
                .school(rs.getString("school"))
                .major(rs.getString("major"))
                .createOn(rs.getTimestamp("created_on"))
                .updatedOn(rs.getTimestamp("updated_on"))
                .build()
                ;
    }
}