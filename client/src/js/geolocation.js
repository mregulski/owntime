(function(app) {
    let log = app.getLog("geolocation");
    let geolocation = {}

    if ("geolocation" in navigator) {
        geolocation.getCurrentPosition = getCurrentPosition;
    } else {
        geolocation.getCurrentPosition = () => {};
    }

    app.geolocation = geolocation;

    /**
     * Return a Promise with user's current location
     */
    function getCurrentPosition() {
        return new Promise((resolve, reject) => {
                navigator.geolocation.getCurrentPosition(resolve, reject, {
                enableHighAccuracy: true,
                timeout: 5000,
                maximumAge: 36000
            });
        });
    }
})(app);