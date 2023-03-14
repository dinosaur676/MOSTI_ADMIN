package emblock.mosti.application.domain.builder;

import emblock.mosti.application.domain.Api;
import emblock.mosti.application.domain.ApiRoleMapping;

public final class ApiRoleMappingFindBuilder {
    private int id;
    private int roleId;
    private int apiId;
    private Api api;

    private ApiRoleMappingFindBuilder(int id) {
        this.id = id;
    }

    public static ApiRoleMappingFindBuilder anApiRoleMapping(int id) {
        return new ApiRoleMappingFindBuilder(id);
    }

    public ApiRoleMappingFindBuilder withRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public ApiRoleMappingFindBuilder withApiId(int apiId) {
        this.apiId = apiId;
        return this;
    }

    public ApiRoleMappingFindBuilder withApi(Api api) {
        this.api = api;
        return this;
    }

    public ApiRoleMapping build() {
        return new ApiRoleMapping(id, roleId, apiId, api);
    }
}
