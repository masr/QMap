package com.passioncoder.qmap.preprocess;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.drawing.Drawing;

public class SubwayProcessor {
	private GraphProcessor dataProcessor;

	public SubwayProcessor() {
		dataProcessor = new GraphProcessor();
	}

	public BufferedImage getImage(List<Path> paths) {
		GraphBuilder graphBuilder = new GraphBuilder();
		Graph<Point, Path> graph = graphBuilder.buildGoogleGraph(paths);
		Drawing.setMaxActualSize(0);
		return dataProcessor.processData(graph);
	}

	public List<Path> buildPathList(String qString) {
		List<Path> paths = new ArrayList<Path>();
		try {
			JSONObject pathsObject = new JSONObject(qString);
			JSONArray pathsArray = pathsObject.getJSONArray("paths");
			for (int i = 0; i < pathsArray.length(); i++) {
				Path path = new Path();
				paths.add(path);
				JSONObject spotsObject = pathsArray.getJSONObject(i);
				JSONArray spotsArray = spotsObject.getJSONArray("spots");
				for (int j = 0; j < spotsArray.length(); j++) {
					JSONObject spotObject = spotsArray.getJSONObject(j);
					double lat = spotObject.getDouble("lat");
					double lng = spotObject.getDouble("lng");
					String name = spotObject.getString("name");
					int repeatId = spotObject.getInt("repeat_id");
					Point point;
					if ((point = getRepeatPoint(paths, repeatId)) != null) {
						path.addPoint(point);
						continue;
					}

					point = new Point(name, lat, lng);
					point.setRepeatId(repeatId);
					path.addPoint(point);
				}
			}
			return paths;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private Point getRepeatPoint(List<Path> paths, int repeatId) {
		if (repeatId==0)
			return null;
		for (Path path : paths) {
			for (Point point : path.getPoints()) {
				if (point.getRepeatId() == repeatId)
					return point;
			}
		}
		return null;
	}
}
