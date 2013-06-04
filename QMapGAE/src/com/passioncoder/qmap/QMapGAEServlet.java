package com.passioncoder.qmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class QMapGAEServlet extends HttpServlet {

	private static final String DIRECTION_URL = "http://maps.google.com/maps/api/directions/json";

	private static final int MAX_WAYPOINTS = 9;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGetAndPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGetAndPost(req, resp);
		

	}

	private void doGetAndPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String param = req.getParameter("json");
		List<Path> pathes = parseParamter(param);
		String result = findPathes(pathes);
		resp.setContentType("application/json");
		resp.getWriter().println(result == null ? "" : result);
	}

	private List<Path> parseParamter(String param) {
		List<Path> result = new LinkedList<Path>();
		try {
			JSONObject root = new JSONObject(param);
			JSONArray pathes = root.getJSONArray("paths");
			for (int i = 0; i < pathes.length(); i++) {
				JSONArray points = pathes.getJSONObject(i).getJSONArray(
						"points");
				Path path = new Path();
				for (int j = 0; j < points.length(); j++) {
					JSONObject point = points.getJSONObject(j);
					double latitude = point.getDouble("latitude");
					double longitude = point.getDouble("longitude");
					path.addPoint(new Point(latitude, longitude));
				}
				result.add(path);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private String findPathes(List<Path> pathes) {
		List<List<Path>> result = new LinkedList<List<Path>>();
		for (Path path : pathes) {
			List<Path> innerPathes = findPathAll(path);
			result.add(innerPathes);
		}
		JSONObject json = new JSONObject();
		try {
			json.put("paths", result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}

	private String buildURL(String baseURL, Map<String, String> args) {
		StringBuilder sb = new StringBuilder(baseURL + "?");
		for (Map.Entry<String, String> entry : args.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			try {
				sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sb.append("&");
		}
		return sb.toString();
	}

	private List<Path> findPathAll(Path originalPath) {
		List<Point> points = originalPath.getPoints();
		int size = points.size();
		List<Path> result = new ArrayList<Path>(size - 1);
		int counter = 0;
		while (counter < size) {
			Point startPoint = points.get(counter);
			counter++;
			StringBuilder wayPoints = new StringBuilder();
			for (int i = 0; i < MAX_WAYPOINTS && counter < size - 1; i++, counter++) {
				Point wayPoint = points.get(counter);
				wayPoints.append(wayPoint.getLatitude());
				wayPoints.append(",");
				wayPoints.append(wayPoint.getLongitude());
				wayPoints.append("|");
			}
			Point endPoint = points.get(counter);
			if (counter == size - 1) {
				counter++;
			}
			String origin = startPoint.getLatitude() + ","
					+ startPoint.getLongitude();
			String destination = endPoint.getLatitude() + ","
					+ endPoint.getLongitude();
			Map<String, String> paramters = new HashMap<String, String>();
			paramters.put("origin", origin);
			paramters.put("destination", destination);
			paramters.put("waypoints", wayPoints.toString());
			paramters.put("sensor", "false");
			paramters.put("mode", "walking");
			try {
				URL requestURL = new URL(buildURL(DIRECTION_URL, paramters));
				HttpURLConnection connection = (HttpURLConnection) requestURL
						.openConnection();
				connection.connect();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				StringBuilder all = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					all.append(line);
				}
				JSONObject root = new JSONObject(all.toString());
				JSONObject routes = (root.getJSONArray("routes"))
						.getJSONObject(0);
				JSONArray legs = routes.getJSONArray("legs");
				for (int i = 0; i < legs.length(); i++) {
					JSONObject leg = legs.getJSONObject(i);
					JSONArray steps = leg.getJSONArray("steps");
					JSONObject distance = leg.getJSONObject("distance");
					Path path = new Path();
					path.setDistance(Double.parseDouble(distance.getString(
							"text").split(" ")[0].replaceAll(",", "")
							.replaceAll(" ", "")));
					path.addPoint(startPoint);
					for (int j = 1; j < steps.length(); j++) {
						JSONObject place = steps.getJSONObject(j);
						JSONObject ll = place.getJSONObject("start_location");
						String lat = ll.getString("lat");
						String lng = ll.getString("lng");
						path.addPoint(new Point(Double.parseDouble(lat), Double
								.parseDouble(lng)));
					}
					path.addPoint(endPoint);
					result.add(path);
				}
			} catch (Exception e) {
				int startIndex = points.indexOf(startPoint);
				int endIndex = points.indexOf(endPoint);
				for(int i = startIndex; i < endIndex; i++) {
					Path path = new Path();
					path.addPoint(points.get(i));
					path.addPoint(points.get(i + 1));
					result.add(path);
				}
			}
		}
		return result;
	}

	private Path findPath(Point startPoint, Point endPoint) {
		String origin = startPoint.getLatitude() + ","
				+ startPoint.getLongitude();
		String destination = endPoint.getLatitude() + ","
				+ endPoint.getLongitude();
		Map<String, String> args = new HashMap<String, String>();
		args.put("origin", origin);
		args.put("destination", destination);
		args.put("sensor", "false");
		args.put("mode", "walking");
		try {
			URL requestURL = new URL(buildURL(DIRECTION_URL, args));
			HttpURLConnection connection = (HttpURLConnection) requestURL
					.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuilder all = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				all.append(line);
			}
			JSONObject root = new JSONObject(all.toString());
			JSONObject routes = (root.getJSONArray("routes")).getJSONObject(0);
			JSONObject legs = routes.getJSONArray("legs").getJSONObject(0);
			JSONArray steps = legs.getJSONArray("steps");
			JSONObject distance = legs.getJSONObject("distance");
			Path path = new Path();
			path.setDistance(Double.parseDouble(distance.getString("text")
					.split(" ")[0].replaceAll(",", "").replaceAll(" ", "")));
			path.addPoint(startPoint);
			for (int i = 1; i < steps.length(); i++) {
				JSONObject place = steps.getJSONObject(i);
				JSONObject ll = place.getJSONObject("start_location");
				String lat = ll.getString("lat");
				String lng = ll.getString("lng");
				path.addPoint(new Point(Double.parseDouble(lat), Double
						.parseDouble(lng)));
			}
			path.addPoint(endPoint);
			return path;
		} catch (Exception e) {
			Path path = new Path();
			path.addPoint(startPoint);
			path.addPoint(endPoint);
			return path;
		}
	}
}
