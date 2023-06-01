export const logic = {
    init: function(){
        $$("frmMaster").bind($$("dtMaster"));
        this.select();
    },

    select: function () {
        const promise = schoolService.select();
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

    update: function(params) {
        const promise = schoolService.update(params);
        promise.then(function (json) {
            if (json.status == "00") {
                webix.message({ text: '수정되었습니다..', expire: 1000 });

                logic.select();
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }

        });
    },
    remove: function(params) {
        const promise = schoolService.remove(params);
        promise.then(function (json) {
            if (json.status == "00") {
                webix.message({ text: '삭제되었습니다.', expire: 1000 });
                logic.select();
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }

        });
    },

    add: function(params) {
        const promise = schoolService.add(params);
        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {
                webix.message({ text: '저장되었습니다.', expire: 1000 });
                logic.select();
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }
        });
    },

}
