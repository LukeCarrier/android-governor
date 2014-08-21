var browserify = require("browserify"),
    concat     = require("gulp-concat"),
    del        = require("del"),
    fs         = require("fs"),
    gulp       = require("gulp"),
    less       = require("gulp-less"),
    liveReload = require("gulp-livereload"),
    minifyCss  = require("gulp-minify-css"),
    minifyHtml = require("gulp-minify-html"),
    path       = require("path"),
    sourcemaps = require("gulp-sourcemaps"),
    uglify     = require("gulp-uglify"),
    util       = require("gulp-util"),
    source     = require("vinyl-source-stream"),
    watchify   = require("watchify");

var useLiveReload = !!util.env["live-reload"];

var paths = {
    html:      "html/*.html",
    builtHtml: "out",

    scriptGovernor: "./script/index.js", // must be relative for Browserify
    scriptVendorIe: [
        "bower_components/html5shiv/dist/html5shiv.js",
        "bower_components/respond/dest/respond.src.js"
    ],
    builtScript:          "out/js",
    builtScriptGovernor:  "governor.min.js",
    builtScriptVendorIe:  "vendor-ie.min.js",
    builtScriptSourcemap: ".",

    style:               "less/*.less",
    builtStyle:          "out/css",
    builtStyleAll:       "all.min.css",
    builtStyleSourcemap: ".",

    builtLiveReload: "out/**/*"
};

/**
 * Source a set of paths, perform non-browserify transforms and return a stream.
 *
 * @param string srcPath
 * @param string targetPath
 *
 * @return stream
 */
function sourceScript(srcPath, targetPath) {
    return gulp.src(srcPath)
               .pipe(sourcemaps.init())
                   .pipe(concat(targetPath))
                   .pipe(uglify())
                   .pipe(sourcemaps.write(paths.builtScriptSourcemap));
}

/**
 * Source a set of paths and return a browserify or object.
 *
 * @param string  srcPath
 * @param boolean useWatchify
 */
function sourceScriptBrowserify(srcPath) {
    return browserify({
        cache:        {},
        debug:        true,
        entries:      [srcPath],
        fullPaths:    true,
        packageCache: {}
    });
}

function processScript(stream) {
    stream.pipe(gulp.dest(paths.builtScript));

    return stream;
}

/*
 * Clean up the build directory
 */
gulp.task("clean", function(cb) {
    del("out", cb);
});

/*
 * Static HTML
 */
gulp.task("html", function() {
    var minifyOptions = {
        comments: true,    // just until the conditionals option is fixed
        conditionals: true
    };

    return gulp.src(paths.html)
               .pipe(minifyHtml(minifyOptions))
               .pipe(gulp.dest(paths.builtHtml));
});

/*
 * Cross-browser script (via Browserify)
 */
gulp.task("script", function() {
    var browserifyer = sourceScriptBrowserify(paths.scriptGovernor),
        stream       = browserifyer.bundle().pipe(source(paths.builtScriptGovernor));

    if (fs.existsSync("script/local.js")) {
        util.log(util.colors.red("local.js found") + " -- don't ship this build to production!");
    }

    return processScript(stream);
});

/**
 * Cross-browser script (via Watchify).
 */
gulp.task("script-watch", function() {
    var browserifyer = sourceScriptBrowserify(paths.scriptGovernor, true),
        watchifyer   = watchify(browserifyer);

    watchifyer.on("update", function() {
        var stream = watchifyer.bundle().pipe(source(paths.builtScriptGovernor));
        processScript(stream);
    });
});

/*
 * Poly fill script for IE
 */
gulp.task("script-vendor-ie", function() {
    var stream = sourceScript(paths.scriptVendorIe, paths.builtScriptVendorIe);

    return processScript(stream);
});

/*
 * LESS to CSS
 */
gulp.task("style", function() {
    return gulp.src(paths.style)
               .pipe(less())
               .pipe(minifyCss())
               .pipe(concat(paths.builtStyleAll))
               .pipe(gulp.dest(paths.builtStyle));
});

/*
 * Default task
 */
gulp.task("default", ["html", "script", "script-vendor-ie", "style"]);

/*
 * Watch for changes, build immediately and (optionally) live reload
 */
gulp.task("watch", ["script-watch"], function() {
    gulp.watch(paths.html,     ["html"]);
    gulp.watch(paths.scriptIe, ["script-ie"]);
    gulp.watch(paths.style,    ["style"]);

    /* Add another watcher on build artefacts -- this will ensure LR doesn't
     * handle the change events before Gulp has finished outputting them. */
    if (useLiveReload) {
        liveReload.listen();

        gulp.watch(paths.builtLiveReload, function(file) {
            var relativePath = path.relative(".", file.path);
            util.log(util.colors.magenta(relativePath) + " live reloaded");

            liveReload.changed(file.path);
        });
    }
});
