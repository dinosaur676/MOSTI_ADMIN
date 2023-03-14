package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class Menu{
    private int id;
    private String name;
    private String path;
    private LocalDateTime createdOn;

    public Menu(int id, String name, String path, LocalDateTime createdOn) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.createdOn = createdOn;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
