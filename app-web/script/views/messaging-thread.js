var TableView = require("./abstract/table");

var MessagingThreadView = TableView.extend({
    /**
     * @override TableView
     */
    getHead: function() {
        return [
            [
                "Message"
            ]
        ];
    },

    /**
     * @override TableView
     */
    getRow: function(row) {
        return [
            row.get("address"),
            row.get("body")
        ];
    }
});

module.exports = MessagingThreadView;
