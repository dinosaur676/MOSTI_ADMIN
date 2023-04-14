package emblock.mosti.adapter.rdb;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.SchoolRowMapper;
import emblock.mosti.adapter.rdb.sql.SchoolRepositorySQL;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.School;
import emblock.mosti.application.port.out.ISchoolRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolRepository implements ISchoolRepository {

    JdbcQuery<School> jdbcQuery;
    JdbcCommand jdbcCommand;

    public SchoolRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new SchoolRowMapper());
    }

    @Override
    public School 학교이름으로조회(String schoolName) {
        return jdbcQuery.조회(SchoolRepositorySQL.학교이름으로조회, schoolName);
    }

    @Override
    public School 유저아이디로조회(long userId) {
        return jdbcQuery.조회(SchoolRepositorySQL.유저아이디로조회, userId);
    }

    @Override
    public void 추가(School school) {
        jdbcCommand.실행(SchoolRepositorySQL.추가, school.getUserId(), school.getSchoolName(), school.getSchoolTokenId(), school.getCreatedOn());
    }

    @Override
    public School 인증(Account account) {
        return jdbcQuery.조회(SchoolRepositorySQL.인증, account.getAddress());
    }
}
