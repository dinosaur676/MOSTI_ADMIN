import {getUserActivity} from "../../../data/useractivity.js";
import {getLocalItems} from "../../_common/webix_utils.js";

export function	init(){
	getLocalItems($$("GeoView"), "geo").parse(getUserActivity());
}

export const GeoView = {
	id:"GeoView",
	gravity:2.5, height:500,
	type:"form", rows:[
		{
			view:"geochart",
			localId:"geo",
			borderless:true,
			// provide your own Google API key
			// https://developers.google.com/maps/documentation/javascript/get-api-key
			key:"AIzaSyAi0oVNVO-e603aUY8SILdD4v9bVBkmiTg",
			chart:{
				colorAxis:{
					colors:[ "#FDBF4C", "#1CA1C1", "#FF5C4C" ]
				},
				legend:"none",
				datalessRegionColor:"#D9D8D7"
			}
		}
	]
}
