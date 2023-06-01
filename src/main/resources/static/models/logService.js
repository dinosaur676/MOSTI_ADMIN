const logService = {
    endpoint: {
        base: "/api/log",
    },

    select: function (params) {
        return apiWrapper.post(this.endpoint.base, params);
    }

}