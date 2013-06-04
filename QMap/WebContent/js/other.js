function markPoints(str) {
	reset();
	var temp = str.split(",");
	for (i = 0; i < temp.length; i = i + 2) {
		lat = Number(temp[i]);
		lng = Number(temp[i + 1]);
		var myLatlng = new google.maps.LatLng(lat, lng);
		markers.push(new google.maps.Marker( {
			position : myLatlng,
			map : map,
			icon : "http://maps.google.com/mapfiles/marker"
					+ String.fromCharCode(markers.length + 65) + ".png"

		}));
	}
}

function showSimplePoint(str) {
	reset();
	var origin = null;
	var destination = null;
	var waypoints = [];

	temp = str.split(",");
	for (i = 0; i < temp.length; i = i + 2) {
		lat = Number(temp[i]);
		lng = Number(temp[i + 1]);
		if (origin == null) {
			origin = new google.maps.LatLng(lat, lng);
		} else if (destination == null) {
			destination = new google.maps.LatLng(lat, lng);
		} else {
			waypoints.push( {
				location : destination,
				stopover : true
			});
			destination = new google.maps.LatLng(lat, lng);
		}
	}

	directionsService = new google.maps.DirectionsService();
	directionsRenderer = new google.maps.DirectionsRenderer();
	directionsRenderer.setMap(map);
	directionsService.route( {
		origin : origin,
		waypoints : waypoints,
		destination : destination,
		travelMode : google.maps.DirectionsTravelMode.WALKING

	}, function(results, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsRenderer.setDirections(results);
		}

	});
}