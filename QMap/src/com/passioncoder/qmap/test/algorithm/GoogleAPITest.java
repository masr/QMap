package com.passioncoder.qmap.test.algorithm;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.passioncoder.qmap.algorithm.GoogleAPI;
import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;

public class GoogleAPITest {

	@Test
	public void testFindPaths() {
		testCaseOne();
		testCaseTwo();
		testCaseThree();
	}

	private void testCaseOne() {
		Point start = new Point("XX", 32.048097, 118.777742);
		Point end = new Point("XX", 32.034565, 118.792591);
		Path path = new Path();
		path.addPoint(start);
		path.addPoint(end);
		List<Path> pathes = new LinkedList<Path>();
		pathes.add(path);
		List<List<Path>> result = GoogleAPI.findPaths(pathes);
		path = result.get(0).get(0);
		/*
		 * distance = 2.7;
		 */
		assertEquals(2.7, path.getDistance(), 0.0001);
		List<Point> points = path.getPoints();
		/*
		 * 0 longitude: 118.777742 latitude: 32.048097
		 * 
		 * 1 longitude: 118.77632 latitude: 32.04234
		 * 
		 * 2 longitude: 118.77624 latitude: 32.04226
		 * 
		 * 3 longitude: 118.77585 latitude: 32.04115
		 * 
		 * 4 longitude: 118.78016 latitude: 32.03973
		 * 
		 * 5 longitude: 118.78405 latitude: 32.03801
		 * 
		 * 6 longitude: 118.78745 latitude: 32.03726
		 * 
		 * 7 longitude: 118.78731 latitude: 32.03599
		 * 
		 * 8 longitude: 118.792591 latitude: 32.034565
		 */
		assertEquals(32.048097, points.get(0).getLatitude(), 0.0001);
		assertEquals(118.777742, points.get(0).getLongitude(), 0.0001);
		assertEquals(32.04234, points.get(1).getLatitude(), 0.0001);
		assertEquals(118.77632, points.get(1).getLongitude(), 0.0001);
		assertEquals(32.04226, points.get(2).getLatitude(), 0.0001);
		assertEquals(118.77624, points.get(2).getLongitude(), 0.0001);
		assertEquals(32.04115, points.get(3).getLatitude(), 0.0001);
		assertEquals(118.77585, points.get(3).getLongitude(), 0.0001);
		assertEquals(32.03973, points.get(4).getLatitude(), 0.0001);
		assertEquals(118.78016, points.get(4).getLongitude(), 0.0001);
		assertEquals(32.03801, points.get(5).getLatitude(), 0.0001);
		assertEquals(118.78405, points.get(5).getLongitude(), 0.0001);
		assertEquals(32.03726, points.get(6).getLatitude(), 0.0001);
		assertEquals(118.78745, points.get(6).getLongitude(), 0.0001);
		assertEquals(32.03599, points.get(7).getLatitude(), 0.0001);
		assertEquals(118.78731, points.get(7).getLongitude(), 0.0001);
		assertEquals(32.034565, points.get(8).getLatitude(), 0.0001);
		assertEquals(118.792591, points.get(8).getLongitude(), 0.0001);
	}

