package com.passioncoder.qmap.test.drawing;

import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.drawing.DrawingPreProcessor;

public class DrawingPreProcessorTest {

	/**
	 * Test whether the process of setting new coordinates of the points is
	 * correct and we print out the converting result of the process and check
	 * whether the result is correct.
	 */
	@Test
	public void testSetDrawingCoordinate() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addEdge(point1, point2);
		graph.addEdge(point2, point3);
		DrawingPreProcessor tempprocessor = new DrawingPreProcessor();
		tempprocessor.setDrawingCoordinate(graph);
		Set<Point> pointset = graph.vertexSet();
		// print out information of all the points and paths
		for (Point temppoint : pointset) {
			System.out.println(temppoint.getXPosition() + " "
					+ temppoint.getYPosition());
		}
		Set<Path> pathSet = graph.edgeSet();
		for (Path tempPath : pathSet) {
			System.out.println(tempPath.getPoint1XPosition() + " "
					+ tempPath.getPoint1YPosition() + " "
					+ tempPath.getPoint2XPosition() + ""
					+ tempPath.getPoint2YPosition());
		}
	}

	/**
	 * Test whether the process of setting new coordinates of the points is
	 * correct and we print out the converting result of the process and check
	 * whether the result is correct.
	 */
	@Test
	public void testSetDrawingCoordinate1() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(31.9999, 117.2929);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addEdge(point1, point2);
		graph.addEdge(point2, point3);
		graph.addEdge(point3, point4);
		DrawingPreProcessor tempprocessor = new DrawingPreProcessor();
		tempprocessor.setDrawingCoordinate(graph);
		Set<Point> pointset = graph.vertexSet();
		// print out information of all the points and paths
		for (Point temppoint : pointset) {
			System.out.println(temppoint.getXPosition() + " "
					+ temppoint.getYPosition());
		}
		Set<Path> pathSet = graph.edgeSet();
		for (Path tempPath : pathSet) {
			System.out.println(tempPath.getPoint1XPosition() + " "
					+ tempPath.getPoint1YPosition() + " "
					+ tempPath.getPoint2XPosition() + ""
					+ tempPath.getPoint2YPosition());
		}
	}

	/**
	 * Test whether the process of setting new coordinates of the points is
	 * correct and we print out the converting result of the process and check
	 * whether the result is correct.
	 */
	@Test
	public void testSetDrawingCoordinate2() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.888, 117.3333);
		Point point5 = new Point(34.222, 116.2222);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		Path testpath4 = new Path();
		testpath4.addPoint(point4);
		testpath4.addPoint(point5);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addVertex(point5);
		graph.addEdge(point1, point2);
		graph.addEdge(point2, point3);
		graph.addEdge(point3, point4);
		graph.addEdge(point4, point5);
		DrawingPreProcessor tempprocessor = new DrawingPreProcessor();
		tempprocessor.setDrawingCoordinate(graph);
		Set<Point> pointset = graph.vertexSet();
		// print out information of all the points and paths
		for (Point temppoint : pointset) {
			System.out.println(temppoint.getXPosition() + " "
					+ temppoint.getYPosition());
		}
		Set<Path> pathSet = graph.edgeSet();
		for (Path tempPath : pathSet) {
			System.out.println(tempPath.getPoint1XPosition() + " "
					+ tempPath.getPoint1YPosition() + " "
					+ tempPath.getPoint2XPosition() + ""
					+ tempPath.getPoint2YPosition());
		}
	}

	/**
	 * Test whether the process of setting new coordinates of the points is
	 * correct and we print out the converting result of the process and check
	 * whether the result is correct.
	 */
	@Test
	public void testSetDrawingCoordinate3() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.888, 117.3333);
		Point point5 = new Point(34.222, 116.2222);
		Point point6 = new Point(31.9842, 118.821602);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		Path testpath4 = new Path();
		testpath4.addPoint(point4);
		testpath4.addPoint(point5);
		Path testpath5 = new Path();
		testpath5.addPoint(point5);
		testpath5.addPoint(point6);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addVertex(point5);
		graph.addVertex(point6);
		graph.addEdge(point1, point2);
		graph.addEdge(point2, point3);
		graph.addEdge(point3, point4);
		graph.addEdge(point4, point5);
		graph.addEdge(point5, point6);
		DrawingPreProcessor tempprocessor = new DrawingPreProcessor();
		tempprocessor.setDrawingCoordinate(graph);
		Set<Point> pointset = graph.vertexSet();
		// print out information of all the points and paths
		for (Point temppoint : pointset) {
			System.out.println(temppoint.getXPosition() + " "
					+ temppoint.getYPosition());
		}
		Set<Path> pathSet = graph.edgeSet();
		for (Path tempPath : pathSet) {
			System.out.println(tempPath.getPoint1XPosition() + " "
					+ tempPath.getPoint1YPosition() + " "
					+ tempPath.getPoint2XPosition() + ""
					+ tempPath.getPoint2YPosition());
		}
	}

	/**
	 * Test whether the process of setting new coordinates of the points is
	 * correct and we print out the converting result of the process and check
	 * whether the result is correct.
	 */
	@Test
	public void testSetDrawingCoordinate4() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.888, 117.3333);
		Point point5 = new Point(34.222, 116.2222);
		Point point6 = new Point(31.9842, 118.821602);
		Point point7 = new Point(33.6666, 119.2222);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		Path testpath4 = new Path();
		testpath4.addPoint(point4);
		testpath4.addPoint(point5);
		Path testpath5 = new Path();
		testpath5.addPoint(point5);
		testpath5.addPoint(point6);
		Path testpath6 = new Path();
		testpath6.addPoint(point6);
		testpath6.addPoint(point7);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addVertex(point5);
		graph.addVertex(point6);
		graph.addVertex(point7);
		graph.addEdge(point1, point2);
		graph.addEdge(point2, point3);
		graph.addEdge(point3, point4);
		graph.addEdge(point4, point5);
		graph.addEdge(point5, point6);
		graph.addEdge(point6, point7);
		DrawingPreProcessor tempprocessor = new DrawingPreProcessor();
		tempprocessor.setDrawingCoordinate(graph);
		Set<Point> pointset = graph.vertexSet();
		// print out information of all the points and paths
		for (Point temppoint : pointset) {
			System.out.println(temppoint.getXPosition() + " "
					+ temppoint.getYPosition());
		}
		Set<Path> pathSet = graph.edgeSet();
		for (Path tempPath : pathSet) {
			System.out.println(tempPath.getPoint1XPosition() + " "
					+ tempPath.getPoint1YPosition() + " "
					+ tempPath.getPoint2XPosition() + ""
					+ tempPath.getPoint2YPosition());
		}
	}

	/**
	 * Test whether the process of setting new coordinates of the points is
	 * correct and we print out the converting result of the process and check
	 * whether the result is correct.
	 */
	@Test
	public void testSetDrawingCoordinate5() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addEdge(point1, point2);
		graph.addEdge(point2, point3);
		DrawingPreProcessor tempprocessor = new DrawingPreProcessor();
		tempprocessor.setDrawingCoordinate(graph);
		Set<Point> pointset = graph.vertexSet();
		// print out information of all the points and paths
		for (Point temppoint : pointset) {
			System.out.println(temppoint.getXPosition() + " "
					+ temppoint.getYPosition());
		}
		Set<Path> pathSet = graph.edgeSet();
		for (Path tempPath : pathSet) {
			System.out.println(tempPath.getPoint1XPosition() + " "
					+ tempPath.getPoint1YPosition() + " "
					+ tempPath.getPoint2XPosition() + ""
					+ tempPath.getPoint2YPosition());
		}
	}

	/**
	 * Test whether the process of setting new coordinates of the points is
	 * correct and we print out the converting result of the process and check
	 * whether the result is correct.
	 */
	@Test
	public void testSetDrawingCoordinate6() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addEdge(point1, point2);
		graph.addEdge(point2, point3);
		DrawingPreProcessor tempprocessor = new DrawingPreProcessor();
		tempprocessor.setDrawingCoordinate(graph);
		Set<Point> pointset = graph.vertexSet();
		// print out information of all the points and paths
		for (Point temppoint : pointset) {
			System.out.println(temppoint.getXPosition() + " "
					+ temppoint.getYPosition());
		}
		Set<Path> pathSet = graph.edgeSet();
		for (Path tempPath : pathSet) {
			System.out.println(tempPath.getPoint1XPosition() + " "
					+ tempPath.getPoint1YPosition() + " "
					+ tempPath.getPoint2XPosition() + ""
					+ tempPath.getPoint2YPosition());
		}
	}
}
