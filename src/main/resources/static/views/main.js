import {appHeader} from "./_common/header.js";
import {sidebar} from "./_common/sidebar.js";
import {topbar} from "./_common/topbar.js";
const jsPath = document.getElementById('mainViewJs').getAttribute('data-name');

const {logic} = await import("/views/"+jsPath+"/logic.js");
const {mainView} = await import("/views/"+jsPath+"/app.js");


webix.ready(() => {
	// if (!webix.env.touch && webix.env.scrollSize) {
	// 	webix.CustomScroll.init();
	// }
  //  webix.skin.material = {
  //      fontFamily: 'Noto Sans KR'
  //  };
	webix.ui({
		css: "bgLight",
        cols: [
            {
                gravity: 1000,
                rows: [
                    appHeader,
                    {
                        type: "clean",
                        cols: [
                            sidebar,
                            mainView
                        ]
                    }
                ]
            }
        ]
	});

  $$("sidebar1").data.each(item=>{
    if( window.location.href.includes(item.url)) $$("sidebar1").select(item.id);
  });

  $$('sidebar1').attachEvent("onItemClick", function(id, e, node){
      const item = this.getItem(id);
      const _selectedId = $$('sidebar1')._selected[0];
      if(_selectedId !== id && item.url){
         window.location.href = item.url;
      }
  });

  logic.init();
  webix.i18n.setLocale("ko-KR");
});
