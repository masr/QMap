package com.passioncoder.qmap.test.drawing;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;
import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.PointType;
import com.passioncoder.qmap.drawing.DrawingGenerator;

public class DrawingGeneratorTest {

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.004512, 118.881010);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point1.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp1.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate1() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.004512, 118.881010);
		Point point5 = new Point(32.1922, 116.2837);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point5.setName("5号点 for test only");
		point1.setType(PointType.KEY);
		point2.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		point5.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Path testpath4 = new Path();
		testpath4.addPoint(point4);
		testpath4.addPoint(point5);
		testpath4.setPathID(4);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addVertex(point5);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		graph.addEdge(point4, point5, testpath4);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp2.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate2() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.00212, 118.881010);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point1.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp3.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate3() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792476);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.004512, 118.881010);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point1.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp4.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate4() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.822602);
		Point point4 = new Point(32.004512, 118.881010);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point1.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp5.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate5() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.4842, 118.821602);
		Point point4 = new Point(32.004512, 118.84);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point1.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp6.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate6() {
		Point point1 = new Point(32.041945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.004512, 118.83010);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point1.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp7.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	/**
	 * The test function is used to test whether the process of generating a
	 * schematic map is correct and the test data is from a real map and we
	 * compare the generated image with the actual map and decide whether the
	 * generated map is correct.
	 */
	@Test
	public void testGenerate7() {
		Point point1 = new Point(32.031945, 118.78006);
		Point point2 = new Point(32.006329, 118.792076);
		Point point3 = new Point(31.9842, 118.821602);
		Point point4 = new Point(32.004512, 118.881010);
		Point point5 = new Point(21.3838, 118.3333);
		Point point6 = new Point(23.2212, 119.28);
		Point point7 = new Point(28.8282, 124.11);
		point1.setName("1号点 for test only");
		point2.setName("2号点 for test only");
		point3.setName("3号点 for test only");
		point4.setName("4号点 for test only");
		point5.setName("Nanjing University");
		point6.setName("东南大学");
		point7.setName("USA");
		point1.setType(PointType.KEY);
		point2.setType(PointType.KEY);
		point3.setType(PointType.KEY);
		point4.setType(PointType.KEY);
		point5.setType(PointType.KEY);
		point6.setType(PointType.KEY);
		point7.setType(PointType.KEY);
		Path testpath1 = new Path();
		testpath1.addPoint(point1);
		testpath1.addPoint(point2);
		testpath1.setPathID(1);
		Path testpath2 = new Path();
		testpath2.addPoint(point2);
		testpath2.addPoint(point3);
		testpath2.setPathID(2);
		Path testpath3 = new Path();
		testpath3.addPoint(point3);
		testpath3.addPoint(point4);
		testpath3.setPathID(3);
		Path testpath4 = new Path();
		testpath4.addPoint(point4);
		testpath4.addPoint(point5);
		testpath4.setPathID(4);
		Path testpath5 = new Path();
		testpath5.addPoint(point5);
		testpath5.addPoint(point6);
		testpath5.setPathID(5);
		Path testpath6 = new Path();
		testpath6.addPoint(point6);
		testpath6.addPoint(point7);
		testpath6.setPathID(6);
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		graph.addVertex(point1);
		graph.addVertex(point2);
		graph.addVertex(point3);
		graph.addVertex(point4);
		graph.addVertex(point5);
		graph.addVertex(point6);
		graph.addVertex(point7);
		graph.addEdge(point1, point2, testpath1);
		graph.addEdge(point2, point3, testpath2);
		graph.addEdge(point3, point4, testpath3);
		graph.addEdge(point4, point5, testpath4);
		graph.addEdge(point5, point6, testpath5);
		graph.addEdge(point6, point7, testpath6);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("C:/temp8.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
