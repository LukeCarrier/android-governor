var _    = require("underscore"),
    home = require("./home");

function initialize(router, sidebarView) {
    var routes = {
        home: home
    };

    _.each(routes, function(route) {
        route.registerRoutes(router);
        route.registerSidebarItems(sidebarView.model);
    });

    return router;
}

module.exports = initialize;
