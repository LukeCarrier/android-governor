var _               = require("underscore"),
    Backbone        = require("backbone"),
    SidebarItemView = require("./sidebar-item");

/**
 * Sidebar item list view.
 *
 * Represents a list of sidebar items within the view hierarchy.
 */
var SidebarView = Backbone.View.extend({
    /**
     * Parent element selector.
     *
     * @var string
     */
    el: ".sidebar ul",

    /**
     * Render the view.
     *
     * @return SidebarItemListView This instance, for method chaining.
     */
    render: function() {
        var childHtml = this.model.items.map(function(item) {
            return new SidebarItemView({ model: item }).render().el;
        });

        this.$el.html(childHtml);

        return this;
    }
});

module.exports = SidebarView;
