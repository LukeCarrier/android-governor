/**
 * Sidebar item view.
 *
 * Represents an individual sidebar item within the view hierarchy.
 */
var SidebarItemView = Backbone.View.extend({
    tagName: "li",

    template: _.template('<a href="<%= path %>"><%= label %></a>'),

    render: function() {
        this.$el.html(this.template(this.model));
        return this;
    }
});

/**
 * Sidebar item list view.
 *
 * Represents a list of sidebar items within the view hierarchy.
 */
var SidebarItemListView = Backbone.View.extend({
    /**
     * Parent element selector.
     *
     * @var string
     */
    el: '.sidebar ul',

    /**
     * Array of sidebar items.
     *
     * @var SidebarItemView[]
     */
    items: [],

    /**
     * Add an item.
     *
     * @param SidebarItemView item The item to add.
     *
     * @return SidebarItemListView This instance, for method chaining.
     */
    addItem: function(item) {
        this.items.push(item);
        return this;
    },

    /**
     * Render the view.
     *
     * @return SidebarItemListView This instance, for method chaining.
     */
    render: function() {
        var html  = '',
            items = this.items.slice(0);

        items.unshift(new SidebarItemView({
            className: 'brand',
            label:     'Governor',
            path:      '#'
        }));

        childHtml = _.map(items, function(item) {
            return item.render();
        }).join('');

        this.$el.html(childHtml);

        return this;
    }
});
