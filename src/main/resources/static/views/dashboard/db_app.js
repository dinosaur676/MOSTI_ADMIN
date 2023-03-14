const mainView = {
    id: "mainView",
    type: "space",
    rows: [
        ctrlView,
        {
            cols: [
                {
                    rows:[
                        { template: "발전량 목록", height: 32, css:"ctrlTitle" },
                        gridMaster
                    ]
                },
                { view: "resizer" },
                {
                    rows: [
                        { template: "발전량 정보", height: 32, css:"ctrlTitle" },
                        formMaster
                    ]
                }
                
            ]
        }
    ]
};
