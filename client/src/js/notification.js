(function(app) {
    let log = app.getLog("notification");

    let notification = {};

    notification.checkPermission = checkPermission;
    notification.setAlarm = setAlarm;
    notification.timeout = 10000

    app.notification = notification;
    log.d("Initialized.")



    /**
     * Check if app can use the Notifications API.
     * Attempt to obtain the permission if necessary.
     * @return: Promise; resolves to true if Notifications API is present and we've
     * been granted the permission to use it.
     */
    function checkPermission() {
        if(! ("Notification" in window)) {
            log.w("Notifications are not supported");
            return Promise.resolve(false);
        } else {
            if (Notification.permission !== "granted") {
                return Notification.requestPermission()
                    .then((result) => {
                        log.d("permission", result);
                        return result === "granted";
                });
            } else {
                return Promise.resolve(true);
            }
        }
    }

    /**
     * Display a delayed notification with route details
     * @param {moment} time - when to display the notification
     * @param routeDetails  - route details to display
     */
    function setAlarm(time, routeDetails) {
        checkPermission().then(res => {
            log.d("Notifications active:", res);
            if(!res) { return; }
            window.setTimeout(() => showNotification(routeDetails),
                time.diff(moment())
            );
        });
    }


    function showNotification(routeDetails) {
        let body = parseRoute(routeDetails);
        let n = new Notification("Musisz już wychodzić!", {body: body});
        setTimeout(n.close.bind(n), notification.timeout);
    }

    function parseRoute(routeDetails) {
        var msg = "linia " + routeDetails.line;
        msg +=  " w kierunku " + routeDetails.direction;
        msg += " odjeżdża z przystanku " + routeDetails.stop;
        msg += " za 12 minut";
        return msg;
    }
})(app);