var _    = require("underscore"),
    home = require("./home");

function initialize(router, contentView, sidebarView) {
    var routes = {
        home: home
    };

    _.each(routes, function(route) {
        route.registerRoutes(router, contentView);
        route.registerSidebarItems(sidebarView.model);
    });

    return router;
}

module.exports = initialize;
