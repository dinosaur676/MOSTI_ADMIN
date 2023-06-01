import {logic} from "./logic.js"

const getParams = function () {
    const params = {

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
        { id: "id", header: "공지사항ID", hidden: true},
        { id: "writer", header: "작성자", width: 120, sort:"string"},
        { id: "title", header: "제목", width: 120, sort:"string"},
        { id: "content", header: "내용", width: 120, sort:"string"},
        { id: "createdOn", header: "날짜", width: 120, sort:"string"},
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
        { view: "text", id: "id", name: "id", label: "공지사항ID", disabled: true},
        { view: "text", id: "writer", name: "writer", label: "작성자"},
        { view: "text", id: "title", name: "title", label: "제목"},
        { view: "text", id: "content", name: "content", label: "내용", height: 400, scroll:true},
        { view: "text", id: "createdOn", name: "createdOn", label: "날짜"},
        {
            view: "toolbar", borderless: true, margin: 20,
            cols:[
                { gravity: 3 },
                { view: "button", label: "삭제", type: "danger", width: 80,
                    css:"webix_danger",
                    click: function() {
                        var params = $$("frmMaster").getValues();
                        //console.log(model);
                        webix.confirm({
                            title: "공지사항 삭제",
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
                        var params = $$("frmMaster").getValues();
                        logic.update(params);
                        //console.log(model);

                    }
                },
                { view: "button", label: "저장", type: "form", width: 80, align: "right",
                    css:"webix_primary",
                    click: function() {
                        var params = $$("frmMaster").getValues();
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
        {view: "text", id: "searchUserName", label: "이름", labelAlign:"right"},
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
                        { template: "공지사항 목록", height: 32, css:"ctrlTitle"},
                        gridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "공지사항 정보", height: 32, css:"ctrlTitle" },
                        formMaster
                    ]
                }
            ]
        }
    ]
};
