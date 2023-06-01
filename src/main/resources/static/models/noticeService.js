const noticeService = {
    endpoint: {
        base: "/api/notice",
    },

    selectAll: function () {
        return apiWrapper.get(this.endpoint.base);
    },

    select: function (writer) {
        return apiWrapper.get(this.endpoint.base + "/" + writer);
    },

    update: function(params) {
        return apiWrapper.put(this.endpoint.base, params);
    },

    add: function (params) {
        return apiWrapper.post(this.endpoint.base, params);
    },

    remove: function (params) {
        return apiWrapper.del(this.endpoint.base, params);
    }
}