var _         = require("underscore"),
    home      = require("./home"),
    messaging = require("./messaging");

/**
 * Initialise routes.
 *
 * At present, intialising routes consists of two tasks:
 *
 * -> Configuring Backbone's router instance with routes for each of the pages
 *    within the application, and
 * -> Setting up sidebar navigation for each primary page.
 *
 * @param Backbone.Router router      The router to assign routes to.
 * @param ContentView     contentView The primary content view which will wrap
 *                                    each view within the application.
 * @param Sidebar         sidebar     The sidebar to populate with navigation
 *                                    items.
 *
 * @return function[][] A module containing each of the route modules.
 */
function initialize(router, sidebar, contentView) {
    var routes = {
        home:      home,
        messaging: messaging
    };

    _.each(routes, function(route) {
        route.registerRoutes(router, contentView);
        route.registerSidebarItems(sidebar);
    });

    return routes;
}

module.exports = initialize;
