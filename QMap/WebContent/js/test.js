function simulateClickGoogleMap(lat,lng){
	var latLng=new google.maps.LatLng(lat,lng);
	var index = paths[curPathId].length;
	var spot = new Spot(index, curPathId, index + 1 + "", latLng);
	paths[curPathId].add(spot);
	formAddItem(spot);
}

function testSuite(){
	

		window.confirm=function(){
			return false;
		};
		window.alert=function(){};
	

	
	
module("path form act");
test("We mark three marks and add three place item in the form", function() {
	
	simulateClickGoogleMap(32.7849295, 118.4194155);
	simulateClickGoogleMap(32.7949295, 118.4194155);
	simulateClickGoogleMap(32.8049295, 118.4194155);

	equals(paths.length,1,"1 path")
    equals( paths[0].length,3, "3 spots in path 0" );
    equals($("#places_form #path_form_0").length,1,"#path_form_0 exist");
    equals( $("#places_form .place_item").length,3,"3 .place_item");
    equals($("#places_form input").length,3,"3 input");
    equals($("#places_form .delete_link").length,3,"3 .delete_link");
    
});


test("Mark another path and add another three points", function() {
	$("#mark_new_path_button").click();
	simulateClickGoogleMap(32.7849295, 119.4194155);
	simulateClickGoogleMap(32.7949295, 119.4194155);
	simulateClickGoogleMap(32.8049295, 119.4194155);

    equals( paths.length,2, "There should be 2 paths now!" );
    equals($("#places_form #path_form_1").length,1,"#path_form_1 exist");
    equals( $("#path_form_1 .place_item").length,3,"#path_form_1 has 3 .place_item");
    equals($("#path_form_1 input").length,3,"#path_form_1 has 3 input");
    equals($("#path_form_1 .delete_link").length,3,"#path_form_1 has 3 .delete_link");
});

test("Delete the 1st point of second path", function() {
	
	var spot=paths[1].get(1);
	$("#path_form_1 .delete_link:first").click();
	equals(paths[1].length,2,"There should be 2 spots in second path");
	equals(paths[1].get(0).latLng,spot.latLng,"object equals,original 1 0, current 1 0");
	equals($("#path_form_1 .place_item").length,2,"#path_form_1 has 2 .place_item now");
    equals(paths[1].get(1).name,"3","paths[1][1] should remain its name '3'");
    equals(getOrganizedData(),"{paths:[{spots:[{repeat_id:0,name:1,lat:32.7849295,lng:118.4194155},{repeat_id:0,name:2,lat:32.7949295,lng:118.4194155},{repeat_id:0,name:3,lat:32.8049295,lng:118.4194155},]},{spots:[{repeat_id:0,name:2,lat:32.7949295,lng:119.4194155},{repeat_id:0,name:3,lat:32.8049295,lng:119.4194155},]},]}","test organized data without repeat point");

});



test("Mark the 3rd point repeating spot 0 0",function(){
	var spot=paths[0].get(0);
	spot.repeatId = repeatId;
	addRepeatSpot(spot.latLng);
	equals($(".place_item").length,6,"There should be 6 points now,including the repeated one");
	equals(paths[0].get(0).repeatId,1,"spot 0 0 repeatid is 1");
	equals(paths[1].get(2).repeatId,1,"spot 1 2 repeatid is 1");
	equals(paths[1].get(2).name,"3","spot 1 2 name is 3");
});



test("Change name of spot 1 1",function(){
	equals(paths[1].get(1).name,"3","spot 1 1 origin name is '3'");
	$("#place_item_1_1 input").val("nju");
	$("#place_item_1_1 input").blur();
	equals($("#place_item_1_1 input").val(),"nju"),"#place_item_1_1 input current value is 'nju'";
	equals(paths[1].get(1).name,"nju","spot 1 1 current name is 'nju'");
});



test("Submit data", function() {
	$("#submit_button").click();
	equals(checkSubmit(),true,"check right");
	var imgSrc=$("#simple_map img").attr("src");
	equals(imgSrc.indexOf("Subway"),0,"imgsrc  contains Subway pre");
	equals(getOrganizedData(),"{paths:[{spots:[{repeat_id:1,name:1,lat:32.7849295,lng:118.4194155},{repeat_id:0,name:2,lat:32.7949295,lng:118.4194155},{repeat_id:0,name:3,lat:32.8049295,lng:118.4194155},]},{spots:[{repeat_id:0,name:2,lat:32.7949295,lng:119.4194155},{repeat_id:0,name:nju,lat:32.8049295,lng:119.4194155},{repeat_id:1,name:3,lat:32.7849295,lng:118.4194155},]},]}","Test organized data within repeat point");

});



test("Reset all the places",function(){

	$("#reset_link").click();
	equals($(".place_item").length,6,"There should be 5 places remain because we should confirm first")
	$("#reset_link").click(reset);
	$("#reset_link").click();
	equals($(".place_item").length,0,"All palce item should be removed");
});

test("Add one point to separate path and check submit",function(){
	simulateClickGoogleMap(32.7849295, 118.4194155);
	equals(checkSubmit(),false,"fail the check because only one spot exist");
	$("#mark_new_path_button").click();
	simulateClickGoogleMap(33.7849295, 118.4194155);
	equals(checkSubmit(),false,"fail the check because only one spot in separate path");
});


module("place search");

$("#place_search_input").val("nanjing");
$("#place_search_submit_button").click();
setTimeout(function(){
	test("Relocate Map Center to Nanjing", function() {
		var newLatLng=map.getCenter();
		var lat=newLatLng.lat();
		var lng=newLatLng.lng();
		equals(lat>31.677&&lat<32.424&lng>118.210&lng<119.314,true,"Relocate map centers to Nanjing");
		});
},1500);

}

$(function(){

$("body").append("<h1 id='qunit-header'>QUnit example</h1<h2 id='qunit-banner'></h2><h2 id='qunit-userAgent'></h2><ol id='qunit-tests'></ol>");
$("head").append("<link rel='stylesheet' href='css/qunit.css'  media='screen' />")
.append("<script type='text/javascript' src='js/qunit.js'></script>")
	
testSuite();


});