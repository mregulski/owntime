/*
to triggerowało funkcję w html'u

<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBSrkPCHS0QJt0aVyG_2rNDEmLJ2dZ_KnU&callback=initMap">
    </script>
*/
(function(app) {
    let log = app.getLog("initMap");
    let initMap = {}

     app.initMap = function initialMap() {
        var geoloc = app.geolocation.getCurrentPosition();
        var mapOptions = {
            zoom: 17,
            center: /*geoloc*/ {lat: 51.10930159999999, lng:  17.0517307}
        };
        map = new google.maps.Map(document.getElementById('map'), mapOptions);
    }
})(app);

function mapsCallback () {console.log("mapsCallback");app.initMap();};
