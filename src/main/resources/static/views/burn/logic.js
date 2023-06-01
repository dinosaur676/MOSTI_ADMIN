export const logic = {
    init: function(){
        this.selectStudents();
    },

    selectStudents: function (params) {
        const promise = studentService.search(params);
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

    selectTokens: function (userId) {
        const promise = tokenService.getUserToken(userId, true);
        //console.log(promise);
        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {

                $$("tokenTable").clearAll();
                if (json.data != null) {
                    $$("tokenTable").parse(json.data);

                    // $$("frmMaster").getChildViews()[3].hide();
                }
            }
            webix.message(json.message, "info", 1000);
        });
    },
    
    burnToken: function (params) {
        const promise = tokenService.burnTokenInPublic(params);

        promise.then(function (json) {

            if (json.status == "00") {
                params = {
                    "userId": params.userId,
                    "contractType": "P"
                }
                logic.selectTokens(params);
            }
            webix.message(json.message, "info", 1000);
        });

    }

}