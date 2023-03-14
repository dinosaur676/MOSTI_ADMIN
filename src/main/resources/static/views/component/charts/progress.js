import { getLocalItems, openSearch} from "../../_common/webix_utils.js";
import { getProgress, shuffle } from "../../../data/progressch.js";

export function init(){
	getLocalItems($$("ProgressView"),"chart").parse(getProgress());
	getLocalItems($$("ProgressView"),"chart").attachEvent(
		"currency:select",function(id) {
			if (!this.ListId || this.ListId !== id){
				this.clearAll();
				this.parse(shuffle());
				this.ListId = id;
			}
		}
	)
}

export const ProgressView  = {
	id: "ProgressView",
	rows:[
		{ type:"toolbar", height:48, padding:{ right:8 }, cols:[
			{ template:"2018 year stats", type:"header", borderless:true, localId:"header" },
			{
				view:"text", localId:"srch:field", placeholder:"Type to filter values above (e.g. 30)", hidden:true,
				on:{
					onViewShow(){
						const self = this;
						webix.delay(function(){ self.focus(); });
					},
					onTimedKeyPress(){
						const input = parseInt(this.getValue());
						if (!isNaN(input)){
							getLocalItems($$("ProgressView"),"chart").filter(obj => obj.a > input);
						}
						else {
							getLocalItems($$("ProgressView"),"chart").filter();
						}
					}
				}
			},
			{ view:"icon", icon:"mdi mdi-magnify", click:openSearch },
		]},
		{
			view:"chart", type:"stackedBar", localId:"chart",
			yAxis:{ template:"", lineColor:"#fff", color:"#fff" },
			xAxis:{ lineColor:"#fff", color:"#fff", template:"" },
			series:[
				{
					value:"#a#",
					color: "#1CA1C1",
					tooltip:{
						template:"#a#"
					}
				},
				{
					value:"#b#",
					color: "#EBEDF0",
					tooltip:{
						template:"#b#"
					}
				}
			],
			padding:{ left:30, bottom:30 }, barWidth:24
		}
	]
}
