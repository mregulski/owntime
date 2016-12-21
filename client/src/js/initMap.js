(function(app) {
    let log = app.getLog("initMap");

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
            app.map = new google.maps.Map(document.getElementById('map'), mapOptions);
        }, function(err){
            coords = {lat: 51.110909, lng: 17.056910};
            var mapOptions = {
            zoom: 20,
            center: coords
            };
            app.map = new google.maps.Map(document.getElementById('map'), mapOptions);
        });
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

    }
})(app);

function mapsCallback () {app.initMap();};

/*
	do wyświetlenia dummyRoute :)
*/
function testr(){
	var image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
        var tests = []
        //var test0 = [, image , "Dom", "Blue"];
		var test0 = {position: {lat: 51.1012573, lng:  17.10914151}, icon: image, text: "BISKUPIN", color: "Blue"};
		var test1 = {position: {lat: 51.10220572, lng:  17.10232232}, icon: image, text: "Spółdzielcza", color: "RED"};
		var test2 = {position: {lat: 51.1031808, lng:  17.09632744}, icon: image, text: "Piramowicza", color: "RED"};
		var test3 = {position: {lat: 51.10388356, lng:  17.09071195}, icon: image, text: "Chełmońkiego", color: "RED"};
		var test4 = {position: {lat: 51.10446682, lng:  17.08466996}, icon: image, text: "Tramwajowa", color: "RED"};
		var test5 = {position: {lat: 51.10556081, lng:  17.0778982}, icon: image, text: "ZOO", color: "RED"};
		var test6 = {position: {lat: 51.10708825, lng:  17.07346452}, icon: image, text: "Hala Stulecia", color: "RED"};
		var test7 = {position: {lat: 51.1094524, lng:  17.06612151}, icon: image, text: "Kliniki", color: "RED"};
		var test8 = {position: {lat: 51.1111569, lng:  17.06146483}, icon: image, text: "PL. GRUNWALDZKI", color: "RED"};
		var test9 = {position: {lat: 51.11536582, lng:  17.06087806}, icon: image, text: "Piastowska", color: "RED"};
		var test10 = {position: {lat: 51.11947641, lng:  17.05740455}, icon: image, text: "Prusa", color: "RED"};
		var test11 = {position: {lat: 51.12191852, lng:  17.05294165}, icon: image, text: "Wyszyńkiego", color: "RED"};
		var test12 = {position: {lat: 51.12423914, lng:  17.0448911}, icon: image, text: "Nowowiejska", color: "RED"};
		var test13 = {position: {lat: 51.12354191, lng:  17.04021723}, icon: image, text: "Słowiańka", color: "RED"};
		var test14 = {position: {lat: 51.12474918, lng:  17.03497559}, icon: image, text: "DWORZEC NADODRZE", color: "RED"};
		var test15 = {position: {lat: 51.12431442, lng:  17.03503321}, icon: image, text: "DWORZEC NADODRZE", color: "RED"};
		var test16 = {position: {lat: 51.12813406, lng:  17.03666443}, icon: image, text: "Trzebnicka", color: "RED"};
		var test17 = {position: {lat: 51.13539403, lng:  17.03647045}, icon: image, text: "Broniewskiego", color: "RED"};
		var test18 = {position: {lat: 51.14097662, lng:  17.03222223}, icon: image, text: "Kamieńskiego", color: "RED"};
		var test19 = {position: {lat: 51.14421883, lng:  17.031233}, icon: image, text: "Kępińska", color: "RED"};
		var test20 = {position: {lat: 51.14736913, lng:  17.03027131}, icon: image, text: "Wołowska", color: "RED"};
		var test21 = {position: {lat: 51.15020822, lng:  17.02870501}, icon: image, text: "POŚWTNE", color: "Green"};

		tests.push(test0);
		tests.push(test1);
		tests.push(test2);
		tests.push(test3);
		tests.push(test4);
		tests.push(test5);
		tests.push(test6);
		tests.push(test7);
		tests.push(test8);
		tests.push(test9);
		tests.push(test10);
		tests.push(test11);
		tests.push(test12);
		tests.push(test13);
		tests.push(test14);
		tests.push(test15);
		tests.push(test16);
		tests.push(test17);
		tests.push(test18);
		tests.push(test19);
		tests.push(test20);
		tests.push(test21);

        app.makeRoute(tests);
}
