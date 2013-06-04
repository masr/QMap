package com.passioncoder.qmap.algorithm;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class Point implements JSONString {

	private String name;
	private double latitude;
	private double longitude;
	private PointType type;
	private double xposition;
	private double yposition;
	private int repeatId;
	private int connectionCounter = 0;

	public Point(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = PointType.KEY;
	}

	public Point(double latitude, double longitude) {
		this.name = null;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = PointType.NON_KEY;
	}

	public void moveBy(double delta_lat, double delta_lon) {
		this.latitude += delta_lat;
		this.longitude += delta_lon;
	}

	public PointType getType() {
		return type;
	}

	public void setType(PointType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getConnectionCounter() {
		return connectionCounter;
	}

	public void setConnectionCounter(int connectionCounter) {
		this.connectionCounter = connectionCounter;
	}
	
	public void increaseConnectionCounter() {
		connectionCounter++;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setPosition(double _xposition, double _yposition) {
		this.xposition = _xposition;
		this.yposition = _yposition;
	}

	public double getXPosition() {
		return this.xposition;
	}

	public double getYPosition() {
		return this.yposition;
	}

	public void setXPosition(double _xposition) {
		this.xposition = _xposition;
	}

	public void setYPosition(double _yposition) {
		this.yposition = _yposition;
	}

	public int getRepeatId() {
		return repeatId;
	}

	public void setRepeatId(int repeatId) {
		this.repeatId = repeatId;
	}

	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		try {
			json.put("latitude", latitude);
			json.put("longitude", longitude);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}

}
