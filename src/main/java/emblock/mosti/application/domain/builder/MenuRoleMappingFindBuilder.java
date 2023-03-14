package emblock.mosti.application.domain.builder;

import emblock.mosti.application.domain.Menu;
import emblock.mosti.application.domain.MenuRoleMapping;

import java.time.LocalDateTime;

public final class MenuRoleMappingFindBuilder {
    private int id;
    private int roleId;
    private int menuId;
    private int depth;
    private int parentMenuId;
    private int seq;
    private LocalDateTime createdOn;
    private Menu menu;

    private MenuRoleMappingFindBuilder(int id) {
        this.id = id;
    }

    public static MenuRoleMappingFindBuilder aMenuRoleMapping(int id) {
        return new MenuRoleMappingFindBuilder(id);
    }


    public MenuRoleMappingFindBuilder withRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public MenuRoleMappingFindBuilder withMenuId(int menuId) {
        this.menuId = menuId;
        return this;
    }

    public MenuRoleMappingFindBuilder withDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public MenuRoleMappingFindBuilder withParentMenuId(int parentMenuId) {
        this.parentMenuId = parentMenuId;
        return this;
    }

    public MenuRoleMappingFindBuilder withSeq(int seq) {
        this.seq = seq;
        return this;
    }

    public MenuRoleMappingFindBuilder withCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public MenuRoleMappingFindBuilder withMenu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public MenuRoleMapping build() {
        return new MenuRoleMapping(id, roleId, menuId, depth, parentMenuId, seq, createdOn, menu);
    }
}
