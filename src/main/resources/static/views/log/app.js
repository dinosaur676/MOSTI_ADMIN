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
        { id: "userId", header: "유저ID", hidden: true},
        { id: "userName", header: "사용자 이름", width: 120, sort:"string"},
        { id: "loginId", header: "로그인 아이디", width: 120, sort:"string"},
        { id: "label", header: "로그 이름", width: 120, sort:"string"},
        { id: "createdOn", header: "날짜", width: 120, sort:"string"},
        { id: "status", header: "상태", width: 80, hidden:true, css:{'text-align': 'center'} }
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
        { view: "text", id: "userId", name: "userId", label: "유저ID", disabled: true},
        { view: "text", id: "userName", name: "userName", label: "사용자 이름", readonly: true},
        { view: "text", id: "loginId", name: "loginId", label: "로그인 아이디", readonly: true},
        { view: "text", id: "label", name: "label", label: "로그 이름", readonly: true},
        { view: "text", id: "createdOn", name: "createdOn", label: "날짜", readonly: true},
        { view: "text", id: "type", name: "type", label: "유형", readonly: true},
        { view: "text", id: "status", name: "status", label: "상태", readonly: true},
    ],
};

const ctrlView = {
    view: "toolbar",
    cols: [
        {view: "text", id: "searchUserName", label: "이름", labelAlign:"right"},
        {view: "text", id: "searchLabel", label: "로그 명", labelAlign:"right"},
        {view: "datepicker", id: "startDate", label: "", timepicker: false, labelAlign:"right"},
        {view: "datepicker", id: "endDate", label: "~ ", timepicker: false, labelAlign:"right"},
        {
            view: "button", id: "btnSearch", value: "검색", width: 100, height: 40,
            click: function () {
                var params = {
                    "userName" : $$("searchUserName").getValue(),
                    "label" : $$("searchLabel").getValue(),
                    "startDate" : $$("startDate").getValue(),
                    "endDate" : $$("endDate").getValue(),
                }
                logic.select(params);
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
