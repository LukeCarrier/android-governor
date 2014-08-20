var sidebar = require("../lib/sidebar");

var sidebarItemListView = new sidebar.SidebarItemListView();

function registerRoutes(router) {
    router.route("", "home");

    router.on("route:home", function() {
        sidebarItemListView.render();
    });
}

module.exports = {
    registerRoutes:      registerRoutes,
    sidebarItemListView: sidebarItemListView
};
