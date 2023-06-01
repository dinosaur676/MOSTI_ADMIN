import {logic} from "./logic.js"

const getParams = function () {
    const params = {
        "schoolName": $$("schoolName").getValue(),
        "tokenId":  parseInt($$("tokenId").getValue()),
    }

    return params;
}

const gridMaster = {
    view: "datatable",
    id: "dtMaster",
    select: true,
    headerRowHeight: 35,
    //autowidth: true,
    navigation:false,       //keyboard protect
    columns:  [
        { id: "schoolName", header: "학교 이름", width: 100, sort:"string"},
        { id: "tokenId", header: "학교 토큰 ID", width: 120, sort:"string"}
    ],
    on: {
        onBeforeLoad: function () {
            this.showOverlay("Loading...");
        },
        onAfterLoad: function () {
            if (!this.count())
                this.showOverlay("Sorry, there is no data");
            else
                this.hideOverlay();
        },
        onAfterSelect: function (cell) {
            $$("frmMaster").clearValidation();
        },
        onItemClick: function (id, e, trg) {

        },
        onSelectChange: function() {

        }
    }
};

const formMaster = {
    view: "form", id: "frmMaster", scroll: true,
    elementsConfig: { labelWidth: 100, labelAlign: "right" },
    elements: [
        { view: "text", id: "schoolName", name: "schoolName", label: "학교 이름"},
        { view: "text", id: "tokenId", name: "tokenId", label: "학교 토큰 ID"},
        {
            view: "toolbar", borderless: true, margin: 20,
            cols:[
                { gravity: 3 },
                { view: "button", label: "삭제", type: "danger", width: 80,
                    css:"webix_danger",
                    click: function() {
                        var params = getParams();
                        //console.log(model);
                        webix.confirm({
                            title: "사용자 삭제",
                            ok: "Yes", cancel: "No",
                            text: "선택된 사용자를 삭제하시겠습니까?"
                        }).then(function () {
                            logic.remove(params);
                        })
                    }
                },
                { view: "button", label: "수정", type: "form", width: 80, align: "right",
                    css:"webix_primary",
                    click: function() {
                        var params = getParams();
                        logic.update(params);
                        //console.log(model);

                    }
                },
                { view: "button", label: "저장", type: "form", width: 80, align: "right",
                    css:"webix_primary",
                    click: function() {
                        var params = getParams();
                        logic.add(params);
                        //console.log(model);

                    }
                }
            ]
        },
    ],
};

const ctrlView = {
    view: "toolbar",
    cols: [
        {
            view: "text", id: "txtName", label: "이름", labelAlign:"right",
            options: {
                body: {
                    data: [],
                    on: {
                        'onItemClick': function(id) {
                            //console.log(this.getItem(id));
                        }
                    }
                }
            }
        },
        {
            view: "button", id: "btnSearch", value: "검색", width: 100, height: 40,
            click: function () {
                logic.select();
           }
        },

    ]
};

export const mainView = {
    id: "mainView",
    type: "space",
    rows: [
        ctrlView,
        {
            cols: [
                {
                    rows:[
                        { template: "학교 목록", height: 32, css:"ctrlTitle"},
                        gridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "학교 정보", height: 32, css:"ctrlTitle" },
                        formMaster
                    ]
                }
            ]
        }
    ]
};
