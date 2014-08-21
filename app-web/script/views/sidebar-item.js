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

        if (this.model.get("active")) {
            this.$el.addClass("active");
        }

        if (this.model.get("brand")) {
            this.$el.addClass("brand");
        }

        return this;
    }
});

module.exports = SidebarItemView;
