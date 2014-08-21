var Backbone = require("backbone");

/**
 * Sidebar item.
 */
var SidebarItem = Backbone.Model.extend({
    defaults: {
        active: false,
        brand: false,
        label: "",
        route:  ""
    }
});

module.exports = SidebarItem;
