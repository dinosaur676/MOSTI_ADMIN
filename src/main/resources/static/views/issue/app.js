import {logic} from "./logic.js"

const datas = {
    student: {
        userId: "",
        userName: "",
        studentId: "",
        school: "",
        major: "",
    },
    token: {
        tokenId: "",
        tokenOwnerName: "",
        type: "",
        metaData: "",
    }
}

let clickedToken = null;

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
            $$("frmMaster").clearValidation();
            //console.log(cell);
        },
        onItemClick: function (id, e, trg) {
            var record = $$("studentTable").getItem(id.row);
            datas.student.userId = record.userId;
            datas.student.studentId = record.studentId;
            datas.student.major = record.major;
            datas.student.userName = record.userName;
            datas.student.school = record.school;
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
            datas.token.tokenId = record.tokenId;
            datas.token.tokenOwnerName = record.tokenOwnerName;
            datas.token.type = record.type;
            datas.token.metaData = record.metaData;
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
const popup_form = {
    view: "form",
    id: "frmMaster",
    scroll: true,
    elementsConfig: { labelWidth: 100, labelAlign: "right" },
    elements:
        [
            {template: "학생 정보", height: 32, css:"ctrlTitle" },
            {view: "text", id:"userId", name: "userId", label: "Id"},
            {view: "text", id:"userName", name: "userName", label: "이름", disabled: true},
            {view: "text", id:"studentId", name: "studentId", label: "학번", disabled: true},
            {view: "text", id:"school", name: "school", label: "학교", disabled: true},
            {view: "text", id:"major", name: "major", label: "학과", disabled: true},

            { template: "토큰 정보", height: 32, css:"ctrlTitle" },
            {view: "text", id:"tokenId", name: "tokenId", label: "토큰 ID"},
            {view: "text", id:"tokenOwnerName", name: "tokenOwnerName", label: "토큰 소유자", disabled: true},
            {view: "text", id:"type", name: "type", label: "토큰 타입", disabled: true},
            {view: "text", id:"metaData", name: "metaData", label: "토큰 정보", height: 400, disabled: true},
            {view: "datepicker", id:"deletedOn", label: "만료 기간", value: new Date(9999,1,1 )},
        ]
}
const popup = {
    view : "window",
    id: "mint_token_popup",
    height: 600,
    width: 600,
    close: true,
    position: "center",
    head: {
        cols: [
            {template: "토큰 생성", type: "header", borderless:true},
            {
                view: "icon", icon: "wxi_close", tooltip: "닫기", click: function () {
                    $$("mint_token_popup").close();
                }
            },
        ]
    },
    body:
        {
           rows: [
               popup_form,
               {view:"button", id: "createTokenButton", height: 50, value: "토큰 생성",
                   click: function () {
                       const param = {
                           "userId": $$("userId").getValue(),
                           "tokenId": $$("tokenId").getValue(),
                           "deletedOn": $$("deletedOn").getValue()
                       }
                       logic.mintToken(param);
                   }
               }
           ],
        }

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
                        { template: "사용자 정보", height: 32, css:"ctrlTitle" },
                        tokenGridMaster,
                        { view: "button", label: "토큰 지급", type: "form", align: "right",
                            css:"webix_primary",
                            click: function() {
                                webix.ui(popup).show();
                            }
                        },
                    ]
                },
            ]
        },
    ]
};
