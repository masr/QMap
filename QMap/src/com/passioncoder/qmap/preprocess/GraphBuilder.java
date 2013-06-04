package com.passioncoder.qmap.preprocess;

import java.util.Date;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import com.passioncoder.qmap.algorithm.GoogleAPI;
import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;

/**
 * @author lidejia
 * 
 */

public class GraphBuilder {

	public Graph<Point, Path> buildGoogleGraph(List<Path> paths) {
		Date eDate=new Date();
		
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		List<List<Path>> result = GoogleAPI.findPaths(paths);
		for (int i = 0, pathID = 0; i < result.size(); i++, pathID++) {
			Path originalPath = paths.get(i);
			List<Point> points = originalPath.getPoints();
			List<Path> realPathes = result.get(i);
			for (int j = 0; j < points.size() - 1; j++) {
				Point startPoint = points.get(j);
				Point endPoint = points.get(j + 1);
				graph.addVertex(startPoint);
				graph.addVertex(endPoint);
				Path temp_path = realPathes.get(j);
				temp_path.setStartPoint(startPoint);
				temp_path.setEndPoint(endPoint);
				if (originalPath.getPathID() == -1) {
					temp_path.setPathID(pathID);
				} else {
					temp_path.setPathID(originalPath.getPathID());
				}
				graph.addEdge(startPoint, endPoint, temp_path);
			}
		}
		return graph;
	}

	public Graph<Point, Path> buildSimpleGraph(List<Path> paths) {
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		int pathID = 0;
		for (Path path : paths) {
			List<Point> points = path.getPoints();
			for (int i = 0; i < points.size() - 1; i++) {
				Point start = points.get(i);
				Point end = points.get(i + 1);
				graph.addVertex(start);
				graph.addVertex(end);
				Path resultPath = new Path();
				resultPath.addPoint(start);
				resultPath.addPoint(end);
				if (path.getPathID() == -1) {
					resultPath.setPathID(pathID);
				} else {
					resultPath.setPathID(path.getPathID());
				}
				graph.addEdge(start, end, resultPath);
			}
			pathID++;
		}
		return graph;
	}

}