	private void testCaseTwo() {
		Point start = new Point("XX", 32.048097, 118.777742);
		Point end = new Point("XX", 32.034565, 118.792591);
		Path path = new Path();
		path.addPoint(start);
		path.addPoint(end);
		List<Path> pathes = new LinkedList<Path>();
		pathes.add(path);
		List<List<Path>> result = GoogleAPI.findPaths(pathes);
		path = result.get(0).get(0);
		/*
		 * distance = 2.7;
		 */
		assertEquals(2.7, path.getDistance(), 0.0001);
		List<Point> points = path.getPoints();
		/*
		 * 0 longitude: 118.777742 latitude: 32.048097
		 * 
		 * 1 longitude: 118.77632 latitude: 32.04234
		 * 
		 * 2 longitude: 118.77624 latitude: 32.04226
		 * 
		 * 3 longitude: 118.77585 latitude: 32.04115
		 * 
		 * 4 longitude: 118.78016 latitude: 32.03973
		 * 
		 * 5 longitude: 118.78405 latitude: 32.03801
		 * 
		 * 6 longitude: 118.78745 latitude: 32.03726
		 * 
		 * 7 longitude: 118.78731 latitude: 32.03599
		 * 
		 * 8 longitude: 118.792591 latitude: 32.034565
		 */
		assertEquals(32.048097, points.get(0).getLatitude(), 0.0001);
		assertEquals(118.777742, points.get(0).getLongitude(), 0.0001);
		assertEquals(32.04234, points.get(1).getLatitude(), 0.0001);
		assertEquals(118.77632, points.get(1).getLongitude(), 0.0001);
		assertEquals(32.04226, points.get(2).getLatitude(), 0.0001);
		assertEquals(118.77624, points.get(2).getLongitude(), 0.0001);
		assertEquals(32.04115, points.get(3).getLatitude(), 0.0001);
		assertEquals(118.77585, points.get(3).getLongitude(), 0.0001);
		assertEquals(32.03973, points.get(4).getLatitude(), 0.0001);
		assertEquals(118.78016, points.get(4).getLongitude(), 0.0001);
		assertEquals(32.03801, points.get(5).getLatitude(), 0.0001);
		assertEquals(118.78405, points.get(5).getLongitude(), 0.0001);
		assertEquals(32.03726, points.get(6).getLatitude(), 0.0001);
		assertEquals(118.78745, points.get(6).getLongitude(), 0.0001);
		assertEquals(32.03599, points.get(7).getLatitude(), 0.0001);
		assertEquals(118.78731, points.get(7).getLongitude(), 0.0001);
		assertEquals(32.034565, points.get(8).getLatitude(), 0.0001);
		assertEquals(118.792591, points.get(8).getLongitude(), 0.0001);
	}

	private void testCaseThree() {
		//Construct a new path.
		Point start = new Point("XX", 32.048097, 118.777742);
		Point end = new Point("XX", 32.034565, 118.792591);
		Path path = new Path();
		path.addPoint(start);
		path.addPoint(end);
		List<Path> pathes = new LinkedList<Path>();
		pathes.add(path);
		List<List<Path>> result = GoogleAPI.findPaths(pathes);
		path = result.get(0).get(0);
		/*
		 * distance = 2.7;
		 */
		assertEquals(2.7, path.getDistance(), 0.0001);
		List<Point> points = path.getPoints();
		/*
		 * 0 longitude: 118.777742 latitude: 32.048097
		 * 
		 * 1 longitude: 118.77632 latitude: 32.04234
		 * 
		 * 2 longitude: 118.77624 latitude: 32.04226
		 * 
		 * 3 longitude: 118.77585 latitude: 32.04115
		 * 
		 * 4 longitude: 118.78016 latitude: 32.03973
		 * 
		 * 5 longitude: 118.78405 latitude: 32.03801
		 * 
		 * 6 longitude: 118.78745 latitude: 32.03726
		 * 
		 * 7 longitude: 118.78731 latitude: 32.03599
		 * 
		 * 8 longitude: 118.792591 latitude: 32.034565
		 */
		assertEquals(32.048097, points.get(0).getLatitude(), 0.0001);
		assertEquals(118.777742, points.get(0).getLongitude(), 0.0001);
		assertEquals(32.04234, points.get(1).getLatitude(), 0.0001);
		assertEquals(118.77632, points.get(1).getLongitude(), 0.0001);
		assertEquals(32.04226, points.get(2).getLatitude(), 0.0001);
		assertEquals(118.77624, points.get(2).getLongitude(), 0.0001);
		assertEquals(32.04115, points.get(3).getLatitude(), 0.0001);
		assertEquals(118.77585, points.get(3).getLongitude(), 0.0001);
		assertEquals(32.03973, points.get(4).getLatitude(), 0.0001);
		assertEquals(118.78016, points.get(4).getLongitude(), 0.0001);
		assertEquals(32.03801, points.get(5).getLatitude(), 0.0001);
		assertEquals(118.78405, points.get(5).getLongitude(), 0.0001);
		assertEquals(32.03726, points.get(6).getLatitude(), 0.0001);
		assertEquals(118.78745, points.get(6).getLongitude(), 0.0001);
		assertEquals(32.03599, points.get(7).getLatitude(), 0.0001);
		assertEquals(118.78731, points.get(7).getLongitude(), 0.0001);
		assertEquals(32.034565, points.get(8).getLatitude(), 0.0001);
		assertEquals(118.792591, points.get(8).getLongitude(), 0.0001);
	}
}