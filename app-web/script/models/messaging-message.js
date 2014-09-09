var Backbone = require("backbone");

var MessagingMessage = Backbone.Model.extend({
    urlRoot: "/messaging/messages",
    idAttribute: "id"
});

module.exports = MessagingMessage;
