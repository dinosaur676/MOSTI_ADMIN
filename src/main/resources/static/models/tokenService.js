const tokenService = {
    endpoint: {
        base: "/api/gateway",
    },

    callAPIWithParam: function (url, param) {
        return apiWrapper.post(this.endpoint.base + url, param);
    },

    callAPI: function (url) {
        return apiWrapper.post(this.endpoint.base + url);
    }
    // remove: function (model) {
    //     //return webix.ajax().headers(ajaxOption).del(this.apiUrl.base + "/" + model.id);
    //     return apiWrapper.del(this.endpoint.base + "/" + model.id);
    // }

};