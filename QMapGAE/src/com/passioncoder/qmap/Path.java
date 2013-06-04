package com.passioncoder.qmap;

import java.util.LinkedList;
import java.util.List;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONString;

public class Path implements JSONString {

	private double distance;
	private List<Point> points = new LinkedList<Point>();
	
	public Path() {
		this(0.0);
	}

	public Path(double distance) {
		this.distance = distance;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		try {
			json.put("points", points);
			json.put("distance", distance);
			return json.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
