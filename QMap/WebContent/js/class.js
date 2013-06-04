function Spot(id, pathid, name, latLng) {
	this.id = id;
	this.pathid = pathid;
	this.latLng = latLng;
	this.name = name;
	this.repeatId = 0;
	this.marker = new google.maps.Marker( {
		position : latLng,
		map : map,
		icon : "http://maps.google.com/mapfiles/marker" + ABC(id) + ".png"

	});
	this.marker.owner = this;

	google.maps.event.addListener(this.marker, "click", function() {
		this.owner.repeatId = repeatId;
		addRepeatSpot(this.getPosition());
	});

}

Spot.prototype.setMarkerVisible = function(a) {
	this.marker.setVisible(a);
};

Spot.prototype.setId = function(id) {
	this.id = id;
	this.marker.setIcon("http://maps.google.com/mapfiles/marker" + ABC(id)
			+ ".png");
};

function Path(pathid) {
	this.pathid = pathid;

	var polyOptions = {
		strokeColor : polyColors[this.pathid],
		strokeWeight : 2
	};
	this.polyline = new google.maps.Polyline(polyOptions);
	this.polyline.setMap(map);
	this.spots = [];
	this.polyArray = new google.maps.MVCArray();
	this.length = 0;

}

Path.prototype.get = function(id) {
	return this.spots[id];
	return this;
};

Path.prototype.add = function(spot) {
	this.spots.push(spot);
	this.polyArray.push(spot.latLng);
	this.polyline.setPath(this.polyArray);
	this.length++;
	return this;
};

Path.prototype.del = function(index) {
	this.spots[index].setMarkerVisible(false);
	arrayRemove(this.spots, index);
	this.polyArray.removeAt(index);
	this.polyline.setPath(this.polyArray);
	this.length--;
	return this;
};

Path.prototype.clear = function() {
	for (i = 0; i < this.spots.length; i++)
		this.spots[i].setMarkerVisible(false);
	this.polyline.setPath( []);
	this.length = 0;
};


