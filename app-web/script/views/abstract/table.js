var _        = require("underscore"),
    Backbone = require("backbone");

/**
 * Table view.
 *
 * Because LinkedIn don't know how to NPM. ;)
 */
var TableView = Backbone.View.extend({
    /**
     * Class name (for CSS frameworks).
     *
     * Bootstrap uses this, so let's go with it.
     *
     * @var string
     */
    className: "table",

    /**
     * Tag name.
     *
     * We'll get bound to a visible DOM element later, either through this.el
     * or this.setElement(). Let Backbone give us an element in the meantime.
     *
     * @var string
     */
    tagName: "table",

    /**
     * Reusable templates.
     *
     * These templates are cached for reuse throughout the class and can be
     * overridden by subclasses.
     *
     * @var object<function>
     */
    templates: {
        head: _.template("<thead><%= content %></thead>"),
        body: _.template("<tbody><%= content %></tbody>"),

        row: _.template("<tr><%= content %></tr>"),
        cell: _.template("<td><%= content %></td>"),
        headCell: _.template("<th><%= content %></th>")
    },

    /**
     * Get the table heading.
     *
     * An array like so:
     *
     * [
     *     [
     *         "Forename",
     *         "Surname"
     *     ]
     * ]
     *
     * Would become:
     *
     * <thead>
     *     <tr>
     *         <th>Forename</th>
     *         <th>Surname</th>
     *     </tr>
     * </thead>
     *
     * @return string[][]
     */
    getHead: function() {
        throw new Error("getHead() must be implemented in a subclass");
    },

    /**
     * Format a row from a record in this.model.
     *
     * @param Backbone.Model model The Backbone model object, straight out of
     *                             your collection.
     *
     * @return string[] An array of values to go in the table.
     */
    getRow: function(model) {
        throw new Error("getRow(model) must be implemented in a subclass");
    },

    /**
     * Initialiser.
     *
     * Set up a handler for change events on the model.
     *
     * @return void
     */
    initialize: function() {
        this.model.bind("change", this.render, this);
    },

    /**
     * Render the table.
     *
     * @return TableView this, for method chaining.
     */
    render: function() {
        this.$el.html(this.renderHead() + this.renderBody());

        return this;
    },

    /**
     * Render the <tbody> element.
     *
     * To simplify mapping data in the model objects to cells in the table, all
     * model objects will get mapped through your table's getRow()
     * implementation.
     *
     * @return string HTML markup.
     */
    renderBody: function() {
        var bodyHtml;

        if (this.model instanceof Backbone.Collection) {
            bodyHtml = this.model.map(_.bind(this.renderRow, this));
        } else if (this.model instanceof Backbone.Model) {
            bodyHtml = _.map(this.model.pairs(), _.bind(this.renderRow, this));
        } else {
            throw new Error("renderBody() expects either Backbone.Collection or Backbone.Model");
        }

        return this.templates.body({ content: bodyHtml });
    },

    /**
     * Render the <thead> element.
     *
     * @return string HTML markup.
     */
    renderHead: function() {
        var headHtml = _.map(this.getHead(), function(row) {
            var rowHtml = _.map(row, function(cell) {
                return this.templates.headCell({ content: cell });
            }, this);

            return this.templates.row({ content: rowHtml });
        }, this);

        return this.templates.head({ content: headHtml });
    },

    /**
     * Render a <tr> element containing data.
     *
     * @param model The row object derived from the model by getRow().
     *
     * @return string HTML markup.
     */
    renderRow: function(model) {
        var rowHtml = _.map(this.getRow(model), function(cell) {
            return this.templates.cell({ content: cell });
        }, this);

        return this.templates.row({ content: rowHtml });
    }
});

module.exports = TableView;
