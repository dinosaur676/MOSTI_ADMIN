package emblock.mosti.application.port.in;

import emblock.mosti.application.domain.ApiRoleMapping;
import emblock.mosti.application.domain.MenuRoleMapping;

import java.util.List;

public interface IMenuService {
    public List<MenuRoleMapping> 사용자별메뉴조회(int RoleId);

    List<ApiRoleMapping> 사용자별api리스트조회(int roleId);
}
