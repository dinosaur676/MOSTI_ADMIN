package emblock.mosti.adapter.rdb;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import emblock.framework.domain.SnowflakeIdGenerator;
import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.StudentRowMapper;
import emblock.mosti.adapter.rdb.sql.StudentRepositorySql;
import emblock.mosti.application.domain.Student;
import emblock.mosti.application.port.out.IStudentRepository;

@Repository
public class StudentRepository implements IStudentRepository {
    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<Student> jdbcQuery;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new StudentRowMapper());
    }

    @Override
    public List<Student> 목록조회() {
        return this.jdbcQuery.목록조회(StudentRepositorySql.SQL_목록조회);
    }

    @Override
    public Student 조회(long userId) {
        return this.jdbcQuery.조회(StudentRepositorySql.SQL_조회, userId);
    }

    @Override
    public Student 이름학번조회(String name, String studentId) {
        return this.jdbcQuery.조회(StudentRepositorySql.SQL_이름학번조회, name, studentId);
    }

    @Override
    public void 추가(Student student) {
        this.jdbcCommand.실행(StudentRepositorySql.SQL_추가
                , student.getUserId(), student.getUserName(), student.getStudentId()
                , student.getSchool(), student.getMajor()
                , student.getCreatedOn(), student.getUpdatedOn());

    }

    @Override
    public void 수정(Student student) {
        this.jdbcCommand.실행(StudentRepositorySql.SQL_수정
                , student.getUserId(), student.getUserName()
                , student.getSchool(), student.getMajor()
                , student.getUpdatedOn());

    }
  
    public static void  main(String[] args){
        System.out.println(SnowflakeIdGenerator.genId());
    }
}
