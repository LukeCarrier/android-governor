var _    = require("underscore"),
    home = require("./home");

module.exports = function(router) {
    var routes = {
        home: home
    };

    _.each(routes, function(route) {
        route.registerRoutes(router);
    });

    return router;
};
