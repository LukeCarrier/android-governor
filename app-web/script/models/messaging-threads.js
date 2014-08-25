var Backbone = require("backbone"),
    MessagingThread = require("./messaging-thread");

var MessagingThreads = Backbone.Collection.extend({
    model: MessagingThread,
    url: "/messaging/threads"
});

module.exports = MessagingThreads;
