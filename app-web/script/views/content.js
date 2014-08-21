var Backbone = require("backbone");

var ContentView = Backbone.View.extend({
    activeView: null,

    el: ".content",

    render: function() {
        this.$el.html(this.activeView.render().el);

        return this;
    },

    setActiveView: function(view) {
        this.activeView = view;
        this.render();

        return this;
    }
});

module.exports = ContentView;
