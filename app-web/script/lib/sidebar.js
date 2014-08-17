var SidebarItems = Backbone.Collection.extend({
    url: '/governor/sidebar_items'
});

var SidebarItemView = Backbone.View.extend({
    tagName: "li",

    template: _.template('<a href=""></a>'),

    render: function() {
        this.$el.html(this.template(this.model));
        return this;
    }
})

var SidebarItemListView = Backbone.View.extend({
    el: '.sidebar ul',

    initialize: function() {
        //
    },

    render: function() {
        var sidebarItems = new SidebarItems();
        sidebarItems.fetch({
            success: function() {
                this.$el.html('<li class="brand"><a href="#">Governor</a></li>');
            }
        });

        return this;
    }
});
