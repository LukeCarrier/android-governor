var SidebarItem     = require("../models/sidebar-item"),
    SystemBuild     = require("../models/system-build"),
    SystemBuildView = require("../views/system-build");

function registerRoutes(router, contentView) {
    router.route("", "home");

    router.on("route:home", function() {
        var systemBuild     = new SystemBuild(),
            systemBuildView = new SystemBuildView({ model: systemBuild });

        contentView.setActiveView(systemBuildView);

        systemBuild.fetch();
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
