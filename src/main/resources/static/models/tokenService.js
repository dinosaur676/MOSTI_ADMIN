const studentService = {
    endpoint: {
        base: "/api/gateway",
    },

    callAPI: function (url, param) {
        return apiWrapper.post(this.endpoint.base + url, param);
    },
    // remove: function (model) {
    //     //return webix.ajax().headers(ajaxOption).del(this.apiUrl.base + "/" + model.id);
    //     return apiWrapper.del(this.endpoint.base + "/" + model.id);
    // }

};
