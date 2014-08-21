var Backbone = require("backbone");

/**
 * Android device build information.
 */
var SystemBuild = Backbone.Model.extend({
    url: "/system/build"
});

module.exports = SystemBuild;
