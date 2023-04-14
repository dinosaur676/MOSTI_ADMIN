export const logic = {
    init: function(){
        this.selectStudents();
        this.selectTokens();
    },

    selectStudents: function (params) {
        const promise = issueService.callStudentAPIWithParamGet("", params);
        //console.log(promise);
        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {
                if (json.data != null) {
                    $$("studentTable").clearAll();
                    $$("studentTable").parse(json.data);

                   // $$("frmMaster").getChildViews()[3].hide();
                }
            }
            webix.message(json.message, "info", 1000);
        });
    },

    selectTokens: function (params) {
        if(params == null)
        {
            params = {
                "userId" : "",
                "contractType": "P"
            }
        }
        const promise = issueService.callTokenAPIWithParam("/token-info", params);
        //console.log(promise);
        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {
                if (json.data != null) {
                    $$("tokenTable").clearAll();
                    $$("tokenTable").parse(json.data);

                    // $$("frmMaster").getChildViews()[3].hide();
                }
            }
            webix.message(json.message, "info", 1000);
        });
    },
    
    mintToken: function (params) {
        const promise = issueService.callTokenAPIWithParam("/admin-mint-token", params);

        promise.then(function (json) {

            webix.message(json.message, "info", 1000);
        });

    }

}