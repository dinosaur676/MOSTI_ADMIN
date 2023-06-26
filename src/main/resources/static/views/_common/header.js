import {userTemplate} from "./userTemplate.js";

export const appHeader = {
	view: "toolbar",
	css: "webix_dark greenHeader",
	paddingX: 10,
	paddingY: 6,
	cols: [
		{
			view: "icon",
			icon: "mdi mdi-menu",
			click: function(){
				$$("sidebar1").toggle()
			}
		},
		{
			view: "label",
			label: "REMS / ADMIN"
		},
		{},
/*		{
			view: "icon",
			icon: "mdi mdi-information"
		},
		{
			view: "icon",
			icon: "mdi mdi-bell",
			badge: 5
		},
		{
			view: "icon",
			icon: "mdi mdi-settings"
		},
		{
			width: 10
		},*/
		userTemplate
		,
		{
			view: "popup",
			body: {
				view: "list",
				id: "my_pop",
				autoheight: true,
				width: 80,
				borderless: true,
				css: "profile_menu",
				data: [
					{id: "profile", value: "My profile", icon: "mdi mdi-account"},
					{id: "dashboard", value: "My dashboard", icon: "mdi mdi-view-dashboard"},
					{id: "logout", value: "Log out", icon: "mdi mdi-logout"}
				],
				on: {
					onItemClick: id => {
						if (id === "logout")
							this.show("/login");
						else {
							(id === "profile") ? this.show("profile/profileinfo/about") : this.show(id);
							this.getRoot().hide();
						}
					}
				}
			}
		},
		{
			view: "button", id: "logout_btn", label: "", type: "icon", icon: "mdi mdi-logout", width: 60, height: 40,
			click: async () => {
				const promise = await apiWrapper.post('/oauth2/logout', {
					"Content-type": "application/json",
							"X-CSRF-TOKEN": token
				})
				if(promise.status === '00'){
					window.location.href = '/'
				}
			}

		},
	]
};
