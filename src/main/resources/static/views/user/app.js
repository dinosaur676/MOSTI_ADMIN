import {logic} from "./logic.js"
const gridMaster = {
    view: "datatable",
    id: "dtMaster",
    select: true,
    headerRowHeight: 35,
    //autowidth: true,
    navigation:false,       //keyboard protect
    columns: [
        { id: "id", header: "ID", width: 80, hidden:true  },
        { id: "userName", header: "이름", width: 100, sort:"string"},
        { id: "loginId", header: "로그인아이디", width: 120, sort:"string"},
        { id: "email", header: "이메일", width: 160, sort:"string", fillspace:true},
        { id: "password", header: "암호", width: 100, sort:"string", hidden:true },
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

const formMaster = {
    view: "form", id: "frmMaster", scroll: true,
    elementsConfig: { labelWidth: 100, labelAlign: "right" },
    elements: [
    	{ view: "text", name:"userId", label: "id", disabled:true },
    	{ view: "text", name:"loginId", label: "로그인아이디",  invalidMessage:"로그인 아이디를 입력해주세요.", bottomLabel:"2자리 이상 입력해주세요." },
        { view: "text", name: "userName",  label: "이름", invalidMessage:"이름을 입력해주세요." },
        { view: "text", name: "email", type:"email",  label: "이메일", /*validate:webix.rules.isEmail, invalidMessage:"이메일을 입력해주세요."*/},
        { view: "text", name: "password", type:"password",  label: "암호" },
        {
            view: "select", id: "type", name: "type",
            label: "유형", value: 1,
            options: [
                { id: "S", value: "판매자" },
                { id: "B", value: "구매자" },
                { id: "A", value: "관리자" }
            ]
        },
        {
            view: "select", id: "status", name: "status", //required: true,
            label: "사용여부", value: 1,
            options: [
                { id: "Y", value: "Y" },
                { id: "N", value: "N" }
            ]
        },

        {
            view: "toolbar", borderless: true, margin: 20,
            cols:[
                { gravity: 3 },
                { view: "button", label: "삭제", type: "danger", width: 80,
                    css:"webix_danger",
                    click: function() {
                        var model = $$("frmMaster").getValues();
                        //console.log(model);
                        webix.confirm({
                            title: "사용자 삭제",
                            ok: "Yes", cancel: "No",
                            text: "선택된 사용자를 삭제하시겠습니까?"
                        }).then(function () {
                            logic.deleteUser(model);
                        })
                    }
                },
                { view: "button", label: "저장", type: "form", width: 80, align: "right",
                    css:"webix_primary",
                    click: function() {
                        var model = $$("frmMaster").getValues();
                        if($$("frmMaster").validate())
                            logic.saveUser(model);
                        //console.log(model);

                    }
                }
            ]
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
                logic.selectUsers(params);
           }
        },
        {},
        {
            view: "button", value: "새 사용자", width: 150, height: 40,
            css: "embTxBtn",
            click: function () {
                logic.makeNewForm();
            }
        }
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
                        { template: "사용자 목록", height: 32, css:"ctrlTitle"},
                        gridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "사용자 정보", height: 32, css:"ctrlTitle" },
                        formMaster
                    ]
                }
            ]
        }
    ]
};
