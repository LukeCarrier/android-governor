var _        = require("underscore"),
    Backbone = require("backbone");

var ContentView = Backbone.View.extend({
    activeTitle: null,

    activeView: null,

    /**
     * DOM element selector.
     *
     * @var string
     */
    el: ".content",

    subElements: {
        body: ".content-body",
        bodyInner: ".content-body > *",
        head: ".content-head"
    },

    bodyInnerHtml: '<div class="content-body-inner"></div>',

    destroyActiveView: function() {
        this.activeView.undelegateEvents();
        this.activeView.$el.removeData()
                           .unbind();
        this.activeView.remove();
        Backbone.View.prototype.remove.call(this.activeView);

        this.activeView = null;

        this.$el.find(this.subElements.body).html(this.bodyInnerHtml);
    },

    hasActiveView: function() {
        return !!this.activeView;
    },

    render: function() {
        if (this.hasActiveView()) {
            this.$el.find(this.subElements.head).first().text(this.activeTitle);
            this.activeView.render()
                           .delegateEvents();
            this.$el.find(this.subElements.bodyInner).first().replaceWith(this.activeView.$el);
        }

        return this;
    },

    setActiveView: function(view) {
        if (this.hasActiveView()) {
            this.destroyActiveView();
        }

        this.activeView = view;

        return this;
    },

    setActiveTitle: function(title) {
        this.activeTitle = title;

        return this;
    }
});

module.exports = ContentView;
