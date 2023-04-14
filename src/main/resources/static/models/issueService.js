const issueService = {
    endpoint: {
        tokenBase: "/api/gateway",
        studentBase: "/api/students",
    },

    callTokenAPIWithParam: function (url, param) {
        return apiWrapper.post(this.endpoint.tokenBase + url, param);
    },

    callStudentAPIWithParam: function (url, param) {
        return apiWrapper.post(this.endpoint.studentBase + url, param);
    },

    callStudentAPIWithParamGet: function (url, param) {
        return apiWrapper.get(this.endpoint.studentBase + url, param);
    },

}