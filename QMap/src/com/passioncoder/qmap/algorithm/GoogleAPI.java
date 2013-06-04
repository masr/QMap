package com.passioncoder.qmap.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleAPI {

	private static final String REQUEST_URL = "http://qmap-passioncoder.appspot.com/qmapgae";

	// private static final String REQUEST_URL =
	// "http://localhost:8888/qmapgae";

	/**
	 * Send request to the GAE Server.
	 * 
	 * @param points
	 *            The points that need to look for path through the Google Map
	 *            Service.
	 * @return The path.
	 */
	public static List<List<Path>> findPaths(List<Path> paths) {
		// Build the request parameters.
		JSONObject json = new JSONObject();
		try {
			json.put("paths", paths);
			try {
				// Send the request to the servlet on GAE.
				long currentTime = System.currentTimeMillis();
				URL request_url = new URL(REQUEST_URL);
				HttpURLConnection connection = (HttpURLConnection) request_url
						.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				OutputStream body = connection.getOutputStream();
				body.write(("json=" + json.toString()).getBytes());
				connection.connect();
				// Read the response.
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				System.out.println("Send Request Cost : "
						+ (System.currentTimeMillis() - currentTime));
				return parseJSON(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

//		 List<List<Path>> result = new LinkedList<List<Path>>();
//		 for (Path path : paths) {
//		 List<Path> innerPath = new LinkedList<Path>();
//		 List<Point> points = path.getPoints();
//		 for (int i = 0; i < points.size() - 1; i++) {
//		 Path temp = new Path();
//		 temp.addPoint(points.get(i));
//		 temp.addPoint(points.get(i + 1));
//		 innerPath.add(path);
//		 }
//		 result.add(innerPath);
//		 }
//		
//		 return result;
	}

	/**
	 * Construct the path list from the given json string.
	 * 
	 * @param json
	 *            The given json string.
	 * @return The path list.
	 */
	private static List<List<Path>> parseJSON(String json) {
		try {
			List<List<Path>> result = new LinkedList<List<Path>>();
			JSONObject root = new JSONObject(json);
			JSONArray pathes = root.getJSONArray("paths");
			for (int i = 0; i < pathes.length(); i++) {
				List<Path> innerPaths = new LinkedList<Path>();
				JSONArray subPathes = pathes.getJSONArray(i);
				for (int j = 0; j < subPathes.length(); j++) {
					JSONObject pathJSON = subPathes.getJSONObject(j);
					Path path = new Path();
					double distance = pathJSON.getDouble("distance");
					path.setDistance(distance);
					JSONArray pointsJSON = pathJSON.getJSONArray("points");
					for (int k = 0; k < pointsJSON.length(); k++) {
						JSONObject pointJSON = pointsJSON.getJSONObject(k);
						double latitude = pointJSON.getDouble("latitude");
						double longitude = pointJSON.getDouble("longitude");
						path.addPoint(new Point(latitude, longitude));
					}
					innerPaths.add(path);
				}
				result.add(innerPaths);
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
