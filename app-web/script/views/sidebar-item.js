var _        = require("underscore"),
    Backbone = require("backbone");

/**
 * Sidebar item view.
 *
 * Represents an individual sidebar item within the view hierarchy.
 */
var SidebarItemView = Backbone.View.extend({
    tagName: "li",

    template: _.template('<a href="<%= path %>"><%= label %></a>'),

    initialize: function() {
        this.model.on("change", this.render);
    },

    render: function() {
        this.$el.html(this.template(this.model.toJSON()));
        if (this.model.get("brand")) {
            this.$el.addClass("brand");
        }

        return this;
    }
});

module.exports = SidebarItemView;
