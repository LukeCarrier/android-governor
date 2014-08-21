/* Prepare our dependencies as early on as possible. */
var $        = require("jquery"),
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
    sidebarView = new SidebarView({ model: new Sidebar() }),
    router      = new Router(),
    routes      = require("./routes")(router, contentView, sidebarView);

/* Try and load the developer's local configuration if possible. Be sure to
 * relocate this file before doing production builds! */
try {
    require("./local")();
} catch (e) {}

router.on("route", function() {
    sidebarView.render();
});

Backbone.history.start();
