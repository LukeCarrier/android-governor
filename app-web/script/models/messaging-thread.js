var Backbone = require("backbone");

var MessagingThread = Backbone.Model.extend({
    urlRoot: "/messaging/threads",
    idAttribute: "personId",

    viewUrl: function() {
        var url = "/messaging/thread/" + this.get("personId");

        return (Backbone.history.options.pushState ? "" : "#") + url;
    }
});

module.exports = MessagingThread;
