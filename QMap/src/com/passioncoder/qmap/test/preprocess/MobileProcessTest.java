package com.passioncoder.qmap.test.preprocess;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.preprocess.UserCenterProcessor;

public class MobileProcessTest {
	private String center;
	private String keywordString;
	private String range;

	@Test
	public void testGetPointsMiddleRange() {
		center = "32.058365,118.7964";
		keywordString = "大学";
		range = "0.032,0.032";
		UserCenterProcessor userCenterProcessor = new UserCenterProcessor();
		List<Point> points = userCenterProcessor.getPoints(center, range,
				keywordString);
		assertNotNull(points);
		assertNotSame(0, points.size());
		List<String> names = getNameList(points);

		assertTrue(names.contains("南京大学"));
		assertTrue(names.contains("东南大学"));

		assertTrue(names.contains("南京金陵国际语言进修学院"));

		assertTrue(names.contains("中国天文学会"));

	}

	@Test
	public void testGetPointsLargeRange() {
		center = "32.058365,118.7964";
		keywordString = "大学";
		range = "1.024,1.024";
		UserCenterProcessor userCenterProcessor = new UserCenterProcessor();
		List<Point> points = userCenterProcessor.getPoints(center, range,
				keywordString);
		assertNotNull(points);
		assertNotSame(0, points.size());
		List<String> names = getNameList(points);

		assertTrue(names.contains("南京邮电大学"));
		assertTrue(names.contains("南京高等职业技术学校"));

		assertTrue(names.contains("南京理工大学"));

		assertTrue(names.contains("南京师范大学附中"));

	}

	@Test
	public void testGetPointsSmallRange() {
		center = "32.058365,118.7964";
		keywordString = "大学";
		range = "0.001,0.001";
		UserCenterProcessor userCenterProcessor = new UserCenterProcessor();
		List<Point> points = userCenterProcessor.getPoints(center, range,
				keywordString);
		assertNotNull(points);
		assertNotSame(0, points.size());
		List<String> names = getNameList(points);

		assertTrue(names.contains("金陵老年大学"));
		assertTrue(names.contains("东南大学邮政所"));

		assertTrue(names.contains("东南大学建筑设计研究院"));

		assertTrue(names.contains("东南大学邮政所"));

	}

	@Test
	public void testGetPointsEnglish() {
		center = "37.49,-119.58";// in california
		keywordString = "university";
		range = "0.512,0.512";
		UserCenterProcessor userCenterProcessor = new UserCenterProcessor();
		List<Point> points = userCenterProcessor.getPoints(center, range,
				keywordString);
		assertNotNull(points);
		assertNotSame(0, points.size());
		List<String> names = getNameList(points);

		assertTrue(names.contains("Co-Op Extension"));
		assertTrue(names.contains("Community Medical Center"));

		assertTrue(names.contains("Mammoth Hospital"));

	}
	
	@Test
	public void testGetPaths(){
		center = "32.058365,118.7964";
		keywordString = "大学";
		range = "0.001,0.001";
		UserCenterProcessor userCenterProcessor = new UserCenterProcessor();
		List<Point> points = userCenterProcessor.getPoints(center, range,
				keywordString);
		Point centerPoint=new Point(32.058365, 118.7964);
      List<Path> paths=userCenterProcessor.getPaths(points, centerPoint);
      assertNotNull(paths);
      assertNotSame(0, paths.size());
     assertEquals(11, paths.size());
	}
	@Test
	public void testGetImage(){
		center = "32.058365,118.7964";
		keywordString = "大学";
		range = "0.001,0.001";
		UserCenterProcessor userCenterProcessor = new UserCenterProcessor();
		List<Point> points = userCenterProcessor.getPoints(center, range,
				keywordString);
		Point centerPoint=new Point(32.058365, 118.7964);
      List<Path> paths=userCenterProcessor.getPaths(points, centerPoint);
     BufferedImage image= userCenterProcessor.getImage(paths);
     assertNotNull(image);
	}

	private List<String> getNameList(List<Point> points) {
		List<String> names = new ArrayList<String>();
		for (Point p : points) {
			names.add(p.getName());
		}
		return names;
	}
}
