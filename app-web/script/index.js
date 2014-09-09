/* Prepare our dependencies as early on as possible. */
var $        = require("jquery")
    _        = require("underscore"),
    Backbone = require("backbone");

/* Instruct Backbone to use this jQuery instance, else calls to $el in our views
 * will bomb out. */
Backbone.$ = $;

/* Now we can initialise the router and source all of our routes. We ought to
 * look at dynamically resolving these when the application gets bigger to keep
 * performance respectable. */
var ContentView = require("./views/content"),
    Router      = require("./lib/router"),
    Sidebar     = require("./models/sidebar"),
    SidebarView = require("./views/sidebar");

var contentView = new ContentView(),
    sidebar     = new Sidebar(),
    sidebarView = new SidebarView({ model: sidebar }),
    router      = new Router(),
    routes      = require("./routes")(router, sidebar, contentView);

/* Try and load the developer's local configuration if possible. Be sure to
 * relocate this file before doing production builds! */
var local = require("./local");
if (typeof local === "function") {
    local();
}

/* Render the sidebar for the first time, then ensure we update the active menu
 * item whenever the router handles a navigation event. */
sidebarView.render();
router.bind("route", function(route) {
    var activeItems   = sidebar.items.where({ active: true }),
        routeModule   = _.first(route.split(":")),
        newActiveItem = sidebar.items.findWhere({ route: routeModule });

    activeItems.map(function(item) {
        item.set("active", false);
    });
    newActiveItem.set("active", true);
});

Backbone.history.start();
