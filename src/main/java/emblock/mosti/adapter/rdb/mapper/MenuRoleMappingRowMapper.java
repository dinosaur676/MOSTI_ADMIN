package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.builder.MenuFindBuilder;
import emblock.mosti.application.domain.MenuRoleMapping;
import emblock.mosti.application.domain.builder.MenuRoleMappingFindBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuRoleMappingRowMapper implements RowMapper<MenuRoleMapping> {
    @Override
    public MenuRoleMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MenuRoleMappingFindBuilder.aMenuRoleMapping(rs.getInt("id"))
                .withMenu(
                        MenuFindBuilder.aMenu(rs.getInt("menu_id")
                                ,rs.getString("path")
                                ,rs.getString("name")
                        ).build())
                .withRoleId(rs.getInt("role_id"))
                .withMenuId(rs.getInt("menu_id"))
                .withSeq(rs.getInt("seq"))
                .withDepth(rs.getInt("depth"))
                .withParentMenuId(rs.getInt("parent_menu_id"))
                .build();
    }
}
