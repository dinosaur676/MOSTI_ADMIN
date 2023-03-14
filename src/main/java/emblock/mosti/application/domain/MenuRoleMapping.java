package emblock.mosti.application.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuRoleMapping {
    private int id;
    private int roleId;
    private int menuId;
    private int depth;
    private int parentMenuId;
    private int seq;
    private LocalDateTime createdOn;
    private Menu menu;

    private List<MenuRoleMapping> childrenMenuList;

    public MenuRoleMapping(int id, int roleId, int menuId, int depth, int parentMenuId, int seq, LocalDateTime createdOn, Menu menu) {
        this.id = id;
        this.roleId = roleId;
        this.menuId = menuId;
        this.depth = depth;
        this.parentMenuId = parentMenuId;
        this.seq = seq;
        this.menu = menu;
        this.createdOn = createdOn;
    }

    public void 자식메뉴리스트생성(MenuRoleMapping childMenuList){
        if(this.childrenMenuList == null) this.childrenMenuList = new ArrayList<>();
        this.childrenMenuList.add(childMenuList);
    }

    public int getId() {
        return id;
    }

    public int getRoleId() {
        return roleId;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getDepth() {
        return depth;
    }

    public int getParentMenuId() {
        return parentMenuId;
    }

    public int getSeq() {
        return seq;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Menu getMenu() {
        return menu;
    }

    public List<MenuRoleMapping> getChildrenMenuList() {
        return childrenMenuList;
    }

    @Override
    public String toString() {
        return "MenuRoleMapping{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", menuId=" + menuId +
                ", depth=" + depth +
                ", parentMenuId=" + parentMenuId +
                ", seq=" + seq +
                ", createdOn=" + createdOn +
                ", menu=" + menu +
                ", childrenMenuList=" + childrenMenuList +
                '}';
    }
}
