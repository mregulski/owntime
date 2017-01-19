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
    }
	/*
		drawing a route
		input:
			points: tablica obiektów-przystanków:
				pojedynczy obiekt musi zawierać:
				position - kordy w postaci {lat: x, lng: y}
				text - nazwa przystanku
				color - kolor label'a w zależności od tego, czy to początek/środek/koniec trasy.
	*/
	app.makeRoute = function(points){
		var icon = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
    	var markers = [];
		var route = [];
		var markers = [];
		for (var i = 0; i < points.length; i++) {
		var point = points[i];
		route.push(point.position);
		markers.push(new google.maps.Marker({
			position: point.position,
			map: app.map,
			icon: icon,
			label: {
				text: point.text,
				fontfamily: "Arial",
				fontWeight: "bold",
				fontSize: "15px",
				color: point.color
				}
			}));
		}
		app.actualMarkers = markers;
				//console.log(app.actualMarkers.length);
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

	app.clearBuses = function(){
		for (var i = 0; i < app.actualBuses.length; i++){
			app.actualBuses[i].setMap(null);
		}
    }

    app.makeBuses = function(points){
    	var icon = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
    	var markers = [];
		for (var i = 0; i < points.length; i++) {
		var point = points[i];
		markers.push(new google.maps.Marker({
			position: point.position,
			map: app.map,
			icon: icon,
			}));
		}
		app.actualBuses = markers;
    }

    app.showRoute = function(json){
		json.result = json;
    	var route = [];
    	var cords = json.result.start.coords.split(", ");
    	route.push({position: { lat: parseFloat(cords[0]), lng: parseFloat(cords[1]) }, text: json.result.start.displayName, color: "Blue" });

    	var limit = json.result.route.length;
    	for (var i = 0; i < limit; i++) {
    		var stopsl = json.result.route[i].stops.length;
    		for (var j = 0; j < stopsl; j++) {
    			var stop = json.result.route[i].stops[j];
    			cords = stop.coords.split(", ");
    			route.push({position: { lat: parseFloat(cords[0]), lng: parseFloat(cords[1]) }, text: stop.displayName, color: j!=0?"Orange":"RED" });
    		}
    	}

    	cords = json.result.end.coords.split(", ");
    	route.push({position: { lat: parseFloat(cords[0]), lng: parseFloat(cords[1]) }, text: json.result.end.displayName, color: "Green" });

    	app.makeRoute(route);
    }

    app.showBuses = function(json){
    	//TODO: IMPLEMENT (MISSING JSON SPECIFICATION)
    }


})(app);

function mapsCallback () {app.initMap();};

