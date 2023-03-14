package emblock.mosti.application.domain.builder;

import emblock.mosti.application.domain.Menu;

import java.time.LocalDateTime;

public final class MenuFindBuilder {
    private int id;
    private String name;
    private String path;
    private LocalDateTime createdOn;

    private MenuFindBuilder(int id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
    }

    public static MenuFindBuilder aMenu(int id, String path, String name) {
        return new MenuFindBuilder(id, path, name);
    }


    public MenuFindBuilder withCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Menu build() {
        return new Menu(id, name, path, createdOn);
    }
}
