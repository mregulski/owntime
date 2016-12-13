/*
to triggerowało funkcję w html'u

<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDLdSitwxmN1AJv4dIgaB1v9hAqkQjsXqw&callback=initMap">
    </script>
*/

function initMap() {
  var mapOptions = {
    zoom: 17,
    center: {lat: 51.10930159999999, lng:  17.0517307}
  	};
  	map = new google.maps.Map(document.getElementById('map'), mapOptions);
}