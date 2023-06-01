export const logic = {
    init: function(){
        $$("frmMaster").bind($$("dtMaster"));
        this.selectAll();
    },

    selectAll: function() {
        const params = {
            "userName" : "",
            "label" : "",
            "startDate": "2000-01-01",
            "endDate": "9999-12-30"
        }
        logic.select(params);
    },

    select: function (params) {
        const promise = logService.select(params);
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

}
