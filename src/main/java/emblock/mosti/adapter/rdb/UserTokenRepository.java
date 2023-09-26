package emblock.mosti.adapter.rdb;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.UserTokenRowMapper;
import emblock.mosti.adapter.rdb.sql.UserTokenRepositorySql;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.port.out.IUserTokenRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class UserTokenRepository implements IUserTokenRepository {

    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<UserToken> jdbcQuery;

    public UserTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new UserTokenRowMapper());
    }
    @Override
    public List<UserToken> select(String walletName, String walletTag, String userName) {
        return jdbcQuery.목록조회(UserTokenRepositorySql.select, walletName, walletTag, userName);
    }

    @Override
    public void delete(String userName, String walletName, String walletTag, long tokenId) {
        jdbcCommand.실행(UserTokenRepositorySql.delete, userName, walletName, walletTag, tokenId);
    }

    @Override
    public void insert(String userName, String walletName, String walletTag, long tokenId, LocalDateTime createdOn, LocalDateTime deletedOn) {
        jdbcCommand.실행(UserTokenRepositorySql.insert, userName, walletName, walletTag, tokenId, createdOn, deletedOn);
    }
}
