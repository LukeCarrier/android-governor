var Backbone = require("backbone");

/**
 * Sidebar item.
 */
var SidebarItem = Backbone.Model.extend({
    brand: false,
    label: "",
    path:  "#"
});

module.exports = SidebarItem;
