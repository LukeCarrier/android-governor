var _        = require("underscore"),
    Backbone = require("backbone");

/**
 * Main content view.
 *
 * The content view is becoming the Governor user interface's primary view. It
 * wraps key parts of the user interface and handles transitions between
 * different main views.
 */
var ContentView = Backbone.View.extend({
    /**
     * Title of the currently active main view.
     *
     * @var string
     */
    activeTitle: null,

    /**
     * Currently active main view.
     *
     * @var Backbone.View
     */
    activeView: null,

    /**
     * DOM element selector.
     *
     * @var string
     */
    el: ".content",

    /**
     * A few selectors for different regions.
     *
     * @var object<string>
     */
    subElements: {
        body: ".content-body",
        bodyInner: ".content-body > *",
        head: ".content-head"
    },

    /**
     * Markup for subElements.bodyInner.
     *
     * We trash this element when destroying the active view and recreate it
     * when we're finished.
     *
     * @var string
     */
    bodyInnerHtml: '<div class="content-body-inner"></div>',

    /**
     * Throw out the active view.
     *
     * Do not call this method publicly! It's called automatically when the
     * active view is replaced.
     *
     * @return ContentView this, for method chaining.
     */
    destroyActiveView: function() {
        this.activeView.undelegateEvents();
        this.activeView.$el.removeData()
                           .unbind();
        this.activeView.remove();
        Backbone.View.prototype.remove.call(this.activeView);

        this.activeView = null;

        this.$el.find(this.subElements.body).html(this.bodyInnerHtml);

        return this;
    },

    /**
     * Do we have an active view?
     *
     * @return boolean Whether or not we have an active view.
     */
    hasActiveView: function() {
        return !!this.activeView;
    },

    /**
     * @override Backbone.Model
     */
    render: function() {
        if (this.hasActiveView()) {
            this.$el.find(this.subElements.head).first().text(this.activeTitle);
            this.activeView.render()
                           .delegateEvents();
            this.$el.find(this.subElements.bodyInner).first().replaceWith(this.activeView.$el);
        }

        return this;
    },

    /**
     * Set the active view.
     *
     * @param Backbone.View view The view to set as currently active.
     *
     * @return ContentView this, for method chaining.
     */
    setActiveView: function(view) {
        if (this.hasActiveView()) {
            this.destroyActiveView();
        }

        this.activeView = view;

        return this;
    },

    /**
     * Set the title of the currently active view.
     *
     * @param string title The title of the view.
     *
     * @return ContentView this, for method chaining.
     */
    setActiveTitle: function(title) {
        this.activeTitle = title;

        return this;
    }
});

module.exports = ContentView;
