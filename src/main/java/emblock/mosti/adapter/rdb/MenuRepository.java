package emblock.mosti.adapter.rdb;

import emblock.mosti.adapter.rdb.mapper.ApiRoleMappingRowMapper;
import emblock.mosti.adapter.rdb.mapper.MenuRoleMappingRowMapper;
import emblock.mosti.application.domain.ApiRoleMapping;
import emblock.mosti.application.domain.MenuRoleMapping;
import emblock.mosti.application.port.out.IMenuRepository;
import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuRepository implements IMenuRepository {
    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<MenuRoleMapping> jdbcQuery;
    private final JdbcQuery<ApiRoleMapping> apiRoleMappingJdbcQuery;

    public MenuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery =  new JdbcQuery<>(jdbcTemplate,  new MenuRoleMappingRowMapper());
        this.apiRoleMappingJdbcQuery =  new JdbcQuery<>(jdbcTemplate,  new ApiRoleMappingRowMapper());
    }

    @Override
    public List<MenuRoleMapping> 사용자별메뉴조회(int roleId) {
        String query = """
                SELECT m.name
                      ,m.path
                      ,rm.id
                      ,rm.role_id
                      ,rm.menu_id                      
                      ,rm.seq
                      ,rm.depth
                      ,rm.parent_menu_id                      
                  FROM role r
                      ,menu_role_mapping rm
                      ,menu  m
                where r.id = rm.role_id
                  and m.id = rm.menu_id
                  and r.id = ?
                order by depth, rm.seq                
                """;
        return jdbcQuery.목록조회(query, roleId);
    }

    @Override
    public List<ApiRoleMapping> 사용자별api리스트조회(int roleId) {
        String query = """
                SELECT b.id
                      ,a.name
                      ,a.path
                      ,a.method
                      ,b.api_id
                  FROM role r
                      ,api_list_role_mapping b
                      ,api_list a
                 where r.id = b.role_id
                   and b.api_id = a.id
                   and r.id = ?;    
                """;
        return apiRoleMappingJdbcQuery.목록조회(query, roleId);
    }
}
