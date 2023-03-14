package emblock.mosti.application.domain.builder;

import emblock.mosti.application.domain.Api;

public final class ApiFindBuilder {
    private int id;
    private String name;
    private String path;
    private String method;

    private ApiFindBuilder(int id) {
        this.id = id;
    }

    public static ApiFindBuilder anApi(int id) {
        return new ApiFindBuilder(id);
    }

    public ApiFindBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ApiFindBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public ApiFindBuilder withMethod(String method) {
        this.method = method;
        return this;
    }

    public Api build() {
        return new Api(id, name, path, method);
    }
}
