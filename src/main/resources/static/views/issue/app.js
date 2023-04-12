import {logic} from "./logic.js"
const gridMaster = {
    view: "datatable",
    id: "dtMaster",
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
            //console.log(id);
            $$("status").refresh();
        },
        onSelectChange: function() {

        }
    }
};

var qrImg = {};
const popup_template = {
    view: "window",
    id:"issue_popup",
    //autowidth:true,
    height: 350,
    close:true,
    position:"center",
    head:{
        cols:[
        {template:"학생증 발급", type:"header", borderless:true},
        {view:"icon", icon:"wxi-close", tooltip:"Close window", click: function(){
            $$('issue_popup').close();
        }},
        ]
    },
    body: {
        id:'body',
        rows: [
            { view:"text", id:"authKey", type:"password", label:"", name:"password", height: 50, placeholder: "인증번호" ,required:true,invalidMessage:"비밀번호를 입력해주세요." },        
            {
                view: "button", id: "btnIssue", value: "발급", height: 40, width: 300,
                click: function () {
                    var model = $$("frmMaster").getValues();
                    var authKey = $$("authKey").getValue()                
                    logic.issueStudentId(model.id, {"authKey":authKey});
                }
            },
            /*{
                id:"tmp", 
                view:"template",
                template:"<img src='#src#' class='fit_parent'></img>",
                width:500,
                autoheight:true
              }*/
            // {
            //     view: "space", id: "qrImg"
            //         data: {title: "Image One", src: "" , id: "qrImg"},
            //         template: function (obj) {
            //             // obj is a data record object
            //             return '<img src="'+obj.src+'"/>'
            //         }
            // }
            ] 
    }
}

const formMaster = {
    view: "form", id: "frmMaster", scroll: true,
    elementsConfig: { labelWidth: 100, labelAlign: "right" },
    elements: [
    	{ view: "text", name:"userId", label: "id", disabled:true },
    	{ view: "text", name:"userName", label: "이름",  readonly:true},
        { view: "text", name: "studentId",  label: "학번", readonly:true },
        { view: "text", name: "school", label: "학교", readonly:true }, 
        { view: "text", name: "major", label: "전공", readonly:true },
        {
            view: "select", id: "status", name: "status", //required: true,
            label: "사용여부", value: 1,
            options: [
                { id: "Y", value: "Y" },
                { id: "N", value: "N" }
            ]
        },

        { 
            view: "button", value: "학생증 발급", align: "right", css:"webix_secondary",
            click: function() {
                webix.ui(popup_template).show()
                //$$('issue_popup').show();
                // webix.ui({
                //     view:"window",
                //     id:"issue_popup",
                //     autowidth:true,
                //     height: 350,
                //     close:true,
                //     position:"center",
                //     head:{
                //         cols:[
                //         {template:"학생증 발급", type:"header", borderless:true},
                //         {view:"icon", icon:"wxi-close", tooltip:"Close window", click: function(){
                //             popup_template.close();
                //         }}
                //         ]
                //     },
                //     body: popup_template
                // }).show();
            }
        }
    ],
    rules:{
        /*loginId: function (value){
            if(value  == ""){
                this.elements.loginId.define("invalidMessage","로그인 아이디를 입력해주세요.");
                this.elements.loginId.refresh();
                return false;
            }
            if(value.length < 2){
                this.elements.loginId.define("invalidMessage","2자리이상입력해주세요.");
                this.elements.loginId.refresh();
                return false;
            }
            return true;
        },
        password: function () {
            //수정시
            if(this.elements.userId.getValue() !== ""){
                return true;
            //신규 등록시
            }else{
                if(this.elements.password.getValue() === ""){
                    this.elements.password.define("invalidMessage","2자리이상입력해주세요.");
                    this.elements.password.refresh();
                    return false;
                }
                return true;
            }
        }*/

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
        // popup_template, 
        {
            cols: [
                {
                    rows:[
                        { template: "학생 목록", height: 32, css:"ctrlTitle"},
                        gridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "사용자 정보", height: 32, css:"ctrlTitle" },
                        formMaster
                    ]
                },
            ]
        },
    ]
};
