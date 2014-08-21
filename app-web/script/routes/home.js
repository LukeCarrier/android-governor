var SidebarItem = require("../models/sidebar-item");

function registerRoutes(router, contentView) {
    router.route("", "home");

    router.on("route:home", function() {
        contentView.setActiveView(systemBuildView);
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
