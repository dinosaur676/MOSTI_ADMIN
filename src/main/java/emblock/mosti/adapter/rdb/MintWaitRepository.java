package emblock.mosti.adapter.rdb;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.MintWaitRowMapper;
import emblock.mosti.adapter.rdb.mapper.NoticeRowMapper;
import emblock.mosti.adapter.rdb.sql.MintWaitRepositorySql;
import emblock.mosti.application.domain.MintWait;
import emblock.mosti.application.domain.Notice;
import emblock.mosti.application.port.out.IMintWaitRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MintWaitRepository implements IMintWaitRepository {
    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<MintWait> jdbcQuery;

    public MintWaitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new MintWaitRowMapper());
    }

    @Override
    public List<MintWait> select(String userName) {
        return jdbcQuery.목록조회(MintWaitRepositorySql.select, userName);
    }

    @Override
    public MintWait selectByTokenId(String userName, long tokenId) {
        return jdbcQuery.조회(MintWaitRepositorySql.selectByTokenId, userName, tokenId);
    }

    @Override
    public void insert(String userName, long tokenId) {
        jdbcCommand.실행(MintWaitRepositorySql.insert, userName, tokenId, LocalDateTime.now());
    }

    @Override
    public void update(String userName, long tokenId) {
        jdbcCommand.실행(MintWaitRepositorySql.update, tokenId, userName);
    }
}
