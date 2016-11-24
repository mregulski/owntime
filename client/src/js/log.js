/**
 * Create a logger with a specified tag prefixed.
 */
app.getLog = function getLog(tag) {
    let log = {}
    tag = "[" + tag + "]";
    ['error', 'warn', 'info', 'log', 'debug', 'trace'].forEach((fn) => {
        log[fn[0]] = console[fn].bind(console, tag);
    });
    return log;
}

app.log = app.getLog("app");
let log = app.log;