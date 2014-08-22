var SidebarItem     = require("../models/sidebar-item"),
    SystemBuild     = require("../models/system-build"),
    SystemBuildView = require("../views/system-build");

function registerRoutes(router, contentView) {
    router.route("", "");

    router.on("route:", function() {
        var systemBuild     = new SystemBuild(),
            systemBuildView = new SystemBuildView({ model: systemBuild });

        contentView.setActiveTitle("System information")
                   .setActiveView(systemBuildView)
                   .render();

        systemBuild.fetch();
    });
}

function registerSidebarItems(sidebar) {
    sidebar.items.add(new SidebarItem({
        brand: true,
        label: "Governor",
        route: ""
    }));
}

module.exports = {
    registerRoutes:       registerRoutes,
    registerSidebarItems: registerSidebarItems
};
