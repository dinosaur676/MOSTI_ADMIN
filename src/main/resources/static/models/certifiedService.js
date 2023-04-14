const certifiedService = {
    endpoint: {
        tokenBase: "/api/third/certified",
    },

    certified: function (loginId) {
        return apiWrapper.get(this.endpoint.tokenBase + "/" + loginId);
    }

}