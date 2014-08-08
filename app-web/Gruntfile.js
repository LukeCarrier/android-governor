module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        concat: {
            'bootstrap-ie': {
                src: [
                    'bower_components/html5shiv/dist/html5shiv.min.js',
                    'bower_components/respond/dest/respond.min.js'
                ],
                dest: 'build/bootstrap-ie.js'
            }
        },

        less: {
            development: {
                options: {
                    paths: ['less']
                },

                files: {
                    "build/style.css": "less/style.less"
                }
            }
        },

        watch: {
            options: {
                livereload: true
            },

            less: {
                files: ['less/*.less'],
                tasks: ['less']
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default', ['concat', 'less']);
}
