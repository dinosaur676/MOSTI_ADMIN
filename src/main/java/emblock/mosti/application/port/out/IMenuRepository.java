package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.ApiRoleMapping;
import emblock.mosti.application.domain.MenuRoleMapping;

import java.util.List;

public interface IMenuRepository {
    public List<MenuRoleMapping> 사용자별메뉴조회(int roleId);

    List<ApiRoleMapping> 사용자별api리스트조회(int roleId);
}
