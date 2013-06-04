var marker
var map;
var _iterval1;
var i=0;
var j=0;
$(function(){
	var mapOptions = {
			zoom : 10,
			center : new google.maps.LatLng(37.7749295, -122.4194155),
			mapTypeId : google.maps.MapTypeId.ROADMAP
		}
		map = new google.maps.Map($("#map_canvas").get(0), mapOptions);
	marker=new google.maps.Marker( {
		position : new google.maps.LatLng(32.01442 ,118.71302),
		map : map,
		icon : "http://maps.google.com/mapfiles/markerA.png"

	});
	
	    showRoute();
})
	



function showRoute(str) {
	
    var ori1=new google.maps.LatLng(32.01442 ,118.71302)
    var dest1=new google.maps.LatLng(32.0091  ,118.7965)

    var ori2=new google.maps.LatLng(32.0237  ,118.7562)
    var dest2=new google.maps.LatLng(31.977  ,118.7545)
    

    

	directionsService = new google.maps.DirectionsService();
	directionsRenderer1 = new google.maps.DirectionsRenderer();
	directionsRenderer2 = new google.maps.DirectionsRenderer();
	directionsRenderer1.setMap(map);
	directionsRenderer2.setMap(map);
	directionsService.route( {
		origin : ori1,
		destination : dest1,
		travelMode : google.maps.DirectionsTravelMode.WALKING

	}, function(results, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsRenderer1.setDirections(results);
		}

	});
	directionsService.route( {
		origin : ori2,
		destination : dest2,
		travelMode : google.maps.DirectionsTravelMode.WALKING

	}, function(results, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsRenderer2.setDirections(results);
			drawMove(results);
		}

	});

}





function drawMove(results){
	
	var steps=results.routes[0].legs[0].steps
      step=steps[i];
	  var ly=step.start_location.b-step.end_location.b
	  var lx=step.start_location.c-step.end_location.c
	  
		  var x=step.start_location.c-lx/10*j
		  var y=step.start_location.b-ly/10*j
		  drawMarker(y,x)
	 j++;
	 if (j==10)
	 { j=0;
	   i++;
	 }
		 
		  if (i==steps.length)
			  return;
		  
	setTimeout(function(){
		drawMove(results);
	},500)
}

function drawMarker(y,x){
	marker.setPosition(new google.maps.LatLng(y ,x));
}















