var Backbone = require("backbone");

var ContentView = Backbone.View.extend({
    activeView: null,

    el: ".content",

    render: function() {
        this.$el.html(this.activeView.render().el);
    },

    setActiveView: function(view) {
        this.activeView = view;
        this.render();
    }
});

module.exports = ContentView;
