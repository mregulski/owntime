/*
	input: 
	points: tablica dwuwymiarowa:
		pierwszy indeks: przystanek
		drugi indeks: informacje konieczne do wyświetlenia:
			0 - kordy w postaci {lat: x, lng: y}
			1 - adres do ikony markera
			2 - nazwa przystanku
			3 - kolor label'a w zależności od tego, czy to początek/środek/koniec trasy.
	map: obiekt mapy
*/
function makeRoute(points, map){
	var route = [];
	for (var i = 0; i < points.length; i++) {
		var point = points[i];
		route.push(point[0]);
		new google.maps.Marker({
			position: point[0],
			map: map,
			icon: point[1],
			label: {
				text: point[2],
				color: point[3]
			}
		});
	}

	var route1 = new google.maps.Polyline({
          path: route,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2
        }); 
    route1.setMap(map);

}