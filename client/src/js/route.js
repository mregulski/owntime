(function(app){
	let log = app.getLog("makeRoute");

	/*
		drawing a route
		input: 
			points: tablica obiektów-przystanków:
				pojedynczy obiekt musi zawierać:
				position - kordy w postaci {lat: x, lng: y}
				icon - adres do ikony markera
				text - nazwa przystanku
				color - kolor label'a w zależności od tego, czy to początek/środek/koniec trasy.
	*/
	app.makeRoute = function(points){
		var route = [];
		var markers = [];
		for (var i = 0; i < points.length; i++) {
		var point = points[i];
		route.push(point.position);
		markers.push(new google.maps.Marker({
			position: point.position,
			map: app.map,
			icon: point.icon,
			label: {
				text: point.text,
				color: point.color
				}
			}));
		}
		app.actualMarkers = markers;
				console.log(app.actualMarkers.length);
		app.actualRoute = new google.maps.Polyline({
          	path: route,
          	geodesic: true,
          	strokeColor: '#FF0000',
          	strokeOpacity: 1.0,
          	strokeWeight: 2
        	}); 
    	app.actualRoute.setMap(app.map);
	}

	/*
		clearing already drawn route
	*/
	app.clearRoute = function(){
		for (var i = 0; i < app.actualMarkers.length; i++) {
			app.actualMarkers[i].setMap(null);
		}
		app.actualRoute.setMap(null);
	}

})(app);


/*
	do wyświetlenia dummyRoute :)
*/
function testr(){
	var image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
        var tests = []
        //var test0 = [, image , "Dom", "Blue"];
        var test0 = {position: {lat: 51.1160789197085, lng:  17.0525259197085}, icon: image, text: "Dom", color: "Blue"};
        //var test1 = [{lat: 51.10930159999999, lng:  17.0517307}, image , "Most Grunwaldzki", "Red"];
        var test1 = {position: {lat: 51.10930159999999, lng:  17.0517307}, icon: image, text: "Most Grunwaldzki", color: "Red"};
        //var test2 = [{lat: 51.1084387, lng:  17.0405239}, image , "Galeria Dominikańska", "Red"];
        var test2 = {position: {lat: 51.1084387, lng:  17.0405239}, icon: image, text: "Galeria Dominikańska", color: "Red"};
        //var test3 = [{lat: 51.0996462, lng:  17.0372635}, image , "Dworzec Główny PKP", "Green"];
        var test3 = {position: {lat: 51.0996462, lng:  17.0372635}, icon: image, text: "Dworzec Główny PKP", color: "Green"};
        tests.push(test0);
        tests.push(test1);
        tests.push(test2);
        tests.push(test3);
        app.makeRoute(tests);
}
