package com.passioncoder.qmap.test.ESRI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import javax.imageio.ImageIO;
import org.jgrapht.Graph;
import org.junit.Test;
import com.bbn.openmap.layer.shape.ESRIBoundingBox;
import com.bbn.openmap.layer.shape.ESRIPoly;
import com.bbn.openmap.layer.shape.ESRIPolygonRecord;
import com.bbn.openmap.layer.shape.ESRIRecord;
import com.bbn.openmap.layer.shape.ShapeFile;
import com.linuxense.javadbf.DBFReader;
import com.passioncoder.qmap.ESRI.ESRIProcessor;
import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.SchematicMapGenerator;
import com.passioncoder.qmap.drawing.DrawingGenerator;
import static org.junit.Assert.*;

public class ESRIProcessorTest {

	/**
	 * The method tests that whether the method named getNameList can get the
	 * name list of the places correctly.
	 */
	@Test
	public void testGetNameList() {
		String fileName = "WebContent/resources/gxnj/2_polyline.dbf";
		ESRIProcessor tempProcessor = new ESRIProcessor();
		try {
			Method testMethod = ESRIProcessor.class.getMethod("getNameList",
					String.class);
			testMethod.setAccessible(true);
			testMethod.invoke(tempProcessor, fileName);
			Field field = ESRIProcessor.class.getField("nameList");
			List<String> nameList = (List<String>) field.get(tempProcessor);
			assertTrue(nameList.size() > 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The test method can only be run when the tested function is set to be
	 * public.
	 */
	@Test
	public void testReadShapeFile() {
		String fileName = "WebContent/resources/gxnj/二级道路_polyline.shp";
		ESRIProcessor tempProcessor = new ESRIProcessor();
		Method testMethod;
		try {
			testMethod = ESRIProcessor.class.getMethod("readShapeFile",
					String.class);
			testMethod.setAccessible(true);
			ShapeFile shapeFile = (ShapeFile) testMethod.invoke(tempProcessor,
					fileName);
			assertTrue(shapeFile != null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The test method can only be run when the tested function is set to be
	 * public.
	 */
	@Test
	public void testGetESRIRecord() {
		String fileName = "WebContent/resources/gxnj/二级道路_polyline.shp";
		ESRIProcessor tempProcessor = new ESRIProcessor();
		try {
			Method testMethod = ESRIProcessor.class.getMethod("readShapeFile",
					void.class);
			testMethod.setAccessible(true);
			ShapeFile shapeFile = (ShapeFile) testMethod.invoke(tempProcessor,
					fileName);
			assertTrue(shapeFile != null);
			Method testMethod2 = ESRIProcessor.class.getMethod("getESRIRecord",
					ShapeFile.class);

			List<ESRIRecord> recordList = (List<ESRIRecord>) testMethod2
					.invoke(tempProcessor, shapeFile);
			System.out.println(recordList.size());
			for (ESRIRecord record : recordList) {
				if (record.getShapeType() == ESRIRecord.SHAPE_TYPE_POLYLINE) {
					System.out.println(record.getClass().toString());
					ESRIPolygonRecord polygonRecord = (ESRIPolygonRecord) record;
					ESRIBoundingBox boundingBox = polygonRecord
							.getBoundingBox();
					System.out.println(boundingBox.max.x + " "
							+ boundingBox.max.y + " " + boundingBox.min.x + " "
							+ boundingBox.min.y);
					ESRIPoly[] polys = polygonRecord.polygons;
					float[] pts = null;
					for (ESRIPoly poly : polys) {
						pts = ((ESRIPoly.ESRIFloatPoly) poly).getRadians();
						assertTrue(pts != null);
						// System.out.println(pts.length);
						// x =
						// (longitude-longitude0)*(6378137*pi/180)*cos(latitude0*pi/180)
						// y = (latitude-latitude0)*(6378137*pi/180)
						for (int i = 0; i < pts.length; i += 2) {
							double x = pts[i];
							double y = pts[i + 1];
							// double longitude0=boundingBox.min.x;
							// double latitude0=boundingBox.min.y;
							// double
							// longitude=x/(6378137*Math.PI/180)/Math.cos(latitude0*Math.PI/180)+longitude0;
							// double
							// latitude=y/(6378137*Math.PI/180)+latitude0;
							double longitude = x * 180 / Math.PI;
							double latitude = y * 180 / Math.PI;
							System.out.println(longitude + " " + latitude);
						}
						System.out.println();
					}
				}
			}
			assertTrue(recordList.size() != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * The method tests whether the function can read a dbf file correctly.
	 */
	@Test
	public void testReadDBFFile() {
		String fileName = "WebContent/resources/gxnj/2_polyline.dbf";
		// try {
		// DbfFile dbfFile = new DbfFile(new BinaryFile(fileName));
		// int rowCount = dbfFile.getRowCount();
		// System.out.println(rowCount);
		// for (int i = 0; i < rowCount; i++) {
		// List<Object> object = dbfFile.getRecordData(i);
		// for (Object tempData : object) {
		// String nameString = tempData.toString();
		// nameString = new String(nameString.getBytes("utf-8"),
		// "utf-8");
		// System.out.println(nameString);
		// }
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			DBFReader reader = new DBFReader(new FileInputStream(fileName));
			reader.setCharactersetName("GB2312");
			Object[] objectArray = null;
			while ((objectArray = reader.nextRecord()) != null) {
				System.out.println(objectArray.length);
				System.out.println(objectArray[0].toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The method tests whether the function can read a dbf file correctly.
	 */
	@Test
	public void testReadDBFFile1() {
		String fileName = "WebContent/resources/gxnj/2_polyline.dbf";
		try {
			DBFReader reader = new DBFReader(new FileInputStream(fileName));
			reader.setCharactersetName("GB2312");
			Object[] objectArray = null;
			while ((objectArray = reader.nextRecord()) != null) {
				System.out.println(objectArray.length);
				System.out.println(objectArray[0].toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The method tests whether the function can read a dbf file correctly.
	 */
	@Test
	public void testReadDBFFile2() {
		String fileName = "WebContent/resources/gxnj/2_polyline.dbf";
		try {
			DBFReader reader = new DBFReader(new FileInputStream(fileName));
			reader.setCharactersetName("GB2312");
			Object[] objectArray = null;
			while ((objectArray = reader.nextRecord()) != null) {
				System.out.println(objectArray.length);
				System.out.println(objectArray[0].toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The method tests whether the function named parseESRI can parse a shp
	 * file correctly.
	 */
	@Test
	public void testParseESRI() {
		String fileName = "WebContent/resources/gxnj/2_polyline.shp";
		ESRIProcessor processor = new ESRIProcessor();
		Graph<Point, Path> graph = processor.parseESRI(fileName);
		SchematicMapGenerator generator = new SchematicMapGenerator();
		graph = generator.getSchematicMap(graph);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("/home/lidejia/222.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The method tests whether the function named parseESRI can parse a shp
	 * file correctly.
	 */
	@Test
	public void testParseESRI1() {
		String fileName = "WebContent/resources/gxnj/1_polyline.shp";
		ESRIProcessor processor = new ESRIProcessor();
		Graph<Point, Path> graph = processor.parseESRI(fileName);
		SchematicMapGenerator generator = new SchematicMapGenerator();
		graph = generator.getSchematicMap(graph);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("/home/lidejia/1.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The method tests whether the function named parseESRI can parse a shp
	 * file correctly.
	 */
	@Test
	public void testParseESRI2() {

		String fileName = "WebContent/resources/gxnj/3_polyline.shp";
		ESRIProcessor processor = new ESRIProcessor();
		Graph<Point, Path> graph = processor.parseESRI(fileName);
		SchematicMapGenerator generator = new SchematicMapGenerator();
		graph = generator.getSchematicMap(graph);
		DrawingGenerator test = new DrawingGenerator();
		BufferedImage bufferedImage = test.generateImage(graph);
		File imageFile = new File("/home/lidejia/3.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
