(function(app) {
    let log = app.getLog("initMap");
    let initMap = {}

     app.initMap = function () {
        var geoloc = app.geolocation.getCurrentPosition();
        coords = {};
        geoloc.then(function(result){
            coords.lat = result.coords.latitude;
            coords.lng = result.coords.longitude;
            var mapOptions = {
            zoom: 15,
            center: coords
            };
            map = new google.maps.Map(document.getElementById('map'), mapOptions);
        }, function(err){
            coords = {lat: 51.110909, lng: 17.056910};
            var mapOptions = {
            zoom: 17,
            center: coords
            };
            map = new google.maps.Map(document.getElementById('map'), mapOptions);
        });

    }
})(app);

function mapsCallback () {app.initMap();};
