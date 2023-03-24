package emblock.mosti.adapter.rdb.mapper;

import emblock.mosti.application.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.Builder.builder찾기(rs.getLong("user_id"),rs.getString("user_name"))
                .password(rs.getString("password"))
                .loginId(rs.getString("login_id"))
                .email(rs.getString("email"))
                .address(rs.getString("address"))
                .phone(rs.getString("phone"))
                .cellPhone(rs.getString("cell_phone"))
                .status(User.UserStatus.valueOf(rs.getString("status")))
                .type(User.UserType.valueOf(rs.getString("type")))
                .walletId(rs.getString("wallet_id"))
                .publicAddress(rs.getString("public_address"))
                .createOn(rs.getTimestamp("created_on"))
                .updatedOn(rs.getTimestamp("updated_on"))
                .roleId(rs.getInt("role_id")).build()
                ;
    }
    
}
