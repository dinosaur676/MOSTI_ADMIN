package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.ApiRoleMapping;
import emblock.mosti.application.domain.builder.ApiFindBuilder;
import emblock.mosti.application.domain.builder.ApiRoleMappingFindBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiRoleMappingRowMapper implements RowMapper<ApiRoleMapping> {
    @Override
    public ApiRoleMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ApiRoleMappingFindBuilder.anApiRoleMapping(rs.getInt("id"))
                .withApi(
                    ApiFindBuilder.anApi(rs.getInt("api_id"))
                            .withMethod(rs.getString("method"))
                            .withPath(rs.getString("path"))
                            .withName(rs.getString("name"))
                            .build()
                ).build();
    }
}
