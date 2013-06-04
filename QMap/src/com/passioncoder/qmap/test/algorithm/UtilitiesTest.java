package com.passioncoder.qmap.test.algorithm;

import static org.junit.Assert.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.Utilities;

public class UtilitiesTest {

	@Test
	public void testGetPointToLineDistance() {
		//TestCase One
		Point point = new Point(38.5674, 118.45678);
		Point one = new Point(38.23444, 119.06565);
		Point two = new Point(37.23454, 118.232321);
		double distance = Utilities.getPointToLineDistance(point, one, two);
		assertEquals(0.68089, distance, 0.0001);
		
		//TestCase Two
		point = new Point(0, 0);
		one = new Point(0, 0);
		two = new Point(1, 1);
		distance = Utilities.getPointToLineDistance(point, one, two);
		assertEquals(0, distance, 0.0001);
		//TestCase Three
		point = new Point(1, 2);
		one = new Point(1, 0);
		two = new Point(1, 1);
		distance = Utilities.getPointToLineDistance(point, one, two);
		assertEquals(0, distance, 0.0001);
	}

	@Test
	public void testPointToPointDistance() {
		//TestCase One
		Point one = new Point(38.5674, 118.45678);
		Point two = new Point(38.23444, 119.06565);
		double distance = Utilities.pointToPointDistance(one, two);
		assertEquals(0.69396, distance, 0.0001);
		
		//TestCase Two
		one = new Point(0, 0);
		two = new Point(0, 0);
		distance = Utilities.pointToPointDistance(one, two);
		assertEquals(0, distance, 0.0001);
		
		//TestCase Three
		one = new Point(0, 0);
		two = new Point(0, 1);
		distance = Utilities.pointToPointDistance(one, two);
		assertEquals(1, distance, 0.0001);
	}

	@Test
	public void testMovePoint() {
		//TestCase One
		//Build a new graph.
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		//Add some points and pathes.
		Point base = new Point(38.5674, 118.45678);
		Point one = new Point(38.23444, 119.06565);
		Point two = new Point(37.23454, 118.232321);
		graph.addVertex(base);
		graph.addVertex(one);
		graph.addVertex(two);
		graph.addEdge(base, one);
		graph.addEdge(one, two);
		Utilities.movePoint(graph, base, one, 0.0234, 0.3223);
		//Test the result
		assertEquals(38.5674, base.getLatitude(), 0.0001);
		assertEquals(118.45678, base.getLongitude(), 0.0001);
		assertEquals(38.25784, one.getLatitude(), 0.0001);
		assertEquals(119.38795, one.getLongitude(), 0.0001);
		assertEquals(37.2579, two.getLatitude(), 0.0001);
		assertEquals(118.5546, two.getLongitude(), 0.0001);
		
		//TestCase Two
		graph = new SimpleGraph<Point, Path>(Path.class);
		base = new Point(38.5674, 118.45678);
		one = new Point(38.23444, 119.06565);
		two = new Point(37.23454, 118.232321);
		graph.addVertex(base);
		graph.addVertex(one);
		graph.addVertex(two);
		graph.addEdge(base, one);
		graph.addEdge(one, two);
		Utilities.movePoint(graph, base, one, 0.0234, 0.3223);
		assertEquals(38.5674, base.getLatitude(), 0.0001);
		assertEquals(118.45678, base.getLongitude(), 0.0001);
		assertEquals(38.25784, one.getLatitude(), 0.0001);
		assertEquals(119.38795, one.getLongitude(), 0.0001);
		assertEquals(37.2579, two.getLatitude(), 0.0001);
		assertEquals(118.5546, two.getLongitude(), 0.0001);
		
		//TestCase Three
		graph = new SimpleGraph<Point, Path>(Path.class);
		base = new Point(38.5674, 118.45678);
		one = new Point(38.23444, 119.06565);
		two = new Point(37.23454, 118.232321);
		graph.addVertex(base);
		graph.addVertex(one);
		graph.addVertex(two);
		graph.addEdge(base, one);
		graph.addEdge(one, two);
		Utilities.movePoint(graph, base, one, 0.0234, 0.3223);
		assertEquals(38.5674, base.getLatitude(), 0.0001);
		assertEquals(118.45678, base.getLongitude(), 0.0001);
		assertEquals(38.25784, one.getLatitude(), 0.0001);
		assertEquals(119.38795, one.getLongitude(), 0.0001);
		assertEquals(37.2579, two.getLatitude(), 0.0001);
		assertEquals(118.5546, two.getLongitude(), 0.0001);
	}

	@Test
	public void testCaculateAngleDegree() {
		//TestCase One
		Point angle = new Point(38.5674, 118.45678);
		Point one = new Point(38.23444, 119.06565);
		Point two = new Point(37.23454, 118.232321);
		double degree = Utilities.caculateAngleDegree(angle, one, two);
		assertEquals(0.3274, degree, 0.0001);
		
		//TestCase Two
		angle = new Point(0, 0);
		one = new Point(0, 1);
		two = new Point(1, 0);
		degree = Utilities.caculateAngleDegree(angle, one, two);
		assertEquals(0, degree, 0.0001);
		
		//TestCase Three
		angle = new Point(0, 0);
		one = new Point(1, 1);
		two = new Point(1, 0);
		degree = Utilities.caculateAngleDegree(angle, one, two);
		assertEquals(0.7071, degree, 0.0001);
	}
}
