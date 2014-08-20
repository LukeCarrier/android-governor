/* Prepare our dependencies as early on as possible. */
var $        = require("jquery"),
    Backbone = require("backbone");

/* Instruct Backbone to use this jQuery instance, else calls to $el in our views
 * will bomb out. */
Backbone.$ = $;

/* Now we can initialise the router and source all of our routes. We ought to
 * look at dynamically resolving these when the application gets bigger to keep
 * performance respectable. */
var Router = require("./lib/router"),
    router = new Router(),
    routes = require("./routes")(router);

Backbone.history.start();
