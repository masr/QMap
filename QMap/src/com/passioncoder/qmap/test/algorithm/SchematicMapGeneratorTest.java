package com.passioncoder.qmap.test.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;
import static org.junit.Assert.*;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.PointType;
import com.passioncoder.qmap.algorithm.SchematicMapGenerator;

public class SchematicMapGeneratorTest {

	private static final double COS_45 = Math.cos(1.0 / 4 * Math.PI);
	private static final double COS_0 = 1;
	private static final double COS_90 = 0;
	private static final double COS_135 = -COS_45;
	private static final double COS_180 = -1;

	@Test
	public void testGetSchematicMap() {
		testCaseOne();
		testCaseTwo();
		testCaseThree();
	}
	
	public void testCaseOne() {
		// Construct the graph.
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		Point one = new Point(32.058573, 118.796797);
		Point two = new Point(32.042495, 118.787012);
		Point three = new Point(32.04235, 118.812761);
		Point four = new Point(32.019138, 118.826752);
		// Add point.
		graph.addVertex(one);
		graph.addVertex(two);
		graph.addVertex(three);
		graph.addVertex(four);
		// Add path.
		Path path = graph.addEdge(one, two);
		path.addPoint(one);
		path.addPoint(two);
		path = graph.addEdge(two, three);
		path.addPoint(two);
		path.addPoint(three);
		path = graph.addEdge(three, four);
		path.addPoint(three);
		path.addPoint(four);
		SchematicMapGenerator generator = new SchematicMapGenerator();
		graph = generator.getSchematicMap(graph);
		List<Point> originalKeyPoints = new ArrayList<Point>();
		originalKeyPoints.add(one);
		originalKeyPoints.add(two);
		originalKeyPoints.add(three);
		originalKeyPoints.add(four);
		assertTrue (checkRelativePosition(originalKeyPoints, graph));
		assertTrue (checkPathDirection(graph));
	}

	private void testCaseTwo() {
		// Construct the graph.
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		Point one = new Point(32.058573, 118.796797);
		Point two = new Point(32.042495, 118.787012);
		Point three = new Point(32.04235, 118.812761);
		Point four = new Point(32.019138, 118.826752);
		// Add point.
		graph.addVertex(one);
		graph.addVertex(two);
		graph.addVertex(three);
		graph.addVertex(four);
		// Add path.
		Path path = graph.addEdge(one, two);
		path.addPoint(one);
		path.addPoint(two);
		path = graph.addEdge(two, three);
		path.addPoint(two);
		path.addPoint(three);
		path = graph.addEdge(three, four);
		path.addPoint(three);
		path.addPoint(four);
		SchematicMapGenerator generator = new SchematicMapGenerator();
		graph = generator.getSchematicMap(graph);
		List<Point> originalKeyPoints = new ArrayList<Point>();
		originalKeyPoints.add(one);
		originalKeyPoints.add(two);
		originalKeyPoints.add(three);
		originalKeyPoints.add(four);
		assert (checkRelativePosition(originalKeyPoints, graph));
		assert (checkPathDirection(graph));
	}

	private void testCaseThree() {
		// Construct the graph.
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		Point one = new Point(32.058573, 118.796797);
		Point two = new Point(32.042495, 118.787012);
		Point three = new Point(32.04235, 118.812761);
		Point four = new Point(32.019138, 118.826752);
		// Add point.
		graph.addVertex(one);
		graph.addVertex(two);
		graph.addVertex(three);
		graph.addVertex(four);
		// Add path.
		Path path = graph.addEdge(one, two);
		path.addPoint(one);
		path.addPoint(two);
		path = graph.addEdge(two, three);
		path.addPoint(two);
		path.addPoint(three);
		path = graph.addEdge(three, four);
		path.addPoint(three);
		path.addPoint(four);
		SchematicMapGenerator generator = new SchematicMapGenerator();
		graph = generator.getSchematicMap(graph);
		List<Point> originalKeyPoints = new ArrayList<Point>();
		originalKeyPoints.add(one);
		originalKeyPoints.add(two);
		originalKeyPoints.add(three);
		originalKeyPoints.add(four);
		assert (checkRelativePosition(originalKeyPoints, graph));
		assert (checkPathDirection(graph));
	}

	/**
	 * Check if the key points' relative position is reversed after the process.
	 * 
	 * @param origianlKeyPoints
	 * @param graph
	 */
	private boolean checkRelativePosition(List<Point> originalKeyPoints,
			Graph<Point, Path> graph) {
		List<Point> longitudePoints = new ArrayList<Point>(graph.vertexSet());
		List<Point> latitudePoints = new ArrayList<Point>(graph.vertexSet());

		// Sort the points in the map according to their longitude.
		Collections.sort(longitudePoints, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				double delta = o1.getLongitude() - o2.getLongitude();
				return delta > 0 ? 1 : -1;
			}

		});
		// Sort the points in the map according to their latitude.
		Collections.sort(latitudePoints, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				double delta = o1.getLatitude() - o2.getLatitude();
				return delta > 0 ? 1 : -1;
			}

		});

		// Check if longitude order is reversed.
		Collections.sort(originalKeyPoints, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				double delta = o1.getLongitude() - o2.getLongitude();
				return delta > 0 ? 1 : -1;
			}

		});

		int pointCounter = 0;
		for (Point realPoint : longitudePoints) {
			if (realPoint.getType() == PointType.KEY) {
				if (realPoint == originalKeyPoints.get(pointCounter)) {
					pointCounter++;
				} else {
					return false;
				}
			}
		}

		// Check if latitude order is reversed.
		Collections.sort(originalKeyPoints, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				double delta = o1.getLatitude() - o2.getLatitude();
				return delta > 0 ? 1 : -1;
			}

		});

		pointCounter = 0;
		for (Point realPoint : latitudePoints) {
			if (realPoint.getType() == PointType.KEY) {
				if (realPoint == originalKeyPoints.get(pointCounter)) {
					pointCounter++;
				} else {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Check if the result map's direction is 0, 45 or 90.
	 * 
	 * @param graph
	 */
	private boolean checkPathDirection(Graph<Point, Path> graph) {
		Set<Path> pathes = graph.edgeSet();
		for (Path path : pathes) {
			Point source = graph.getEdgeSource(path);
			Point target = graph.getEdgeTarget(path);
			double angle_cos = getAngle(source, target);
			if (!(angleEqual(angle_cos, COS_0) || angleEqual(angle_cos, COS_45)
					|| angleEqual(angle_cos, COS_90)
					|| angleEqual(angle_cos, COS_135) || angleEqual(angle_cos,
					COS_180))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the given line's degree.
	 * 
	 * @param center
	 *            One point of the line.
	 * @param moved
	 *            Another point of the line.
	 * @return The degree in cos.
	 */
	private double getAngle(Point center, Point moved) {
		double vector_x = moved.getLatitude() - center.getLatitude();
		double vector_y = moved.getLongitude() - center.getLongitude();
		double distance = Math.sqrt(Math.pow(vector_x, 2)
				+ Math.pow(vector_y, 2));
		double cos_angle = vector_x / distance;
		return cos_angle;
	}

	/**
	 * Test if two double value are equal.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private boolean angleEqual(double source, double target) {
		// based on the tolerance.
		if (Math.abs(source - target) < 0.01) {
			return true;
		} else {
			return false;
		}
	}

}
