var Backbone = require("backbone");

/**
 * Sidebar.
 */
var Sidebar = Backbone.Model.extend({
    items: new Backbone.Collection()
});

module.exports = Sidebar;
