var map;
var geocoder;
var paths = [];
var polylines = [];
var curPathId;
var repeatId = 1;
var polyColors = [ "#000000", "#00DB00", "#000079", "#642100", "#613030",
		"#336666", "#FF8000", "#F9F900", "#FF0000", "#B766AD" ];

$(function() {
	var mapOptions = {
		zoom : 10,
		center : new google.maps.LatLng(37.7749295, -122.4194155),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map($("#map_canvas").get(0), mapOptions);
	eventInit();
	geocoder = new google.maps.Geocoder();
	curPathId = 0;
	paths.push(new Path(0));
    $("#places_form").html("");
    $("#mark_new_path_button").hide();
	
});

function eventInit() {

	google.maps.event.addListener(map, "click", function(event) {
		$("#mark_new_path_button").show();
		var latLng = event.latLng;
		var index = paths[curPathId].length;
		var spot = new Spot(index, curPathId, index + 1 + "", latLng);
		paths[curPathId].add(spot);
		formAddItem(spot);
	});

	$("#reset_link").click(function() {
		if (confirm("Are you sure to reset?"))
		    reset();
		return false;
	});

	$("#place_search_submit_button").click(function() {
		var request = {
			address : $("#place_search_input").val()
		};
		geocoder.geocode(request, function(result, status) {
			map.setCenter(result[0].geometry.location);
		});
		return false;
	});

	$("#submit_button").click(
			function() {
				if (!checkSubmit())
					return false;

				var str = getOrganizedData();
				$("#simple_map img").hide().attr("src",
						"Subway?q=" + str).load(function() {
					window.scrollBy(0, 1100);
					$(this).show();

				});
				return false;
			});

	$("#mark_new_path_button").click(function() {
		curPathId++;
		paths[curPathId] = new Path(curPathId);
		return false;
	});

    $("#place_search_input").val("Please input place name")
	                        .focus(function(){
								$(this).val("");
							}).blur(function(){
								$(this).val("Please input place name");
							});
	
}

function reset() {

	
	for ( var i = 0; i < paths.length; i++)
		paths[i].clear();

	$("#places_form").html("");
	paths = [];
	curPathId = 0;
	paths.push(new Path(0));
}

function formAddItem(spot) {

	if (spot.id == 0)// A new path form should be rendered
	{
		$("#places_form").append("<div/>").find("div:last").addClass("path_form")
				.attr("id", "path_form_" + spot.pathid).append("<h2>Path" + (spot.pathid+1)+"</h2>");
				
	}

	$("#places_form #path_form_" + spot.pathid)
			.append(
					"<div class='place_item' id='place_item_"
					        +spot.pathid+"_"+ spot.id
							+ "'><label>"
							+ ABC(spot.id)
							+ ": </label><input type='text'>"
							+ "</input><a class='delete_link'  href='#'>Delete</a></div>")
		    .find(".place_item:last")
			.find("input")
			.val("Name"+spot.name)
			.andSelf()
			.find(".delete_link")
			.click(
					function() {
						spotIndex = parseInt($(this).parents(".place_item").get(0).id.split("_")[3]);
						pathIndex = $(this).parents(".place_item").get(0).id
								.split("_")[2];
						paths[pathIndex].del(spotIndex);
						refresh(pathIndex);
						return false;
					})
			.andSelf()
			.find("input")
			.blur(
					function() {
						var spotIndex = parseInt($(this).parents(".place_item").get(0).id
								.split("_")[3]);
						var pathIndex = parseInt($(this).parents(".place_item")
								.get(0).id.split("_")[2]);
						paths[pathIndex].get(spotIndex).name = $(this).val();
					});
}

function refresh(pathid) {

	$("#places_form").find("div#path_form_" + pathid).remove();

	for ( var j = 0; j < paths[pathid].length; j++) {
		paths[pathid].get(j).setId(j);
		formAddItem(paths[pathid].get(j));
	}
}

function getOrganizedData() {
	var str = "{paths:[";
	for ( var i = 0; i < paths.length; i++) {
		str+="{spots:[";
		for ( var j = 0; j < paths[i].length; j++) {
			str+="{";
			var spot = paths[i].get(j);
			str+="repeat_id:"+spot.repeatId+",";
			str+="name:"+spot.name+",";
			str+="lat:"+spot.latLng.lat()+",";
			str+="lng:"+spot.latLng.lng();
	        str+="},";
		}
		str+="]},";
	}
	return str+"]}";
}

function checkSubmit() {

	for(var i=0;i<paths.length;i++){
		if (paths[i].length<=1){
			alert("At least two spots should be marked");
			return false;
		}
	}

        for(var i=0;i<paths.length;i++)
        {
        	for(var j=0;j<paths[i].length;j++)
        	{
        		if (paths[i].get(j).name.length==0){
        			alert("Every spot should have a name!");
    			    return false;
        		}
        	}
        }


  return true;

}

function addRepeatSpot(latLng) {
	var index = paths[curPathId].length;
	var spot = new Spot(index, curPathId, index + 1 + "", latLng);
	spot.repeatId = repeatId;
	paths[curPathId].add(spot);
	formAddItem(spot);
	repeatId++;
}
