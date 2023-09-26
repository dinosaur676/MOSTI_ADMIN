const tokenService = {
    endpoint: {
        base: "/api/token",
    },

    getTokenInfo: function () {
        let params = {
            "userName": userInfo.name
        };

        return apiWrapper.get(this.endpoint.base + "/info", params);
    },

    getUserToken: function (userId, contractType) {
        params = {
            "userId" : userId,
            "contractType" : contractType
        };
        return apiWrapper.get(this.endpoint.base + "/user-token", params);
    },

    getTokenType: function () {
        return apiWrapper.get(this.endpoint.base + "/token-type", null);
    },

    addTokenType: function (params) {
        return apiWrapper.post(this.endpoint.base + "/token-type", params);
    },

    createTokenInPublic: function(params) {
        return apiWrapper.post(this.endpoint.base + "/info", params);
    },
    mintTokenInPublic: function(params) {
        return apiWrapper.put(this.endpoint.base + "/info", params);
    },
    burnTokenInPublic: function(params) {
        return apiWrapper.delete(this.endpoint.base + "/admin/token", params);
    },

    createTokenInCommunity: function(params) {
        return apiWrapper.post(this.endpoint.base + "/admin/token", params);
    },
    mintTokenInCommunity: function(params) {
        return apiWrapper.put(this.endpoint.base + "/admin/token", params);
    },
    burnTokenInCommunity: function(params) {
        return apiWrapper.delete(this.endpoint.base + "/admin/token", params);
    },
};