var SidebarItem = require("../models/sidebar-item");

function registerRoutes(router) {
    router.route("", "home");

    router.on("route:home", function() {
    });
}

function registerSidebarItems(sidebar) {
    sidebar.items.add(new SidebarItem({
        brand: true,
        label: "Governor",
        path:  "#"
    }));
}

module.exports = {
    registerRoutes:       registerRoutes,
    registerSidebarItems: registerSidebarItems
};
