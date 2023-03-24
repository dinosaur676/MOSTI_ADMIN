const issueService = {
    endpoint: {
        base: "/api/issue",
    },


    issueStudentId: function(id, model){
        return apiWrapper.post(this.endpoint.base+'/'+id, JSON.stringify(model));
    },

    valid: function(id, model){
        return apiWrapper.post(this.endpoint.base+'/valid/'+id, JSON.stringify(model));
    }
}