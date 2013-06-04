package com.passioncoder.qmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class PlacesGAEServlet extends HttpServlet {

	private static String REQUEST_URL = "http://ajax.googleapis.com/ajax/services/search/local";

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

		String keyword = req.getParameter("keyword");
		String center = req.getParameter("center");
		String range = req.getParameter("range");

		List<Place> places = getPlaces(keyword, center, range);
		JSONObject json = new JSONObject();
		try {
			json.put("places", places);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString=json.toString();
		resp.setContentType("text/json");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = resp.getWriter();
		writer.println(jsonString);
		writer.close();
	}

	private List<Place> getPlaces(String keyword, String center, String range) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("q", keyword);
		parameters.put("sll", center);
		parameters.put("sspn", range);
		parameters.put("v", "1.0");
		parameters.put("rsz", "8");
		List<Place> resultPlaces = new LinkedList<Place>();
		for (int i = 0; i < 4; i++) {
			parameters.put("start", Integer.toString(i * 8));
			try {
				URL url = new URL(buildURL(REQUEST_URL, parameters));
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.connect();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream(),"UTF-8"));
				StringBuilder all = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					all.append(line);
				}
				 String allString=URLDecoder.decode(all.toString(),"UTF-8");
				buildPlaces(resultPlaces, allString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultPlaces;
	}

	private String buildURL(String baseURL, Map<String, String> args) {
		StringBuilder sb = new StringBuilder(baseURL + "?");
		for (Map.Entry<String, String> entry : args.entrySet()) {
			try {
				sb.append(URLEncoder.encode(entry.getKey(), "utf-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
				sb.append("&");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			}
		}
		return sb.toString();
	}

	private void buildPlaces(List<Place> resultPlaces, String all) {
		try {
			JSONObject root = new JSONObject(all.toString());
			JSONArray results = root.getJSONObject("responseData")
					.getJSONArray("results");
			for (int j = 0; j < results.length(); j++) {
				JSONObject place = results.getJSONObject(j);
				double latitude = place.getDouble("lat");
				double longitude = place.getDouble("lng");
				String name = place.getString("titleNoFormatting");
				resultPlaces.add(new Place(name, latitude, longitude));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
