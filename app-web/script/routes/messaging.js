var MessagingThreads     = require("../models/messaging-threads"),
    MessagingThreadsView = require("../views/messaging-threads"),
    SidebarItem          = require("../models/sidebar-item");

function registerRoutes(router, contentView) {
    router.route("messaging", "messaging");

    router.on("route:messaging", function() {
        var messagingThreads     = new MessagingThreads(),
            messagingThreadsView = new MessagingThreadsView({ model: messagingThreads });

        contentView.setActiveTitle("Messaging")
                   .setActiveView(messagingThreadsView)
                   .render();

        messagingThreads.fetch();
    });
}

function registerSidebarItems(sidebar) {
    sidebar.items.add(new SidebarItem({
        label: "Messaging",
        route: "messaging"
    }));
}

module.exports = {
    registerRoutes:       registerRoutes,
    registerSidebarItems: registerSidebarItems
};
