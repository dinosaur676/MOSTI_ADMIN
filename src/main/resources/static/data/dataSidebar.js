function convertMenuList(list){
  return list.map(item => {
      return {id:item.menu.id,icon: "mdi mdi-view-column", value:item.menu.name, url: item.menu.path, data:item.childrenMenuList?convertMenuList(item.childrenMenuList) : []};
  });
}
export const dataSidebar = convertMenuList(userInfo.menuRoleMappingList);
console.log(dataSidebar);


