package emblock.mosti.adapter.rdb;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.LogRowMapper;
import emblock.mosti.adapter.rdb.sql.LogSql;
import emblock.mosti.application.domain.Log;
import emblock.mosti.application.dto.request.log.LogRequestDto;
import emblock.mosti.application.dto.response.LogResponseDto;
import emblock.mosti.application.port.out.ILogRepository;
import io.netty.util.internal.StringUtil;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogRepository implements ILogRepository {

    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<Log> jdbcQuery;

    public LogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new LogRowMapper());
    }

    @Override
    public List<Log> 목록조회(LogRequestDto dto) {
        String sql = getSql(dto);
        return jdbcQuery.목록조회(sql, dto.startDate(), dto.endDate());
    }


    private String getSql(LogRequestDto dto) {
        String sql = LogSql.목록조회;

        if(!StringUtil.isNullOrEmpty(dto.userName())) {
            sql += LogSql.조건_유저이름.formatted(dto.userName());
        }
        if(!StringUtil.isNullOrEmpty(dto.label())) {
            sql += LogSql.조건_라벨.formatted(dto.label());
        }

        return sql;
    }

    @Override
    public void 추가(long userId, String label) {
        jdbcCommand.실행(LogSql.추가, userId, label, LocalDateTime.now());
    }
}
