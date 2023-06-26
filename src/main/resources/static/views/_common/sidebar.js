
export const sidebar = {
	view: "sidebar",
	id: "sidebar1",
	css: "webix_dark darkSidebar",
	select: true,
	width: 210,
	data: convertMenuList(userInfo.menuRoleMappingList),

	submenuConfig:{
		width:300,
		template:"> #value#",
	},
	ready() {
		//this.select("users");
	}
};

function convertMenuList(list){
	return list?.map(item => {
		return {id:item.menu.id,icon: "mdi mdi-view-column", value:item.menu.name, url: item.menu.path, data:item.childrenMenuList?convertMenuList(item.childrenMenuList) : []};
	});
}
