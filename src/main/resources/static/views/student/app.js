import {logic} from "./logic.js"

const searchType = ["사용자 목록", "학생 목록"]
const logicFunction = [logic.selectUsers, logic.selectStudents]
let index = 0;
const arraySize = 2

const gridColumns = [
    [
        { id: "userId", header: "ID", width: 80, hidden:true  },
        { id: "userName", header: "이름", width: 100, sort:"string"},
        { id: "loginId", header: "로그인아이디", width: 120, sort:"string"},
        { id: "email", header: "이메일", width: 160, sort:"string", fillspace:true},
        { id: "password", header: "암호", width: 100, sort:"string", hidden:true },
        { id: "status", header: "상태", width: 80, hidden:true, css:{'text-align': 'center'} }
    ],
    [
        { id: "userId", header: "ID", width: 80, hidden:true  },
        { id: "userName", header: "이름", width: 100, sort:"string"},
        { id: "studentId", header: "학번", width: 120, sort:"string"},
        { id: "school", header: "학교", width: 160, sort:"string", fillspace:true},
        { id: "major", header: "전공", width: 100, sort:"string"},
    ]
]
const gridMaster = {
    view: "datatable",
    id: "dtMaster",
    select: true,
    headerRowHeight: 35,
    //autowidth: true,
    navigation:false,       //keyboard protect
    columns: gridColumns[index],
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
    	{ view: "text", name:"userName", label: "이름", invalidMessage:"이름을 입력해주세요."},
        { view: "text", name: "studentId",  label: "학번", invalidMessage:"학번을 입력해주세요." },
        { view: "text", name: "school", label: "학교", disabled:true  },
        { view: "text", name: "major", label: "전공" },
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
                            logic.deleteStudent(index, model);
                        })
                    }
                },
                { view: "button", label: "수정", type: "form", width: 80, align: "right",
                    css:"webix_primary",
                    click: function() {
                        var model = $$("frmMaster").getValues();
                        if($$("frmMaster").validate())
                            logic.updateStudent(model);
                        //console.log(model);

                    }
                },
                { view: "button", label: "저장", type: "form", width: 80, align: "right",
                    css:"webix_primary",
                    click: function() {
                        var model = $$("frmMaster").getValues();
                        if($$("frmMaster").validate())
                            logic.saveStudents(model);
                        //console.log(model);

                    }
                }
            ]
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
                logicFunction[index](params);
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
                        {
                            view: "button", id: "btnGrid", value: searchType[index], height: 32, css: "ctrlTitle",
                            click: function () {
                                index += 1;
                                index %= arraySize;
                                $$("btnGrid").setValue(searchType[index]);
                                $$("dtMaster").refreshColumns(gridColumns[index]);

                                logicFunction[index]();
                            }
                        },
                        gridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "학생 정보", height: 32, css:"ctrlTitle" },
                        formMaster
                    ]
                }
            ]
        }
    ]
};
