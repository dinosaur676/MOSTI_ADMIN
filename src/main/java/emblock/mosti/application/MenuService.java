package emblock.mosti.application;

import emblock.mosti.application.domain.ApiRoleMapping;
import emblock.mosti.application.domain.MenuRoleMapping;
import emblock.mosti.application.port.in.IMenuService;
import emblock.mosti.application.port.out.IMenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService implements IMenuService {
    private final IMenuRepository menuRepository;

    public MenuService(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<MenuRoleMapping> 사용자별메뉴조회(int roleId) {
        List<MenuRoleMapping> menuList = menuRepository.사용자별메뉴조회(roleId);
        menuList.forEach(item -> {
            if (item.getParentMenuId() > 0) {
                menuList.stream()
                        .filter(menu -> item.getParentMenuId() == menu.getMenuId())
                        .findFirst().ifPresent(menu->menu.자식메뉴리스트생성(item));
            }
        });
        menuList.removeIf(item->item.getParentMenuId()>0);
        return menuList;
    }

    @Override
    public List<ApiRoleMapping> 사용자별api리스트조회(int roleId) {
        return this.menuRepository.사용자별api리스트조회(roleId);
    }
}
