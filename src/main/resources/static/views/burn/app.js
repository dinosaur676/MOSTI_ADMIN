import {logic} from "./logic.js"

let userId = null;
let tokenId = null;

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
        },
        onItemClick: function (id, e, trg) {
            var record = $$("studentTable").getItem(id.row);
            const param = {

                "userId": record.userId,
                "contractType": "P"
            }

            userId = param.userId;

            logic.selectTokens(param);
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
            tokenId = record.tokenId;
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
                        { template: "사용자 정보", height: 32, css:"ctrlTitle" },
                        tokenGridMaster,
                        { view: "button", label: "토큰 철회", type: "form", align: "right",
                            css:"webix_primary",
                            click: function() {

                                if(tokenId == null || userId == null)
                                    return;

                                const param = {
                                    "userId": userId,
                                    "tokenId": tokenId,
                                }

                                logic.burnToken(param);
                            }
                        },
                    ]
                },
            ]
        },
    ]
};
