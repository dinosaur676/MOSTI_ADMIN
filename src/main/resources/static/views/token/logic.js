export const logic = {

    init: function(){
        // ties between views
        $$("frmMaster").setValues({ id: ""});
        $$("frmMaster").bind($$("dtMaster"));
        this.selectTokens(userInfo.userId);
    },

    selectTokens: function (userId) {
        const promise = tokenService.getTokenInfo(userId, true);
        //console.log(promise);
        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {
                if (json.data != null) {
                    $$("dtMaster").clearAll();
                    $$("dtMaster").parse(json.data);

                   // $$("frmMaster").getChildViews()[3].hide();
                }
            }
            webix.message(json.message, "info", 1000);
        });
    },

    createToken: function (params) {
        const promise = tokenService.createTokenInPublic(params)
        promise.then(function (json) {

            if(json.status == "00") {
                logic.selectTokens();
            }

            webix.message(json.message, "info", 1000);
        });

    },

    getTokenTypes: function () {
        const promise = tokenService.getTokenType();
        
        promise.then(function (json) {

            if(json.status == "00")
            {
                let outputMap = new Array();

                json.data.forEach((item) => {
                    var data = new Object();

                    data.id = item.tokenType;
                    data.value = item.description;

                    outputMap.push(data);
                });

                return outputMap;
            }
        })

    },
}
