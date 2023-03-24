export const logic = {
    init: function(){
        // ties between views
        $$("frmMaster").setValues({ id: ""});
        $$("frmMaster").bind($$("dtMaster"));
        this.selectStudents();
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
    selectStudents: function (params) {
        const promise = studentService.search(params);
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

    //학생증발급이력확인
    // getIssueHistory: function(model){
    //     if (modle) {
    //         const promise = issueService.getIssueHistory(model);
    //         promise.then(function(json){
    //             if (json.status == "00") {
    //                 webix.message({ text: "", expire: 1000 });

    //                 const params = { name: $$("txtName").getValue() };
    //                 logic.selectStudents(params);
    //             } else {
    //                 webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
    //             }
    //         })
    //     }
    // },

    issueStudentId: function(id, model){
        if (model) {
            const promise = issueService.issueStudentId(id, model);
            promise.then(function (json) {
                //success
                //const json = resp.json();
                console.log(json);
                if (json.status == "00") {
                    console.log(json.data);
                    const qrImg = document.createElement("img");
                    qrImg.src = json.data.qrcode

                    document.getElementById("tmp").appendChild(qrImg);
                    //$$("qrimage").getBody().setValues({src: json.data.qrcode});
                    // document.body.appendChild(qrImg)
                    //$$("tmp").appendChild(qrImg)
                    webix.message({ text: '해당 링크로 접속해 발급을 확인하세요.', expire: 1000 });
                    // const params = { name: $$("txtName").getValue() };
                    // logic.selectStudents(params);
                } else {
                    webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
                }

            });
//            .fail(function (err) {
//                webix.message({ type: 'error', text: '[ERROR]\n' + err });
//            });
        }
    }
}