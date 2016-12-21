/**
 * Create a logger with a specified tag prefixed.
 */
(function(app) {
    app.getLog = function getLog(tag) {
        // if (tag) {
        //     tag = "[yak-" + tag + "]";
        // } else {
        //     tag = "[yak]";
        // }
        tag = tag ? "[yak-" + tag + "]" : "[yak]"
        let log = console.log.bind(console, tag);
        ["error", "warn", "info", "log", "debug", "trace"].forEach((fn) => {
            log[fn[0]] = console[fn].bind(console, tag);
        });
        return log;
    }
    app.log = app.getLog("yak");

    window.log = app.log;
})(app);