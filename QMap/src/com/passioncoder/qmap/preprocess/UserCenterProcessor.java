package com.passioncoder.qmap.preprocess;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jgrapht.Graph;
import org.json.JSONArray;
import org.json.JSONObject;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.Utilities;
import com.passioncoder.qmap.drawing.Drawing;

public class UserCenterProcessor {

	private GraphProcessor dataProcessor;
	private int QUARANT_COUNT = 4;

	public UserCenterProcessor() {
		dataProcessor = new GraphProcessor();
	}

	public BufferedImage getImage(List<Path> paths) {
		GraphBuilder graphBuilder = new GraphBuilder();
		Graph<Point, Path> graph = graphBuilder.buildGoogleGraph(paths);
		Drawing.setMaxActualSize(0);
		return dataProcessor.processData(graph);
	}

	public List<Point> getPoints(String center, String range, String keyword) {
		String all = getPointsJSONString(center, range, keyword);
		try {
			List<Point> points = new ArrayList<Point>();
			JSONObject jsonObject = new JSONObject(all);
			JSONArray jsonArray = jsonObject.getJSONArray("places");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject place = jsonArray.getJSONObject(i);
				String name = place.getString("name");
				double lat = place.getDouble("latitude");
				double lng = place.getDouble("longitude");
				Point point = new Point(name, lat, lng);
				points.add(point);
			}
			return points;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getPointsJSONString(String center, String range,
			String keyword) {
		try {
			String url = "http://qmap-passioncoder.appspot.com/places?center="
					+ center + "&range=" + range + "&keyword=" + keyword;

			URL requestURL = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) requestURL
					.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuilder all = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				all.append(line);

			return all.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private int getQuadrant(Point spot, Point user) {
		Point xPoint = new Point(user.getLatitude(), user.getLongitude() + 1);
		double cos = Utilities.caculateAngleDegree(user, xPoint, spot);
		double angle = Math.acos(cos);
		angle = (spot.getLatitude() - user.getLatitude()) > 0 ? angle : 2
				* Math.PI - angle;
		int quadrantNum = (int) (angle / (Math.PI * 2 / QUARANT_COUNT));
		return quadrantNum;
	}

	public List<Path> getPaths(List<Point> allPoints, Point centerPoint) {

		if (allPoints.size() == 1)
			return new ArrayList<Path>();

		List<Path> paths = new ArrayList<Path>();
		for (int i = 0; i < QUARANT_COUNT; i++) {
			List<Point> points = getQuadrantPoints(i, allPoints, centerPoint);
			if (points.size() == 0)
				continue;
			Point nearestPoint = getNearestPoint(points, centerPoint);
			Path path = new Path(1);
			path.addPoint(centerPoint);
			path.addPoint(nearestPoint);
			paths.add(path);
			points.remove(nearestPoint);
			List<Path> tempPaths = getPathsHelper(points, nearestPoint);
			paths.addAll(tempPaths);

		}
		mergePath(paths);
		return paths;

	}

	private List<Path> getPathsHelper(List<Point> allPoints, Point centerPoint) {

		if (allPoints.size() == 1)
			return new ArrayList<Path>();

		List<Path> paths = new ArrayList<Path>();
		for (int i = 0; i < QUARANT_COUNT; i++) {
			List<Point> points = getQuadrantPoints(i, allPoints, centerPoint);
			if (points.size() == 0)
				continue;
			Point nearestPoint = getNearestPoint(points, centerPoint);
			Path path = new Path(1);
			path.addPoint(centerPoint);
			path.addPoint(nearestPoint);
			paths.add(path);
			points.remove(nearestPoint);
			List<Path> tempPaths = getPathsHelper(points, nearestPoint);
			paths.addAll(tempPaths);

		}
		return paths;

	}

	private List<Point> getQuadrantPoints(int quadrant, List<Point> allPoints,
			Point center) {
		List<Point> points = new ArrayList<Point>();
		for (Point point : allPoints) {
			if (getQuadrant(point, center) == quadrant)
				points.add(point);
		}
		return points;
	}

	private Point getNearestPoint(List<Point> allPoints, Point centerPoint) {
		Collections.sort(allPoints, new PointComparator(centerPoint));
		return allPoints.get(0);
	}

	private void mergePath(List<Path> paths) {
		for (int i = 0; i < paths.size(); i++) {
			Path curPath = paths.get(i);
			for (int j = 0; j < i; j++) {
				Path prePath = paths.get(j);
				Point crossPoint = prePath.join(curPath);
				if (crossPoint != null) {
					paths.remove(curPath);
					i--;
					break;
				}
			}
		}
	}

	private class PointComparator implements Comparator<Point> {

		private Point userPoint;

		public PointComparator(Point userPoint) {
			this.userPoint = userPoint;
		}

		@Override
		public int compare(Point o1, Point o2) {
			return Utilities.pointToPointDistance(o1, userPoint) > Utilities
					.pointToPointDistance(o2, userPoint) ? 1 : 0;
		}

	}
}
