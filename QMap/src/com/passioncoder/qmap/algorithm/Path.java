package com.passioncoder.qmap.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class Path implements JSONString{

	private int pathID=-1;
	private double distance;
	private List<Point> points = new ArrayList<Point>();
	private double point1xposition;
	private double point1yposition;
	private double point2xposition;
	private double point2yposition;

	public Path() {
		
	}

	public Path(int pathID) {
		this.pathID = pathID;
		distance = 0.0;
	}

	public Point join(Path another) {
		List<Point> newPoints=new ArrayList<Point>();
		Point exPoints[][]={{this.getStartPoint(),this.getEndPoint()},{another.getStartPoint(),another.getEndPoint()}};
         for(int i=0;i<2;i++){
        	 for(int j=0;j<2;j++){
        		 if (exPoints[0][i] == (exPoints[1][j])){
        			 List<Point> tempAnother=(j!=0?reverse(another.getPoints()):another.getPoints());
        			 List<Point> tempPonits=(i==0?reverse(points):points);
        			 Point crossPoint=tempPonits.get(0);
        		     tempAnother.remove(0);
        		     newPoints.addAll(tempPonits);
        		     newPoints.addAll(tempAnother);
        		     points=newPoints;
        		     return crossPoint;
        		 }
        	 }
         }
         return null;
	}
	
	private List<Point> reverse(List<Point> points){
		List<Point> newPoints=new ArrayList<Point>();
		for(int i=points.size()-1;i>=0;i--)
			newPoints.add(points.get(i));
		
		return newPoints;
	}

	public void replacePoints(Path source) {
		points = source.getPoints();
	}

	public void setStartPoint(Point point) {
		points.set(0, point);
	}

	public void setEndPoint(Point point) {
		points.set(points.size() - 1, point);
	}

	public Point getStartPoint() {
		return points.get(0);
	}

	public Point getEndPoint() {
		return points.get(points.size() - 1);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getPathID() {
		return pathID;
	}

	public void setPathID(int pathID) {
		this.pathID = pathID;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public void setPoint1XPosition(double _xposition) {
		this.point1xposition = _xposition;
	}

	public void setPoint1YPosition(double _yposition) {
		this.point1yposition = _yposition;
	}

	public void setPoint2XPosition(double _xposition) {
		this.point2xposition = _xposition;
	}

	public void setPoint2YPosition(double _yposition) {
		this.point2yposition = _yposition;
	}

	public double getPoint1XPosition() {
		return this.point1xposition;
	}

	public double getPoint1YPosition() {
		return this.point1yposition;
	}

	public double getPoint2XPosition() {
		return this.point2xposition;
	}

	public double getPoint2YPosition() {
		return this.point2yposition;
	}

	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		try {
			json.put("points", points);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}

}