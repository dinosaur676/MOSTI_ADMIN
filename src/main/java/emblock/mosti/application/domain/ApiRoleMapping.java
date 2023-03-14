package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class ApiRoleMapping {
    private int id;
    private int roleId;
    private int apiId;
    private LocalDateTime createdOn;
    private Api api;

    public ApiRoleMapping(int id, int roleId, int apiId, Api api) {
        this.id = id;
        this.roleId = roleId;
        this.apiId = apiId;
        this.api = api;
    }

    public int getId() {
        return id;
    }

    public int getRoleId() {
        return roleId;
    }

    public int getApiId() {
        return apiId;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Api getApi() {
        return api;
    }

    @Override
    public String toString() {
        return "ApiRoleMapping{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", apiId=" + apiId +
                ", createdOn=" + createdOn +
                ", api=" + api +
                '}';
    }
}
