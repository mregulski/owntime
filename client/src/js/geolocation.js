(function(app) {
    let log = app.getLog("geolocation");
    let geolocation = {}

    if ("geolocation" in navigator) {
        geolocation.getCurrentPosition = getCurrentPosition;
    } else {
        geolocation.getCurrentPosition = () => {};
    }

    app.geolocation = geolocation;


    function getCurrentPosition(success) {
        navigator.geolocation.getCurrentPosition(success, handleError, {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 36000
        })
    }

    function handleError(error) {
        log.e(error);
    }

})(app);

// test
$(document).ready(() => {
    app.geolocation.getCurrentPosition((result) => {
        log.i(result);
        window.result = result;
        let latitude = result.coords.latitude;
        let longitude = result.coords.longitude;
        let map = "https://maps.googleapis.com/maps/api/staticmap?markers=" + latitude + "," + longitude + "&zoom=13&size=300x300&sensor=false"
        var img = $("<img>", {id: "map"})
            .attr("src", map);
        $("#location").append(img);
    });
});