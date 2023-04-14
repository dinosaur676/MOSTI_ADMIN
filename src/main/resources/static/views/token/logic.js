export const logic = {

    init: function(){
        // ties between views
        $$("frmMaster").setValues({ id: ""});
        $$("frmMaster").bind($$("dtMaster"));
        this.selectTokens();
        //this.selectUsers();
    },


    makeNewForm: function() {
        $$("frmMaster").setValues({
            id: "",
            name: "",
            studentId: "",
            school: "",
            major: "",
            status: "N"
        });
        $$("dtMaster").unselectAll()
        $$("frmMaster").getChildViews()[3].show();
    },

    selectTokens: function (params) {
        if(params == null)
        {
            params = {
                "userId" : "",
                "contractType" : "P"
            }
        }
        const promise = tokenService.callAPIWithParam("/token-info", params);
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
        const promise = tokenService.callAPIWithParam("/admin-create-token", params)
        promise.then(function (json) {

            if(json.status == "00") {
                logic.selectTokens();
            }

            webix.message(json.message, "info", 1000);
        });

    },

    getTokenTypes: function () {
        const promise = tokenService.callAPI("/token-type");
        
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

    deleteStudent: function(model) {
        if (model) {
            const promise = studentService.remove(model);
            promise.then(function (json) {
                //success
                //const json = resp.json();
                //console.log(data);
                if (json.status == "00") {
                    webix.message({ text: '삭제되었습니다.', expire: 1000 });

                    const params = { name: $$("txtName").getValue() };
                    logic.selectStudents(params);
                } else {
                    webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
                }

            });
//            .fail(function (err) {
//                webix.message({ type: 'error', text: '[ERROR]\n' + err });
//            });
        }
    },

    saveStudents: function(model) {
        console.log(model);
        const promise = studentService.add(model);

        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {
                webix.message({ text: '저장되었습니다.', expire: 1000 });

                const params = { name: $$("txtName").getValue() };
                logic.selectStudents(params);
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }
        });
    },

}
