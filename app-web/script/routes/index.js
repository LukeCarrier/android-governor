var sidebarItemListView = new SidebarItemListView();

router.on('route:index', function() {
    sidebarItemListView.render();
});
