var TableView = require("./abstract/table");

/**
 * System build information view.
 *
 * Presents os.Build in tabular format.
 */
var SystemBuildView = TableView.extend({
    /**
     * @override TableView
     */
    getHead: function() {
        return [
            [
                "Property",
                "Value"
            ]
        ];
    },

    /**
     * @override TableView
     */
    getRow: function(pair) {
        return pair;
    }
});

module.exports = SystemBuildView;
