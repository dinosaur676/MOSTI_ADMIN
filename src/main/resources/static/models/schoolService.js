const schoolService = {
    endpoint: {
        base: "/api/school",
    },

    add: function (params) {
        return apiWrapper.post(this.endpoint.base, params);
    },

    update: function (params) {
        return apiWrapper.put(this.endpoint.base, params);
    },

    remove: function (params) {
        return apiWrapper.del(this.endpoint.base, params);
    },

    select: function () {
        return apiWrapper.get(this.endpoint.base);
    }

}