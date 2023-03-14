import {getLocalItems, openSearch} from "../_common/webix_utils.js";
const CurrenciesView ={
	width: 387,
	id:"CurrenciesView",
	rows: [
		{
			type: "toolbar", height: 48, padding: {right: 8}, cols: [
				{template: "Exchange rates", type: "header", borderless: true, localId: "header"},
				{
					view: "text", localId: "srch:field", placeholder: "Type to search", hidden: true,
					on: {
						onViewShow() {
							const self = this;
							webix.delay(function () {
								self.focus();
							});
						},
						onTimedKeyPress() {
							const input = this.getValue().toLowerCase();
							getLocalItems($$("CurrenciesView"), "currencies").filter(obj => obj.name.indexOf(input) !== -1);
						}
					}
				},
				{view: "icon", icon: "mdi mdi-magnify", click:openSearch }
			]
		},
		{
			view: "list", localId: "currencies",
			css: "currencies_list",
			select: true,
			yCount: 4,
			type: {
				height: 64,
				template: obj => {
					const delta = parseFloat(obj.delta) > 0 ? "green" : "red";
					return `
								<span class='icon' style='background-color:${obj.color};color:#ffffff'>${obj.icon}</span>
								<span class='value'>$${obj.value}</span>
								<span class='delta ${delta}'>${obj.delta}</span>`;
				}
			},
			on: {
				onItemClick: (id) => {
					getLocalItems($$("ProgressView"),"chart").callEvent("currency:select", [id]);
				},
			}
		}
	]
};

function init(data){
	const parseData = data? data: [
			{name: "euro", icon: "€", value: 321.12, delta: "-0.2", color: "#1CA1C1"},
			{name: "dollar", icon: "$", value: 345.76, delta: "+0.2", color: "#55CD97"},
			{name: "yen", icon: "¥", value: 567.26, delta: "+0.3", color: "#FDBF4C"},
			{name: "pound", icon: "£", value: 234.64, delta: "-0.1", color: "#FF5C4C"}
		];
	getLocalItems($$("CurrenciesView"), "currencies").parse(parseData);
	getLocalItems($$("CurrenciesView"), "currencies").select(getLocalItems($$("CurrenciesView"), "currencies").getFirstId());
}
export {CurrenciesView, init}
