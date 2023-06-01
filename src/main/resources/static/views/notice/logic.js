export const logic = {
    init: function(){
        $$("frmMaster").bind($$("dtMaster"));
        this.selectAll();
    },

    selectAll: function() {
        const promise = noticeService.selectAll();
        promise.then(function (json) {
            if (json.status == "00") {
                if (json.data != null) {
                    $$("dtMaster").clearAll();
                    $$("dtMaster").parse(json.data);
                }
            }
            webix.message(json.message, "info", 1000);
        });
    },

    select: function (writer) {
        const promise = noticeService.select(writer);
        promise.then(function (json) {
            if (json.status == "00") {
                if (json.data != null) {
                    $$("dtMaster").clearAll();
                    $$("dtMaster").parse(json.data);
                }
            }
            webix.message(json.message, "info", 1000);
        });
    },

    update: function(params) {
        const promise = noticeService.update(params);
        promise.then(function (json) {
            if (json.status == "00") {
                webix.message({ text: '수정되었습니다..', expire: 1000 });
                logic.selectAll();
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }

        });
    },
    remove: function(params) {
        const promise = noticeService.remove(params);
        promise.then(function (json) {
            if (json.status == "00") {
                webix.message({ text: '삭제되었습니다.', expire: 1000 });
                logic.selectAll();
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }

        });
    },

    add: function(params) {
        const promise = noticeService.add(params);
        promise.then(function (json) {
            //success
            //console.log(json);
            if (json.status == "00") {
                webix.message({ text: '저장되었습니다.', expire: 1000 });
                logic.selectAll();
            } else {
                webix.message({ type: 'error', text: '[ERROR]\n' + json.info });
            }
        });
    },

}
