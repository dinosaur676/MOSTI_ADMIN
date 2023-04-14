import {logic} from "./logic.js"

const tokenTypes = logic.getTokenTypes();


const gridMaster = {
    view: "datatable",
    id: "dtMaster",
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
    	{ view: "text", name:"tokenId", label: "id", readonly:true },
    	{ view: "text", name:"metaData", label: "토큰 정보", height: 300, readonly:true},
        { view: "text", name: "tokenOwnerName",  label: "토큰 주인", readonly: true},
        { view: "text", name: "type", label: "토큰 타입", readonly: true },
        { view: "button", label: "토큰 생성", type: "form", align: "right",
            css:"webix_primary",
            click: function() {
                //$$("tokenType").define("options", logic.getTokenTypes());
                //$$("tokenType").refresh();
                webix.ui(popup).show();
            }
        },
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

const popup = {
    view : "window",
    id: "create_token_popup",
    height: 600,
    width: 600,
    close: true,
    position: "center",
    head: {
        cols: [
            {template: "토큰 생성", type: "header", borderless:true},
            {
                view: "icon", icon: "wxi_close", tooltip: "닫기", click: function () {
                    $$("create_token_popup").close();
                }
            },
        ]
    },
    body: {
        id: "popup_body",
        rows: [
            {view: "text", id:"metaData", label: "", height: 400, placeholder: "토큰 정보", required: true},
            {view: "text", id:"tokenType", type: "number", name: "tokenType", label: "토큰 종류", required: true},
            {view:"button", id: "createTokenButton", height: 50, value: "토큰 생성",
                click: function () {
                    const param = {
                        "metaData" : $$("metaData").getValue(),
                        "tokenType" : parseInt($$("tokenType").getValue())
                    }
                    logic.createToken(param);
                }
            }

        ]
    }
}

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
                        { template: "토큰 목록", height: 32, css:"ctrlTitle" },
                        gridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "토큰 정보", height: 32, css:"ctrlTitle" },
                        formMaster
                    ]
                }
            ]
        }
    ]
};
