export const logic = {
    init: function(){
        this.selectStudents();
        this.selectTokens(userInfo.userId);
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
        const promise = tokenService.getTokenInfo(userId, true);
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
        const promise = tokenService.mintTokenInPublic(params);

        promise.then(function (json) {

            webix.message(json.message, "info", 1000);
        });

    }

}