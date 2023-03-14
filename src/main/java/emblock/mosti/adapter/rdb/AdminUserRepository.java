package emblock.mosti.adapter.rdb;


import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.rdb.mapper.AdminUserRowMapper;
import emblock.mosti.adapter.rdb.sql.AdminUserRepositorySql;
import emblock.mosti.application.port.out.IAdminUserRepository;
import emblock.mosti.application.domain.AdminUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdminUserRepository implements IAdminUserRepository {
    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<AdminUser> jdbcQuery;

    public AdminUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new AdminUserRowMapper());
    }

    @Override
    public List<AdminUser> 목록조회() {
        return this.jdbcQuery.목록조회(AdminUserRepositorySql.SQL_목록조회);
    }

    @Override
    public AdminUser 조회(String 이름) {
        return this.jdbcQuery.조회(AdminUserRepositorySql.SQL_조회, 이름);
    }

    @Override
    public void 추가(AdminUser adminUser) {
        this.jdbcCommand.실행(AdminUserRepositorySql.SQL_추가,
            adminUser.getId(), adminUser.getName(), adminUser.getPassword(), adminUser.getEmail(),
            adminUser.getStatus(), LocalDateTime.now()
        );
    }

    @Override
    public void 수정(AdminUser adminUser) {
        this.jdbcCommand.실행(AdminUserRepositorySql.SQL_수정,
            adminUser.getName(), adminUser.getEmail(), adminUser.getStatus(),
            LocalDateTime.now(), adminUser.getId()
        );
    }

    @Override
    public void 삭제(String id) {
        this.jdbcCommand.실행(AdminUserRepositorySql.SQL_삭제, id);
    }

    @Override
    public void 암호수정(String id, String 암호) {
        this.jdbcCommand.실행(AdminUserRepositorySql.SQL_암호수정,
                암호, LocalDateTime.now(), id
        );
    }
}
