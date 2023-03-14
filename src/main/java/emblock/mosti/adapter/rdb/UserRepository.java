package emblock.mosti.adapter.rdb;

import emblock.framework.domain.SnowflakeIdGenerator;
import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.UserRowMapper;
import emblock.mosti.adapter.rdb.sql.UserRepositorySql;
import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.domain.User;
import io.netty.util.internal.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {
    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<User> jdbcQuery;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new UserRowMapper());
    }

    @Override
    public List<User> 목록조회() {
        return this.jdbcQuery.목록조회(UserRepositorySql.SQL_목록조회);
    }

    @Override
    public User 조회(String loginId) {
        return this.jdbcQuery.조회(UserRepositorySql.SQL_조회, loginId);
    }

    @Override
    public void 추가(User user) {
        this.jdbcCommand.실행(UserRepositorySql.SQL_추가
                , user.getUserId(), user.getLoginId(), user.getPassword(), user.getUserName(),
                user.getEmail(), user.getAddress(), user.getPhone(), user.getCellPhone(), user.getType().name(),
                user.getStatus().name(), user.getRoleId(),
                user.getCreatedOn(), user.getUpdatedOn());

    }

    @Override
    public void 수정(User user) {
        String 쿼리 = UserRepositorySql.SQL_수정;
        쿼리 = 쿼리.formatted(StringUtil.isNullOrEmpty(user.getPassword())? "": ", password = ? ");

        if(StringUtil.isNullOrEmpty(user.getPassword())){
            this.jdbcCommand.실행(쿼리
                    , user.getLoginId(), user.getUserName()
                    , user.getEmail(), user.getAddress(), user.getPhone(), user.getCellPhone(), user.getType().name()
                    , user.getStatus().name()
                    , user.getUpdatedOn()
                    , user.getUserId());
        }else{
            this.jdbcCommand.실행(쿼리
                    , user.getLoginId(), user.getPassword(), user.getUserName()
                    , user.getEmail(), user.getAddress(), user.getPhone(), user.getCellPhone(), user.getType().name()
                    , user.getStatus().name()
                    , user.getUpdatedOn()
                    , user.getUserId());
        }
    }

    @Override
    public void 삭제(long id) {

    }

    @Override
    public void 지갑정보수정(String walletId, String publicAddress, long userId) {
        this.jdbcCommand.실행(UserRepositorySql.SQL_지갑정보수정, walletId, publicAddress,LocalDateTime.now(), userId );
    }

    public static void  main(String[] args){
        System.out.println(SnowflakeIdGenerator.genId());
    }
}
