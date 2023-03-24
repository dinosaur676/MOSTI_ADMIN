package emblock.mosti.adapter.rdb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import emblock.framework.domain.SnowflakeIdGenerator;
import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.TokenAccessAuthRowMapper;
import emblock.mosti.adapter.rdb.sql.TokenAccessAuthRepositorySql;
import emblock.mosti.application.domain.TokenAccessAuth;
import emblock.mosti.application.port.out.ITokenAccessAuthRepository;

@Repository
public class TokenAccessAuthRepository implements ITokenAccessAuthRepository {
    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<TokenAccessAuth> jdbcQuery;

    public TokenAccessAuthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new TokenAccessAuthRowMapper());
    }

    @Override
    public TokenAccessAuth 조회(long studentId) {
        return this.jdbcQuery.조회(TokenAccessAuthRepositorySql.SQL_조회, studentId);
    }

    @Override
    public void 추가(TokenAccessAuth student) {
        this.jdbcCommand.실행(TokenAccessAuthRepositorySql.SQL_추가
                , student.getId(), student.getAuthKey(), student.getStudentId()
                , student.getCreatedOn(), student.getExpiredOn());

    }
  
    public static void  main(String[] args){
        System.out.println(SnowflakeIdGenerator.genId());
    }
}
