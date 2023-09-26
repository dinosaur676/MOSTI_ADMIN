package emblock.mosti.adapter.rdb;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.TokenInfoRowMapper;
import emblock.mosti.adapter.rdb.sql.TokenInfoRepositorySql;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.port.out.ITokenInfoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TokenInfoRepository implements ITokenInfoRepository {

    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<TokenInfo> jdbcQuery;

    public TokenInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new TokenInfoRowMapper());
    }
    @Override
    public List<TokenInfo> select(String userName) {
        return jdbcQuery.목록조회(TokenInfoRepositorySql.select, userName);
    }

    @Override
    public TokenInfo selectByTokenId(long tokenId) {
        return jdbcQuery.조회(TokenInfoRepositorySql.selectByTokenId, tokenId);
    }

    @Override
    public void insert(long tokenId, String metaData, String userName, LocalDateTime createdOn) {
        jdbcCommand.실행(TokenInfoRepositorySql.insert, tokenId, metaData, userName, createdOn);
    }
}
