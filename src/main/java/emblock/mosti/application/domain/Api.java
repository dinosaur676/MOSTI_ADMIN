package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class Api {
    private int id;
    private String name;
    private String path;
    private String method;
    private LocalDateTime cratedOn;

    public Api(int id, String name, String path, String method) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.method = method;
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

    public String getMethod() {
        return method;
    }

    public LocalDateTime getCratedOn() {
        return cratedOn;
    }

    @Override
    public String toString() {
        return "Api{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", cratedOn=" + cratedOn +
                '}';
    }
}
