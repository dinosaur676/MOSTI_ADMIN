export const logic = {
    init: function(){
        // ties between views
        $$("frmMaster").setValues({ id: ""});
        $$("frmMaster").bind($$("dtMaster"));
        this.selectUsers();
        //this.selectUsers();
    },

    makeNewForm: function() {
        $$("frmMaster").setValues({
            userId: "",
            loginId: "",
            userName: "",
            email: "",
            password: "",
            type: "",
            status: "Y"
        });
        $$("dtMaster").unselectAll()
        $$("frmMaster").getChildViews()[3].show();
    },

    selectUsers: function (params) {
        const promise = userService.search(params);
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

    deleteUser: function(model) {
        if (model) {
            const promise = userService.remove(model);
            promise.then(function (json) {
                //success
                //const json = resp.json();
                //console.log(data);
                if (json.status == "00") {
                    webix.message({ text: '삭제되었습니다.', expire: 1000 });

                    const params = { name: $$("txtName").getValue() };
                    logic.selectUsers(params);
                } else {
                    webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
                }

            });
//            .fail(function (err) {
//                webix.message({ type: 'error', text: '[ERROR]\n' + err });
//            });
        }
    },

    saveUser: function(model) {
        console.log(model);
        let promise;
        if (model.userId == "")
            promise = userService.add(model);
        else
            promise = userService.modify(model);

        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {
                webix.message({ text: '저장되었습니다.', expire: 1000 });

                const params = { name: $$("txtName").getValue() };
                logic.selectUsers(params);
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }
        });
    }
}
