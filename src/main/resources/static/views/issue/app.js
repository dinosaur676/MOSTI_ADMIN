import {logic} from "./logic.js"


const studentGridMaster = {
    view: "datatable",
    id: "studentTable",
    select: true,
    headerRowHeight: 35,
    //autowidth: true,
    navigation:false,       //keyboard protect
    columns: [
        { id: "userId", header: "ID", width: 80, hidden:true  },
        { id: "userName", header: "이름", width: 100, sort:"string"},
        { id: "studentId", header: "학번", width: 120, sort:"string"},
        { id: "school", header: "학교", width: 160, sort:"string", fillspace:true},
        { id: "major", header: "전공", width: 100, sort:"string", hidden:true },
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
            //console.log(cell);
        },
        onItemClick: function (id, e, trg) {
            var record = $$("studentTable").getItem(id.row);
            $$("mintForm").elements["userId"].setValue(record.userId);
        },
        onSelectChange: function() {

        }
    }
};

const tokenGridMaster = {
    view: "datatable",
    id: "tokenTable",
    select: true,
    headerRowHeight: 35,
    //autowidth: true,
    navigation:false,       //keyboard protect
    columns: [
        { id: "tokenId", header: "id", width: 80, hidden:true  },
        { id: "tokenOwnerName", header: "토큰 주인", width: 120, sort:"string"},
        { id: "type", header: "토큰 타입", width: 120, sort:"string"},
        { id: "metaData", header: "토큰 정보", width: 100, sort: "string", fillspace: true},
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
            //console.log(cell);
        },
        onItemClick: function (id, e, trg) {
            var record = $$("tokenTable").getItem(id.row);
            $$("mintForm").elements["tokenId"].setValue(record.tokenId);

        },
        onSelectChange: function() {

        }
    }
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
                var params = { name: $$("txtName").getValue() };
                logic.selectStudents(params);
           }
        },
    ]
};

const mintForm = {
    view: "form",
    id: "mintForm",
    scroll: true,
    elementsConfig: { labelWidth: 100, labelAlign: "right" },
    elements:
        [
            {view: "text", id:"userId", name: "userId", label: "Id", disabled: true},
            {view: "text", id:"tokenId", name: "tokenId", label: "토큰 ID", disabled: true},
            {view: "datepicker", id:"deletedOn", label: "만료 기간", value: new Date(9999,1,1 )},
            { view: "button", label: "토큰 지급", type: "form", align: "right",
                css:"webix_primary",
                click: function() {
                    const param = {
                        "userId": $$("userId").getValue(),
                        "tokenId": $$("tokenId").getValue(),
                        "deletedOn": $$("deletedOn").getValue()
                    }
                    logic.mintToken(param);
                }
            },
        ]

}
export const mainView = {
    id: "mainView",
    type: "space",
    rows: [
        ctrlView,
        {
            cols: [
                {
                    rows:[
                        { template: "학생 목록", height: 32, css:"ctrlTitle"},
                        studentGridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "토큰 정보", height: 32, css:"ctrlTitle" },
                        tokenGridMaster,
                        mintForm,
                    ]
                },
            ]
        },
    ]
};
