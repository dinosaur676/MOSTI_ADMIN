/*
webix.ui({
	view:"popup",
	body:{
		view:"list",
		id:"my_pop",
		autoheight:true,
		width:150,
		borderless:true,
		css:"profile_menu",
		data:[
			{ id:"profile", value:"My profile", icon:"mdi mdi-account" },
			{ id:"dashboard", value:"My dashboard", icon:"mdi mdi-view-dashboard" },
			{ id:"logout", value:"Log out", icon:"mdi mdi-logout" }
		],
		on:{
			onItemClick:id => {
				if (id === "logout")
					this.show("/login");
				else{
					(id === "profile") ? this.show("profile/profileinfo/about") :	this.show(id);
					this.getRoot().hide();
				}
			}
		}
	}
});
*/

export const userTemplate = {
	borderless: true,
	css: "user",
	width: 168,
	height: 44,
	id:"userTemplate",
	popup: "my_pop",
	template() {
		const html = `
        <div class="userInner">
            <!--<span class="userAvatar">
                <img class="userImage" src="/assets/image/avatar.png">
            </span>-->
            <span class="userName">
                <span>${userInfo.username}</span>
                <!--<span class="mdi mdi-menu-down mdi-24px"></span>-->
            </span>            
        </div>`;
		return html;
	},
	onClick: {
		"userInner":function(){
			const profileMenu = $$("my_pop");
			if(profileMenu.isVisible()) profileMenu.hide();
			else profileMenu.show()
			//$$("my_pop").show();
		}
	}
};
