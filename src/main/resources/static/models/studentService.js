const studentService = {
    endpoint: {
        base: "/api/students",
    },

    search: function(params) {
        //return webix.ajax().get(this.apiUrl.base, params);
        return apiWrapper.get(this.endpoint.base, params);
    },
    find: function(userId) {
        //return webix.ajax().get(this.apiUrl.base + '/' + id);
        return apiWrapper.get(this.endpoint.base + '/' + userId);
    },
    add: function (model) {
        //return webix.ajax().headers(ajaxOption).post(this.apiUrl.base, JSON.stringify(model));
        return apiWrapper.post(this.endpoint.base, JSON.stringify(model));
    },
    modify: function (model) {
        //return webix.ajax().headers(ajaxOption).put(this.apiUrl.base, JSON.stringify(model));
        return apiWrapper.put(this.endpoint.base, JSON.stringify(model));
    },
    // remove: function (model) {
    //     //return webix.ajax().headers(ajaxOption).del(this.apiUrl.base + "/" + model.id);
    //     return apiWrapper.del(this.endpoint.base + "/" + model.id);
    // }

};
