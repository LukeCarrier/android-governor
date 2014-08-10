var concat     = require('gulp-concat'),
    del        = require('del'),
    gulp       = require('gulp'),
    less       = require('gulp-less'),
    liveReload = require('gulp-livereload'),
    minifyCss  = require('gulp-minify-css'),
    minifyHtml = require('gulp-minify-html'),
    path       = require('path'),
    sourcemaps = require('gulp-sourcemaps'),
    uglify     = require('gulp-uglify'),
    util       = require('gulp-util');

var useLiveReload = !!util.env['live-reload'];

var paths = {
    html:      'html/*.html',
    builtHtml: 'build',

    script:               [
        'bower_components/jquery/dist/jquery.js',
        'bower_components/bootstrap/dist/js/bootstrap.js',
        'script/*.js'
    ],
    scriptIe:             [
        'bower_components/html5shiv/dist/html5shiv.js',
        'bower_components/respond/dest/respond.src.js'
    ],
    builtScript:          'build/js',
    builtScriptAll:       'all.min.js',
    builtScriptIe:        'ie.min.js',
    builtScriptSourcemap: '.',

    style:               'less/*.less',
    builtStyle:          'build/css',
    builtStyleAll:       'all.min.css',
    builtStyleSourcemap: '.',

    builtLiveReload: 'build/**/*'
};

/*
 * Clean up the build directory
 */
gulp.task('clean', function(cb) {
    del('build', cb);
});

/*
 * Static HTML
 */
gulp.task('html', function() {
    var minifyOptions = {
        comments: true,    // just until the conditionals option is fixed
        conditionals: true
    };

    return gulp.src(paths.html)
               .pipe(minifyHtml(minifyOptions))
               .pipe(gulp.dest(paths.builtHtml));
});

/*
 * Cross-browser script
 */
gulp.task('script', function() {
    return gulp.src(paths.script)
               .pipe(sourcemaps.init())
                   .pipe(uglify())
                   .pipe(concat(paths.builtScriptAll))
                   .pipe(sourcemaps.write(paths.builtScriptSourcemap))
               .pipe(gulp.dest(paths.builtScript));
});

/*
 * Poly fill script for IE
 */
gulp.task('script-ie', function() {
    return gulp.src(paths.scriptIe)
               .pipe(sourcemaps.init())
                   .pipe(uglify())
                   .pipe(concat(paths.builtScriptIe))
                   .pipe(sourcemaps.write(paths.builtScriptSourcemap))
               .pipe(gulp.dest(paths.builtScript));
});

/*
 * LESS to CSS
 */
gulp.task('style', function() {
    return gulp.src(paths.style)
               .pipe(less())
               .pipe(minifyCss())
               .pipe(concat(paths.builtStyleAll))
               .pipe(gulp.dest(paths.builtStyle));
});

/*
 * Default task
 */
gulp.task('default', ['html', 'script', 'script-ie', 'style']);

/*
 * Watch for changes, build immediately and (optionally) live reload
 */
gulp.task('watch', function() {
    gulp.watch(paths.html,     ['html']);
    gulp.watch(paths.script,   ['script']);
    gulp.watch(paths.scriptIe, ['script-ie']);
    gulp.watch(paths.style,    ['style']);

    /* Add another watcher on build artefacts -- this will ensure LR doesn't
     * handle the change events before Gulp has finished outputting them. */
    if (useLiveReload) {
        liveReload.listen();

        gulp.watch(paths.builtLiveReload, function(file) {
            var relativePath = path.relative('.', file.path);
            util.log(util.colors.magenta(relativePath) + ' live reloaded');

            liveReload.changed(file.path);
        });
    }
});
