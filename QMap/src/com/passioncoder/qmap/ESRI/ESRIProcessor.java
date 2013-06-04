package com.passioncoder.qmap.ESRI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import com.bbn.openmap.layer.shape.ESRIPoly;
import com.bbn.openmap.layer.shape.ESRIPolygonRecord;
import com.bbn.openmap.layer.shape.ESRIRecord;
import com.bbn.openmap.layer.shape.ShapeFile;
import com.linuxense.javadbf.DBFReader;
import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.PointType;
import com.passioncoder.qmap.algorithm.Utilities;

public class ESRIProcessor {

	List<String> nameList = null;

	/**
	 * Get the list of the name of the places of the places in the shape file.
	 * 
	 * @param fileName
	 *            The name of the file
	 */
	private void getNameList(String fileName) {
		// this.nameList = new LinkedList<String>();
		// try {
		// DbfFile dbfFile = new DbfFile(new BinaryFile(fileName));
		// int rowCount = dbfFile.getRowCount();
		// assert (rowCount != 0);
		// for (int i = 0; i < rowCount; i++) {
		// List<Object> object = dbfFile.getRecordData(i);
		// for (Object tempData : object) {
		// // ByteArrayOutputStream bo = new ByteArrayOutputStream();
		// // ObjectOutputStream oo = new ObjectOutputStream(bo);
		// // oo.writeObject(object);
		// // byte[] bytes = bo.toByteArray();
		// // bo.close();
		// // oo.close();
		// String nameString = new String(tempData.toString()
		// .getBytes(), "utf-8");
		// this.nameList.add(nameString);
		// }
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// The list used to store all the names of the places
		this.nameList = new LinkedList<String>();
		try {
			// Create a DBFReader object to read the file of the common database
			// format
			DBFReader reader = new DBFReader(new FileInputStream(fileName));
			// Set the correct character encoding so that the reader can read
			// the content correctly
			reader.setCharactersetName("GB2312");
			Object[] objectArray = null;
			// The while loop is used to get all of the information in the dbf
			// file
			while ((objectArray = reader.nextRecord()) != null) {
				for (Object object : objectArray) {
					// Add the object read out of the file into the list
					this.nameList.add(object.toString());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Process the string for proper use.
	 * 
	 * @param fileName
	 *            The string needed to be processed.
	 * @param option
	 *            The processing option.
	 * @return The processed string.
	 */
	private String processString(String fileName, int option) {
		// Delete the suffix in the filename if the string ends with ".dbf" or
		// ".shp"
		if (fileName.endsWith(".dbf") || fileName.endsWith(".shp")) {
			fileName = fileName.substring(0, fileName.length() - 4);
		}
		// add the correct suffix to the filename according to the option
		switch (option) {
		case 0:
			fileName += ".shp";
			break;
		case 1:
			fileName += ".dbf";
			break;
		default:
			break;
		}
		return fileName;
	}

	/**
	 * parse the ESRI files
	 * 
	 * @param fileName
	 *            The name of the file needed to be parsed
	 * @return The graph object in presentation of the file containing the
	 *         geographic data
	 */
	public Graph<Point, Path> parseESRI(String fileName) {
		// get all the name of places in the dbf file
		getNameList(processString(fileName, 1));
		// read the shape file
		ShapeFile shapeFile = readShapeFile(processString(fileName, 0));
		// get all the ESRI record in the shape file
		List<ESRIRecord> recordList = getESRIRecord(shapeFile);
		List<Path> result = new LinkedList<Path>();
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		for (ESRIRecord record : recordList) {
			// read the shape file record only if the record is of the keyword
			// SHAPE_TYPE_POLYLINE
			if (record.getShapeType() == ESRIRecord.SHAPE_TYPE_POLYLINE) {
				// cast the record to the type ESRIPolygonRecord
				ESRIPolygonRecord polygonRecord = (ESRIPolygonRecord) record;
				// ESRIBoundingBox boundingBox = polygonRecord.getBoundingBox();
				// System.out.println(boundingBox.max.x+" "+boundingBox.max.y+" "+boundingBox.min.x+" "+boundingBox.min.y);
				ESRIPoly[] polys = polygonRecord.polygons;
				float[] pts = null;
				for (ESRIPoly poly : polys) {
					pts = ((ESRIPoly.ESRIFloatPoly) poly).getRadians();
					assert (pts != null);
					double[] tempArray = new double[pts.length];
					for (int i = 0; i < pts.length; i += 2) {
						double y = pts[i];
						double x = pts[i + 1];
						double longitude = x * 180 / Math.PI;
						double latitude = y * 180 / Math.PI;
						tempArray[i] = latitude;
						tempArray[i + 1] = longitude;
					}
					pts = ((ESRIPoly.ESRIFloatPoly) poly).getRadians();
					// get the start point and the end point in the record
					// rectangle and link them up with a path and add the path
					// to the graph
					Point firstPoint = new Point(tempArray[0], tempArray[1]);
					Point lastPoint = new Point(
							tempArray[tempArray.length - 2],
							tempArray[tempArray.length - 1]);
					firstPoint.setName(nameList.get(recordList.indexOf(record))
							.trim());
					lastPoint.setName(nameList.get(recordList.indexOf(record))
							.trim());
					firstPoint.setType(PointType.NON_KEY);
					lastPoint.setType(PointType.NON_KEY);
					// addIntoPath(result, firstPoint, lastPoint);
					Point tempPoint = null;
					if (null != (tempPoint = isPointContainedByGraph(graph,
							firstPoint))) {
						firstPoint = tempPoint;
					}
					if (null != (tempPoint = isPointContainedByGraph(graph,
							lastPoint))) {
						lastPoint = tempPoint;
					}
					firstPoint.setType(PointType.KEY);
					lastPoint.setType(PointType.KEY);
					if (firstPoint != lastPoint) {
						graph.addVertex(firstPoint);
						graph.addVertex(lastPoint);
						Path tempPath = graph.addEdge(firstPoint, lastPoint);
						if (tempPath != null) {
							tempPath.setPathID(1);
							tempPath.addPoint(firstPoint);
							tempPath.addPoint(lastPoint);
						}
					}
				}
			}
		}
		// do some further processing word later
		return parseGraph(graph);
	}

	/**
	 * Search the given path. Remove the redundancy points which has the same
	 * name.
	 * 
	 * @param graph
	 *            The given graph.
	 * @return The simplified graph.
	 */
	private Graph<Point, Path> parseGraph(Graph<Point, Path> graph) {
		// If some change has been made.
		boolean hasModified = true;
		while (hasModified) {
			hasModified = false;
			Set<Point> points = graph.vertexSet();
			for (Point point : points) {
				Set<Path> paths = graph.edgesOf(point);
				// Only check point which has two edges.
				if (paths.size() == 2) {
					List<Path> pathList = new ArrayList<Path>(paths);
					Path pathOne = pathList.get(0);
					Path pathTwo = pathList.get(1);
					Point source = graph.getEdgeSource(pathOne);
					Point target = graph.getEdgeTarget(pathOne);
					Point pointOne = (point == source) ? target : source;
					source = graph.getEdgeSource(pathTwo);
					target = graph.getEdgeTarget(pathTwo);
					Point pointTwo = (point == source) ? target : source;
					// If the name is same
					if (point.getName().equals(pointTwo.getName())
							&& point.getName().equals(pointOne.getName())) {
						graph.removeVertex(point);
						point.setType(PointType.NON_KEY);
						pathOne.join(pathTwo);
						graph.addEdge(pointOne, pointTwo, pathOne);
						hasModified = true;
						break;
					}
				}
			}
		}
		return graph;
	}

	/**
	 * Read a shape file using the openmap library
	 * 
	 * @param shapeFileName
	 *            The name of the file
	 * @return The shape file object in presentation of the shape file.
	 */
	private ShapeFile readShapeFile(String shapeFileName) {
		ShapeFile shapeFile = null;
		try {
			shapeFile = new ShapeFile(new File(shapeFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (shapeFile != null);
		return shapeFile;
	}

	/**
	 * Get all of the records in a shape file
	 * 
	 * @param shapeFile
	 *            The shape file we needed to get information from.
	 * 
	 * @return A list containing all of the records in the shape file.
	 */
	private List<ESRIRecord> getESRIRecord(ShapeFile shapeFile) {
		List<ESRIRecord> recordList = new LinkedList<ESRIRecord>();
		ESRIRecord tempRecord = null;
		try {
			while ((tempRecord = shapeFile.getNextRecord()) != null) {
				recordList.add(tempRecord);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (recordList.size() != 0);
		return recordList;
	}

	/**
	 * The method returns true if the point is already in the graph and false if
	 * the point is not in the graph object.
	 * 
	 * @param graph
	 *            The container object.
	 * @param point
	 *            The point needed to be checked.
	 * @return The boolean value in presentation of whether the point is already
	 *         contained in the graph.
	 */
	private Point isPointContainedByGraph(Graph<Point, Path> graph, Point point) {
		Set<Point> pointSet = graph.vertexSet();
		for (Point tempPoint : pointSet) {
			if (isTwoPointsTheSame(point, tempPoint)) {
				return tempPoint;
			}
		}
		return null;
	}

	/**
	 * The method checks whether the two points are of the same location
	 * 
	 * @param point1
	 *            The first point in the two points
	 * @param point2
	 *            The second point in the two ponits
	 * @return The boolean value in presentation of whether the two points are
	 *         of the same location.
	 */
	private boolean isTwoPointsTheSame(Point point1, Point point2) {
		if (Utilities.pointToPointDistance(point1, point2) < 0.07) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Parse the given path list to a graph.
	 * 
	 * @param paths
	 *            The given path
	 * @return The generated graph.
	 */
	private Graph<Point, Path> toGraph(List<Path> paths) {
		Graph<Point, Path> graph = new SimpleGraph<Point, Path>(Path.class);
		for (Path path : paths) {
			Point startPoint = path.getStartPoint();
			Point endPoint = path.getEndPoint();
			startPoint.setType(PointType.KEY);
			endPoint.setType(PointType.KEY);
			if (startPoint != endPoint) {
				graph.addVertex(startPoint);
				graph.addVertex(endPoint);
				path.setPathID(1);
				graph.addEdge(startPoint, endPoint, path);
			} else {
				// If has cycle. Split the cycle into two parts.
				List<Point> points = path.getPoints();
				int innerIndex = points.size() / 2;
				Point innerPoint = points.get(innerIndex);
				innerPoint.setType(PointType.KEY);
				graph.addVertex(startPoint);
				graph.addVertex(endPoint);
				graph.addVertex(innerPoint);
				Path firstPart = new Path();
				firstPart.setPathID(1);
				for (int i = 0; i <= innerIndex; i++) {
					firstPart.addPoint(points.get(i));
				}
				Path secondPart = new Path();
				secondPart.setPathID(1);
				for (int i = innerIndex; i < points.size(); i++) {
					secondPart.addPoint(points.get(i));
				}
				graph.addEdge(startPoint, innerPoint, firstPart);
				graph.addEdge(innerPoint, endPoint, secondPart);
			}
		}
		return mergePoints(graph);
	}

	/**
	 * 
	 * @author lidejia
	 * 
	 *         Inner class which contains a point and a path.
	 * 
	 */
	private class Pair {
		public Point point;
		public Path path;

		public Pair(Point point, Path path) {
			this.point = point;
			this.path = path;
		}
	}

	/**
	 * Check if a point has already existed in the graph with the same position.
	 * 
	 * @param map
	 *            The source graph.
	 * @param point
	 *            The point to be checked.
	 * @return The original point, or the already existed point which the same
	 *         position.
	 */
	private Point getRealPointAtHere(Graph<Point, Path> map, Point point) {
		Set<Point> points = map.vertexSet();
		for (Point current : points) {
			if (Utilities.pointToPointDistance(current, point) < 0.01) {
				if (point.getType() == PointType.KEY) {
					current.setType(point.getType());
					current.setName(point.getName());
				}
				return current;
			}
		}
		return point;
	}

	/**
	 * Merge the points if they are too crowd.
	 * 
	 * @param graph
	 *            The source graph.
	 * @return The generated graph.
	 */
	private Graph<Point, Path> mergePoints(Graph<Point, Path> graph) {
		Graph<Point, Path> result = new SimpleGraph<Point, Path>(Path.class);
		Set<Point> points = graph.vertexSet();
		for (Point point : points) {
			Point realPoint = getRealPointAtHere(result, point);
			result.addVertex(realPoint);
			Set<Path> paths = graph.edgesOf(point);
			for (Path path : paths) {
				Point source = graph.getEdgeSource(path);
				Point target = graph.getEdgeTarget(path);
				Point next = (point == source) ? target : source;
				Point realNext = getRealPointAtHere(result, next);
				result.addVertex(realNext);
				if (realPoint != realNext) {
					result.addEdge(realPoint, realNext, path);
				}
			}
		}
		return result;

	}

	/**
	 * Add the path to the path list. The function deal with merging.
	 * 
	 * @param result
	 *            The result path list.
	 * @param start
	 *            The start point of the path.
	 * @param end
	 *            The end point of the path.
	 */
	private void addIntoPath(List<Path> result, Point start, Point end) {

		Pair startPair = pointAlreadyInList(result, start);
		Pair endPair = pointAlreadyInList(result, end);
		// Both points are not in the path list. Add them.
		if (startPair == null && endPair == null) {
			Path path = new Path();
			path.addPoint(start);
			path.addPoint(end);
			result.add(path);
		} else if (startPair == null || endPair == null) {
			// One of them is in. Find it.
			Pair nonNullPair = (startPair == null) ? endPair : startPair;
			Point existedPoint = nonNullPair.point;
			Point mergedPoint = (nonNullPair == startPair) ? start : end;
			Point unExistedPoint = (nonNullPair != startPair) ? start : end;
			// If counter > 0, we do not need check, add the path.
			if (existedPoint.getConnectionCounter() > 0) {
				Path path = new Path();
				path.addPoint(existedPoint);
				path.addPoint(unExistedPoint);
				result.add(path);
			} else {
				// if the two points' name are the same, add the point to the
				// path.
				if (mergedPoint.getName().equals(existedPoint.getName())) {
					Path path = nonNullPair.path;
					List<Point> points = path.getPoints();
					if (existedPoint == path.getStartPoint()) {
						points.add(0, unExistedPoint);
					} else {
						points.add(unExistedPoint);
					}
				} else {
					existedPoint.increaseConnectionCounter();
					Path path = new Path();
					path.addPoint(existedPoint);
					path.addPoint(unExistedPoint);
					result.add(path);
				}
			}
		} else {
			// Both points are in the graph.
			Point startPairPoint = startPair.point;
			Point endPairPoint = endPair.point;
			int startPairCounter = startPairPoint.getConnectionCounter();
			int endPairCounter = endPairPoint.getConnectionCounter();
			if (startPairCounter > 0 && endPairCounter > 0) {
				Path path = new Path();
				path.addPoint(startPairPoint);
				path.addPoint(endPairPoint);
				result.add(path);
			} else if (startPairCounter > 0 || endPairCounter > 0) {
				Point checkedExistedPoint = (startPairCounter > 0) ? endPairPoint
						: startPairPoint;
				Point checkMergedPoint = (checkedExistedPoint == startPairPoint) ? start
						: end;
				Point uncheckedPoint = (checkMergedPoint == start) ? endPairPoint
						: startPairPoint;
				Path checkedPath = (checkedExistedPoint == startPairPoint) ? startPair.path
						: endPair.path;
				if (checkedExistedPoint.getName().equals(
						checkMergedPoint.getName())) {
					List<Point> points = checkedPath.getPoints();
					if (checkedExistedPoint == checkedPath.getStartPoint()) {
						points.add(0, uncheckedPoint);
					} else {
						points.add(uncheckedPoint);
					}
				} else {
					checkedExistedPoint.increaseConnectionCounter();
					Path path = new Path();
					path.addPoint(startPairPoint);
					path.addPoint(endPairPoint);
					result.add(path);
				}
			} else {
				if (startPairPoint.getName().equals(start.getName())
						&& endPairPoint.getName().equals(end.getName())) {
					result.remove(startPair.path);
					result.remove(endPair.path);
					Path path = new Path();
					if (startPairPoint == startPair.path.getStartPoint()) {
						Collections.reverse(startPair.path.getPoints());
					}
					if (endPairPoint == endPair.path.getEndPoint()) {
						Collections.reverse(endPair.path.getPoints());
					}
					for (Point point : startPair.path.getPoints()) {
						path.addPoint(point);
					}
					for (Point point : endPair.path.getPoints()) {
						path.addPoint(point);
					}
					result.add(path);
				} else if (startPairPoint.getName().equals(start.getName())
						|| endPairPoint.getName().equals(end.getName())) {
					Point mergedPoint = startPairPoint.getName().equals(
							start.getName()) ? startPairPoint : endPairPoint;
					Point unMergedPoint = (mergedPoint == startPairPoint) ? endPairPoint
							: startPairPoint;
					Path mergedPath = (mergedPoint == startPairPoint) ? startPair.path
							: endPair.path;
					unMergedPoint.increaseConnectionCounter();
					List<Point> points = mergedPath.getPoints();
					if (mergedPoint == mergedPath.getStartPoint()) {
						points.add(0, unMergedPoint);
					} else {
						points.add(unMergedPoint);
					}
				} else {
					Path path = new Path();
					startPairPoint.increaseConnectionCounter();
					endPairPoint.increaseConnectionCounter();
					path.addPoint(startPairPoint);
					path.addPoint(endPairPoint);
					result.add(path);
				}
			}
		}

	}

	/**
	 * Check if the point is already in the path list. If so, return the already
	 * existed point and the path. If the already point is in the middle of a
	 * path, then split the path into two parts.
	 * 
	 * @param paths
	 *            The path list to search in.
	 * @param point
	 *            The point to check.
	 * @return The result "Pair" structure.
	 */
	private Pair pointAlreadyInList(List<Path> paths, Point point) {
		List<Path> copyPaths = new LinkedList<Path>(paths);
		for (Path path : copyPaths) {
			List<Point> points = path.getPoints();
			for (Point testPoint : points) {
				if (isPointAlmostSame(testPoint, point)) {
					int indexOfPoint = points.indexOf(testPoint);
					if (indexOfPoint != 0 && indexOfPoint != points.size() - 1) {
						Path firstPart = new Path();
						for (int i = 0; i <= indexOfPoint; i++) {
							firstPart.addPoint(points.get(i));
						}
						Path secondPart = new Path();
						for (int i = indexOfPoint; i < points.size(); i++) {
							secondPart.addPoint(points.get(i));
						}
						paths.remove(path);
						paths.add(firstPart);
						paths.add(secondPart);
						testPoint.setType(PointType.KEY);
						testPoint.increaseConnectionCounter();
						return new Pair(testPoint, firstPart);
					} else {
						return new Pair(testPoint, path);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Check if two points are same in position.
	 * 
	 * @param one
	 *            The point.
	 * @param two
	 *            Another point.
	 * @return True of False
	 */
	private boolean isPointAlmostSame(Point one, Point two) {
		return Utilities.pointToPointDistance(one, two) < 0.02;
	}

}
