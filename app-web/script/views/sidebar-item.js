var _        = require("underscore"),
    Backbone = require("backbone");

/**
 * Sidebar item view.
 *
 * Represents an individual sidebar item within the view hierarchy.
 */
var SidebarItemView = Backbone.View.extend({
    tagName: "li",

    template: _.template('<a href="#/<%= route %>"><%= label %></a>'),

    initialize: function() {
        this.model.bind("change", this.render, this);
    },

    render: function() {
        this.$el.html(this.template(this.model.toJSON()));

        this.$el.toggleClass("active", this.model.get("active"));
        this.$el.toggleClass("brand",  this.model.get("brand"));

        return this;
    }
});

module.exports = SidebarItemView;