/*
	do wyświetlenia dummyRoute :)
*/
function testr(){
		var tests = [];
        //var test0 = [, image , "Dom", "Blue"];
		var test0 = {position: {lat: 51.1012573, lng:  17.10914151}, text: "BISKUPIN", color: "Blue"};
		var test1 = {position: {lat: 51.10220572, lng:  17.10232232}, text: "Spółdzielcza", color: "RED"};
		var test2 = {position: {lat: 51.1031808, lng:  17.09632744}, text: "Piramowicza", color: "RED"};
		var test3 = {position: {lat: 51.10388356, lng:  17.09071195},  text: "Chełmońkiego", color: "RED"};
		var test4 = {position: {lat: 51.10446682, lng:  17.08466996},  text: "Tramwajowa", color: "RED"};
		var test5 = {position: {lat: 51.10556081, lng:  17.0778982},  text: "ZOO", color: "RED"};
		var test6 = {position: {lat: 51.10708825, lng:  17.07346452},  text: "Hala Stulecia", color: "RED"};
		var test7 = {position: {lat: 51.1094524, lng:  17.06612151},  text: "Kliniki", color: "RED"};
		var test8 = {position: {lat: 51.1111569, lng:  17.06146483},  text: "PL. GRUNWALDZKI", color: "RED"};
		var test9 = {position: {lat: 51.11536582, lng:  17.06087806},  text: "Piastowska", color: "RED"};
		var test10 = {position: {lat: 51.11947641, lng:  17.05740455},  text: "Prusa", color: "RED"};
		var test11 = {position: {lat: 51.12191852, lng:  17.05294165},  text: "Wyszyńkiego", color: "RED"};
		var test12 = {position: {lat: 51.12423914, lng:  17.0448911},  text: "Nowowiejska", color: "RED"};
		var test13 = {position: {lat: 51.12354191, lng:  17.04021723},  text: "Słowiańka", color: "RED"};
		var test14 = {position: {lat: 51.12474918, lng:  17.03497559},  text: "DWORZEC NADODRZE", color: "RED"};
		var test15 = {position: {lat: 51.12431442, lng:  17.03503321},  text: "DWORZEC NADODRZE", color: "RED"};
		var test16 = {position: {lat: 51.12813406, lng:  17.03666443},  text: "Trzebnicka", color: "RED"};
		var test17 = {position: {lat: 51.13539403, lng:  17.03647045},  text: "Broniewskiego", color: "RED"};
		var test18 = {position: {lat: 51.14097662, lng:  17.03222223},  text: "Kamieńskiego", color: "RED"};
		var test19 = {position: {lat: 51.14421883, lng:  17.031233},  text: "Kępińska", color: "RED"};
		var test20 = {position: {lat: 51.14736913, lng:  17.03027131},  text: "Wołowska", color: "RED"};
		var test21 = {position: {lat: 51.15020822, lng:  17.02870501},  text: "POŚWTNE", color: "Green"};

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

function testb(){
	var tests = []

	var test0 = {position: {lat: 51.1012573, lng:  17.10914151}};
		var test1 = {position: {lat: 51.10220572, lng:  17.10232232}};
		var test2 = {position: {lat: 51.1031808, lng:  17.09632744}};
		var test3 = {position: {lat: 51.10388356, lng:  17.09071195}};
		var test4 = {position: {lat: 51.10446682, lng:  17.08466996}};
		var test5 = {position: {lat: 51.10556081, lng:  17.0778982}};

		tests.push(test0);
		tests.push(test1);
		tests.push(test2);
		tests.push(test3);
		tests.push(test4);
		tests.push(test5);

		app.makeBuses(tests);
}

function testjson(){
var jsons = '{"result": 	{"start": {	    "displayName": "Pl. Grunwaldzki",	    "coords": "51.111562, 17.060132",	    "id": "1","transports": []		},	"end":	{	    "displayName": "Pl. jakiśtam",	    "coords": "51.111562, 17.060132",	    "id": "1",	    "transports": []		},	"departure": 		{"planned": "2016-11-17T17:08:36.136Z",		"predicted": "2016-11-17T17:08:36.136Z"},	"arrival": 		{"planned": "2016-11-17T17:08:36.136Z","predicted": "2016-11-17T17:08:36.136Z"},	"route": [				{				    "stops": [				    			{								    "displayName": "Pl. Grunwaldzki1",								    "coords": "51.111562, 17.060140",								    "id": "1",								    "transports": []								},								{								    "displayName": "Pl. Grunwaldzki2",								    "coords": "51.111562, 17.060150",								    "id": "1",								    "transports": []								},								{								    "displayName": "Pl. Grunwaldzki3",								    "coords": "51.111562, 17.060160",								    "id": "1",								    "transports": []								}				    		],				    "transport": {"type" : "autobus",	"details": {"line": "336"} },				    "departure": {"planned": "2016-11-17T17:08:36.136Z",						"predicted": "2016-11-17T17:08:36.136Z"},				    "arrival": {"planned": "2016-11-17T17:08:36.136Z",						"predicted": "2016-11-17T17:08:36.136Z"},				    "alternatives": [],				    "details": {}				},				{				    "stops": [				    			{								    "displayName": "Pl. trtrtrtr",								    "coords": "51.111570, 17.060160",								    "id": "1",								    "transports": []								},								{								    "displayName": "Pl. trtrotrtr",								    "coords": "51.111580, 17.060160",								    "id": "1",								    "transports": []								}				    		],				    "transport": {"type" : "autobus",	"details": {"line": "236"} },				    "departure": {"planned": "2016-11-17T17:08:36.136Z",						"predicted": "2016-11-17T17:08:36.136Z"},				    "arrival": {"planned": "2016-11-17T17:08:36.136Z",						"predicted": "2016-11-17T17:08:36.136Z"},				    "alternatives": [],				    "details": {}				},				{				    "stops": [				    			{								    "displayName": "Pl. Grunwaldzki",								    "coords": "51.111590, 17.060170",								    "id": "1",								    "transports": []								}				    		],				    "transport": {"type" : "autobus",	"details": {"line": "136"} },				    "departure": {"planned": "2016-11-17T17:08:36.136Z",						"predicted": "2016-11-17T17:08:36.136Z"},				    "arrival": {"planned": "2016-11-17T17:08:36.136Z",						"predicted": "2016-11-17T17:08:36.136Z"},				    "alternatives": [],				    "details": {}				}			]}}';
var jsono = JSON.parse(jsons);
	app.showRoute(jsono);
}


