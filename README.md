Governor
========

Like AirDroid, but with freedomsauce.

* * *

Governor is an attempt at providing Android users with a web-based portal to
interact with their phones. At the moment, it's in a "seedling" stage and is
starting to provide a base framework for working with HTTP requests. It's not
yet capable of any meaningful device management.

To do
-----

This is an ambitious list, and there's a chance that some of these actions
will depend on the device being rooted. It'd be awesome if we could include all
of this functionality, though:

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

Governor would not be possible were it not for the following excellent open
source projects:

* [NanoHttpd](https://github.com/NanoHttpd/nanohttpd) - the tiny, embeddable
  Java HTTP server

