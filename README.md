Governor
========

<p align="center">
    <img width="256" height="256" src="http://governorapp.com/assets/logo-dark.png">
</p>

Like AirDroid, but with freedomsauce.

* * *

No longer maintained
--------------------

This project isn't actively developed or maintained any more, nor is it likely to become
actively developed in the future. You're more than welcome to fork it to develop the idea
further!

Overview
--------

Governor is an attempt at providing Android users with a web-based portal to interact with their
phones. At the moment, it's in a "seedling" stage and is starting to provide a base framework for
working with HTTP requests. It's not yet capable of any meaningful device management.

Obtaining
---------

No stable release of Governor exists at the present time, so the application is not listed on the
Google Play Store. We have a Google Group allowing access to unstable development pre-releases. For
access to this list, drop an email to @LukeCarrier.

Building
--------

Governor consists of two distinct applications: the HTTP server running on the device, which also
hosts the dynamic controllers responsible for generating response data, and the web user interface
which the server delivers to browsers.

The application can be built easily -- just run the Gradle wrapper script:

    $ ./gradlew assemble

The web interface is easy to build independently of the overall application -- the dependencies can
be fetched like so:

    $ cd app-web
    $ npm install

> *NOTE*: at present, ```npm install``` will raise warnings about peer dependencies. This warning is
> harmless and will be resolved with a future update to
> [browserify-shim](https://github.com/thlorenz/browserify-shim).

And subsequent builds are just a call to Gulp away:

    $ cd app-web
    $ npm run gulp

You can also watch the web UI for changes, optionally using LiveReload to save time when tweaking
the UI:

    $ cd app-web
    $ npm run gulp:watch

To further speed up frontend development, the UI can be tested on a server independently of the
device hosting the Governor controllers. To achieve this, you need to configure a filter on all
jQuery AJAX requests like so:

    var $ = require("jquery");

    module.exports = function() {
        $.ajaxPrefilter(function(options, originalOptions, jqXhr) {
            options.url = "http://192.168.1.138:8080" + options.url;
        });
    };

Pop the above into ```app-web/script/local.js```, save and allow gulp to rebuild all the assets.

To do
-----

This is an ambitious list, and there's a chance that some of these actions will depend on the device
being rooted. It'd be awesome if we could include all of this functionality, though:

* HTTP server
    * Move HTTP server out of activity and into a service
    * Display a notification when the server is running
* Device management
    * Camera/screenshotting
        * Take photo from front and back cameras
        * Take a screenshot/screencast
    * Clipboard
        * Insert text into clipboard
        * Export contents from clipboard
    * File management
        * Browse, move and copy files
        * Create and modify text files
        * Sample media files
        * View storage information
    * Geolocation
        * Dude, where's my phone?
    * Messaging
        * Read SMS
        * Send SMS
    * Package management
        * Install APKs
        * Remove existing apps
    * Phone calls
        * Initiate phone calls
        * Reject/dismiss incoming phone calls with a message
    * System management
        * View resource usage

Thanks
------

Governor would not be possible were it not for the following excellent open source projects:

* [Google Gson](https://code.google.com/p/google-gson/) - a library for converting Java Objects into
  their JSON representation
* [NanoHttpd](https://github.com/NanoHttpd/nanohttpd) - the tiny, embeddable Java HTTP server
