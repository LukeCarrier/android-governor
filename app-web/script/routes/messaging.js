var MessagingThread      = require("../models/messaging-thread"),
    MessagingThreadView  = require("../views/messaging-thread"),
    MessagingThreads     = require("../models/messaging-threads"),
    MessagingThreadsView = require("../views/messaging-threads"),
    SidebarItem          = require("../models/sidebar-item");

function registerRoutes(router, contentView) {
    router.route("messaging/thread/:personId", "messaging:thread");
    router.route("messaging", "messaging");

    router.on("route:messaging", function() {
        var messagingThreads     = new MessagingThreads(),
            messagingThreadsView = new MessagingThreadsView({ model: messagingThreads });

        contentView.setActiveTitle("Messaging threads")
                   .setActiveView(messagingThreadsView)
                   .render();

        messagingThreads.fetch();
    });

    router.on("route:messaging:thread", function(personId) {
        var messagingThread     = new MessagingThread({ personId: personId }),
            messagingThreadView = new MessagingThreadView({ model: messagingThread });

        contentView.setActiveTitle("Messaging conversation")
                   .setActiveView(messagingThreadView)
                   .render();

        messagingThread.fetch();
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
