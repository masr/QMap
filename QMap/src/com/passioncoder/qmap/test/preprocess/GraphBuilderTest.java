package com.passioncoder.qmap.test.preprocess;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.junit.Test;
import static org.junit.Assert.*;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.Utilities;
import com.passioncoder.qmap.preprocess.GraphBuilder;

public class GraphBuilderTest {

	@Test
	public void testBuildGraph() {
		testCaseOne();
		testCaseTwo();
		testCaseThree();
	}
	@Test
	public void testCaseOne() {
		// Add points.
		List<Point> keyPoints = new LinkedList<Point>();
		Path path = new Path();
		Point point = new Point("XXX", 32.042423, 118.766155);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.041259, 118.776112);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.040386, 118.794823);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.005747, 118.796368);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 31.992645, 118.825722);
		path.addPoint(point);
		keyPoints.add(point);
		// Add paths.
		List<Path> pathes = new LinkedList<Path>();
		pathes.add(path);
		// Build the graph.
		GraphBuilder builder = new GraphBuilder();
		Graph<Point, Path> graph = builder.buildGoogleGraph(pathes);
		//
		assertTrue(checkKeyPoints(graph, keyPoints));
		// Build the inner path.
		List<Path> realPaths = new LinkedList<Path>();
		//
		path = new Path();
		point = new Point(32.042423, 118.766155);
		path.addPoint(point);
		point = new Point(32.04089, 118.7668);
		path.addPoint(point);
		point = new Point(32.0406, 118.76816);
		path.addPoint(point);
		point = new Point(32.0416, 118.76841);
		path.addPoint(point);
		point = new Point(32.041259, 118.776112);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.041259, 118.776112);
		path.addPoint(point);
		point = new Point(32.0412, 118.775875);
		path.addPoint(point);
		point = new Point(32.04226, 118.77624);
		path.addPoint(point);
		point = new Point(32.04173, 118.78415);
		path.addPoint(point);
		point = new Point(32.04095, 118.7948);
		path.addPoint(point);
		point = new Point(32.040386, 118.794823);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.040386, 118.794823);
		path.addPoint(point);
		point = new Point(32.03968, 118.79446);
		path.addPoint(point);
		point = new Point(32.03846, 118.79841);
		path.addPoint(point);
		point = new Point(32.03837, 118.79899);
		path.addPoint(point);
		point = new Point(32.0333, 118.7977);
		path.addPoint(point);
		point = new Point(32.03243, 118.80237);
		path.addPoint(point);
		point = new Point(32.0324, 118.80275);
		path.addPoint(point);
		point = new Point(32.02178, 118.80055);
		path.addPoint(point);
		point = new Point(32.01446, 118.79846);
		path.addPoint(point);
		point = new Point(32.00751, 118.79621);
		path.addPoint(point);
		point = new Point(32.00589, 118.79643);
		path.addPoint(point);
		point = new Point(32.005747, 118.796368);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.005747, 118.796368);
		path.addPoint(point);
		point = new Point(32.00751, 118.79621);
		path.addPoint(point);
		point = new Point(32.00481, 118.79616);
		path.addPoint(point);
		point = new Point(31.9993, 118.79634);
		path.addPoint(point);
		point = new Point(31.99808, 118.79909);
		path.addPoint(point);
		point = new Point(31.99803, 118.79907);
		path.addPoint(point);
		point = new Point(31.99485, 118.80595);
		path.addPoint(point);
		point = new Point(31.99245, 118.80483);
		path.addPoint(point);
		point = new Point(31.99492, 118.82619);
		path.addPoint(point);
		point = new Point(31.99357, 118.82793);
		path.addPoint(point);
		point = new Point(31.992645, 118.825722);
		path.addPoint(point);
		realPaths.add(path);
		//
		assertTrue(checkInnerPoints(graph, realPaths));
	}
	@Test
	public void testCaseTwo() {
		// Add points.
		List<Point> keyPoints = new LinkedList<Point>();
		Path path = new Path();
		Point point = new Point("XXX", 32.042423, 118.766155);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.041259, 118.776112);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.040386, 118.794823);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.005747, 118.796368);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 31.992645, 118.825722);
		path.addPoint(point);
		keyPoints.add(point);
		// Add paths.
		List<Path> pathes = new LinkedList<Path>();
		pathes.add(path);
		// Build the graph.
		GraphBuilder builder = new GraphBuilder();
		Graph<Point, Path> graph = builder.buildGoogleGraph(pathes);
		//
		assertTrue(checkKeyPoints(graph, keyPoints));
		// Build the inner path.
		List<Path> realPaths = new LinkedList<Path>();
		//
		path = new Path();
		point = new Point(32.042423, 118.766155);
		path.addPoint(point);
		point = new Point(32.04089, 118.7668);
		path.addPoint(point);
		point = new Point(32.0406, 118.76816);
		path.addPoint(point);
		point = new Point(32.0416, 118.76841);
		path.addPoint(point);
		point = new Point(32.041259, 118.776112);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.041259, 118.776112);
		path.addPoint(point);
		point = new Point(32.0412, 118.775875);
		path.addPoint(point);
		point = new Point(32.04226, 118.77624);
		path.addPoint(point);
		point = new Point(32.04173, 118.78415);
		path.addPoint(point);
		point = new Point(32.04095, 118.7948);
		path.addPoint(point);
		point = new Point(32.040386, 118.794823);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.040386, 118.794823);
		path.addPoint(point);
		point = new Point(32.03968, 118.79446);
		path.addPoint(point);
		point = new Point(32.03846, 118.79841);
		path.addPoint(point);
		point = new Point(32.03837, 118.79899);
		path.addPoint(point);
		point = new Point(32.0333, 118.7977);
		path.addPoint(point);
		point = new Point(32.03243, 118.80237);
		path.addPoint(point);
		point = new Point(32.0324, 118.80275);
		path.addPoint(point);
		point = new Point(32.02178, 118.80055);
		path.addPoint(point);
		point = new Point(32.01446, 118.79846);
		path.addPoint(point);
		point = new Point(32.00751, 118.79621);
		path.addPoint(point);
		point = new Point(32.00589, 118.79643);
		path.addPoint(point);
		point = new Point(32.005747, 118.796368);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.005747, 118.796368);
		path.addPoint(point);
		point = new Point(32.00751, 118.79621);
		path.addPoint(point);
		point = new Point(32.00481, 118.79616);
		path.addPoint(point);
		point = new Point(31.9993, 118.79634);
		path.addPoint(point);
		point = new Point(31.99808, 118.79909);
		path.addPoint(point);
		point = new Point(31.99803, 118.79907);
		path.addPoint(point);
		point = new Point(31.99485, 118.80595);
		path.addPoint(point);
		point = new Point(31.99245, 118.80483);
		path.addPoint(point);
		point = new Point(31.99492, 118.82619);
		path.addPoint(point);
		point = new Point(31.99357, 118.82793);
		path.addPoint(point);
		point = new Point(31.992645, 118.825722);
		path.addPoint(point);
		realPaths.add(path);
		//
		assertTrue(checkInnerPoints(graph, realPaths));
	}
	@Test
	public void testCaseThree() {
		// Add points.
		List<Point> keyPoints = new LinkedList<Point>();
		Path path = new Path();
		Point point = new Point("XXX", 32.042423, 118.766155);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.041259, 118.776112);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.040386, 118.794823);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 32.005747, 118.796368);
		path.addPoint(point);
		keyPoints.add(point);
		point = new Point("XXX", 31.992645, 118.825722);
		path.addPoint(point);
		keyPoints.add(point);
		// Add paths.
		List<Path> pathes = new LinkedList<Path>();
		pathes.add(path);
		// Build the graph.
		GraphBuilder builder = new GraphBuilder();
		Graph<Point, Path> graph = builder.buildGoogleGraph(pathes);
		//
		assertTrue(checkKeyPoints(graph, keyPoints));
		// Build the inner path.
		List<Path> realPaths = new LinkedList<Path>();
		//
		path = new Path();
		point = new Point(32.042423, 118.766155);
		path.addPoint(point);
		point = new Point(32.04089, 118.7668);
		path.addPoint(point);
		point = new Point(32.0406, 118.76816);
		path.addPoint(point);
		point = new Point(32.0416, 118.76841);
		path.addPoint(point);
		point = new Point(32.041259, 118.776112);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.041259, 118.776112);
		path.addPoint(point);
		point = new Point(32.0412, 118.775875);
		path.addPoint(point);
		point = new Point(32.04226, 118.77624);
		path.addPoint(point);
		point = new Point(32.04173, 118.78415);
		path.addPoint(point);
		point = new Point(32.04095, 118.7948);
		path.addPoint(point);
		point = new Point(32.040386, 118.794823);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.040386, 118.794823);
		path.addPoint(point);
		point = new Point(32.03968, 118.79446);
		path.addPoint(point);
		point = new Point(32.03846, 118.79841);
		path.addPoint(point);
		point = new Point(32.03837, 118.79899);
		path.addPoint(point);
		point = new Point(32.0333, 118.7977);
		path.addPoint(point);
		point = new Point(32.03243, 118.80237);
		path.addPoint(point);
		point = new Point(32.0324, 118.80275);
		path.addPoint(point);
		point = new Point(32.02178, 118.80055);
		path.addPoint(point);
		point = new Point(32.01446, 118.79846);
		path.addPoint(point);
		point = new Point(32.00751, 118.79621);
		path.addPoint(point);
		point = new Point(32.00589, 118.79643);
		path.addPoint(point);
		point = new Point(32.005747, 118.796368);
		path.addPoint(point);
		realPaths.add(path);
		//
		path = new Path();
		point = new Point(32.005747, 118.796368);
		path.addPoint(point);
		point = new Point(32.00751, 118.79621);
		path.addPoint(point);
		point = new Point(32.00481, 118.79616);
		path.addPoint(point);
		point = new Point(31.9993, 118.79634);
		path.addPoint(point);
		point = new Point(31.99808, 118.79909);
		path.addPoint(point);
		point = new Point(31.99803, 118.79907);
		path.addPoint(point);
		point = new Point(31.99485, 118.80595);
		path.addPoint(point);
		point = new Point(31.99245, 118.80483);
		path.addPoint(point);
		point = new Point(31.99492, 118.82619);
		path.addPoint(point);
		point = new Point(31.99357, 118.82793);
		path.addPoint(point);
		point = new Point(31.992645, 118.825722);
		path.addPoint(point);
		realPaths.add(path);
		//
		assertTrue(checkInnerPoints(graph, realPaths));
	}

	/**
	 * Check if the key point is correct after the build graph process.
	 * 
	 * @param graph
	 *            The generated graph.
	 * @param keyPoints
	 *            The original key points.
	 * @return True or False
	 */
	private boolean checkKeyPoints(Graph<Point, Path> graph,
			List<Point> keyPoints) {
		Set<Point> points = graph.vertexSet();
		if (keyPoints.size() != points.size()) {
			return false;
		}
		for (Point point : keyPoints) {
			if (!points.contains(point)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the inner path is correct.
	 * 
	 * @param graph
	 *            The generated graph.
	 * @param realPathes
	 *            The real paths.
	 * @return True or False
	 */
	private boolean checkInnerPoints(Graph<Point, Path> graph,
			List<Path> realPathes) {
		Set<Path> pathes = graph.edgeSet();
		for (Path path : pathes) {
			List<Point> points = path.getPoints();
			boolean found = false;
			for (Path realPath : realPathes) {
				List<Point> realPoints = realPath.getPoints();
				// Find the corresponding points.
				if (points.size() == realPoints.size()) {
					if (Utilities.pointToPointDistance(realPoints.get(0),
							points.get(0)) < 0.0001
							&& Utilities.pointToPointDistance(
									points.get(points.size() - 1),
									realPoints.get(realPoints.size() - 1)) < 0.0001) {
						for (int i = 0; i < points.size(); i++) {
							if (Utilities.pointToPointDistance(points.get(i),
									realPoints.get(i)) > 0.0001) {
								return false;
							}
						}
						found = true;
						break;
					}
				}
			}
			// if no matching, return false.
			if (!found) {
				return false;
			}
		}
		return true;
	}

}