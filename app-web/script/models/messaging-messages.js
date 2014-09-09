var Backbone = require("backbone");

var MessagingMessages = Backbone.Collection.extend({
    url: "/messaging/messages",
});

module.exports = MessagingMessages;
