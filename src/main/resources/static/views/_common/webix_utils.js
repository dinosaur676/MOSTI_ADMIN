const getLocalItems = function(object, localId){
  if(object._collection)
    return $$(object._collection.find(item=>item.localId===localId).id);
  else
    return $$(object.find(item=>item.localId===localId).id);
}

function openSearch(){
  this.config.icon = (this.config.icon == "wxi-close") ? "mdi mdi-magnify" : "wxi-close";
  const input = getLocalItems(this.getParentView(), "srch:field")
  input.isVisible() ? input.hide() : input.show();
  const header =getLocalItems(this.getParentView(), "header");
  header.isVisible() ? header.hide() : header.show();
  this.refresh();
}

export {getLocalItems, openSearch}
