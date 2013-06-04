package com.passioncoder.qmap.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

public class SchematicMapGenerator {

	private static final double TOLERANCE_FACTOR = 0.15;
	private static final double COS_45 = Math.cos(1.0 / 4 * Math.PI);
	private static final double DISTANCE_FACTOR = 0.04;
	private static final double SAME_POINT_DISTANCE = 0.0005;

	/**
	 * Simplify the path(using recursion).
	 * 
	 * @param path
	 *            The path to be simplified.
	 * @param start
	 *            The start point in the path.
	 * @param end
	 *            The end point in the path.
	 * @return The simplified path.
	 */
	private Path simplifyPath(Path path, Point start, Point end) {
		double vector_x = start.getLatitude() - end.getLatitude();
		double vector_y = end.getLongitude() - start.getLongitude();
		// Calculate the tolerance based on the length of the path.
		double tolerance = Math.sqrt(Math.pow(vector_x, 2)
				+ Math.pow(vector_y, 2))
				* TOLERANCE_FACTOR;
		List<Point> points = path.getPoints();
		int start_index = points.indexOf(start);
		int end_index = points.indexOf(end);
		// Calculate the max distance.
		double max_distance = 0.0;
		int max_index = 0;
		for (int i = start_index + 1; i < end_index; i++) {
			double temp_distance = Utilities.getPointToLineDistance(
					points.get(i), start, end);
			if (temp_distance > max_distance) {
				max_distance = temp_distance;
				max_index = i;
			}
		}
		if (max_distance > tolerance) {
			// If the distance is larger than the tolerance, then split the path
			// in two parts and check each again.
			Path one = simplifyPath(path, start, points.get(max_index));
			Path two = simplifyPath(path, points.get(max_index), end);
			one.join(two);
			return one;
		} else {
			// If the distance is smaller than the tolerance, then return the
			// new path that begin at the start point and end at the end point.
			Path resultPath = new Path();
			resultPath.addPoint(start);
			resultPath.addPoint(end);
			return resultPath;
		}
	}

	/**
	 * Simplify the given map.
	 * 
	 * @param map
	 *            The map to be simplified.
	 */
	private void simplifyMap(Graph<Point, Path> map) {
		Set<Path> paths = map.edgeSet();
		for (Path path : paths) {
			Path simple = simplifyPath(path, path.getStartPoint(),
					path.getEndPoint());
			path.replacePoints(simple);
		}
	}

	/**
	 * Remove the sharp angle in the map if necessary.
	 * 
	 * @param map
	 *            The map to be modified.
	 */
	private void removeSharpCorner(Graph<Point, Path> map) {
		// Check within the two key point.
		Set<Path> paths = map.edgeSet();
		for (Path path : paths) {
			List<Point> points = path.getPoints();
			for (int i = 1; i < points.size() - 1; i++) {
				Point one = points.get(i - 1);
				Point angle = points.get(i);
				Point two = points.get(i + 1);
				double degree = Utilities.caculateAngleDegree(angle, one, two);
				if (degree > COS_45) {
					points.remove(i);
					i--;
				}
			}
			if (points.size() < 2) {
				System.out.println("ERROR");
			}
		}
		// Check cross the key point.
		Set<Point> points = map.vertexSet();
		// Check all key point.
		for (Point point : points) {
			paths = map.edgesOf(point);
			// Test only if the point has two or more edges.
			if (paths.size() >= 2) {
				for (Path pathOne : paths) {
					for (Path pathTwo : paths) {
						if (pathOne != pathTwo) {
							List<Point> pathOnePoints = pathOne.getPoints();
							List<Point> pathTwoPoints = pathTwo.getPoints();
							if (pathOnePoints.size() < 2
									|| pathTwoPoints.size() < 2) {
								System.out.println("ERROR");
							}
							Point point_one = (point == pathOne.getStartPoint()) ? pathOnePoints
									.get(1) : pathOnePoints.get(pathOnePoints
									.size() - 2);
							Point point_two = (point == pathTwo.getStartPoint()) ? pathTwoPoints
									.get(1) : pathTwoPoints.get(pathTwoPoints
									.size() - 2);
							double angle = Utilities.caculateAngleDegree(point,
									point_one, point_two);
							if (angle > COS_45) {
								// If the point is not the key point, we can
								// remove it.
								if (point_one.getType() == PointType.NON_KEY) {
									pathOnePoints.remove(point_one);
								}
								if (point_two.getType() == PointType.NON_KEY) {
									pathTwoPoints.remove(point_two);
								}
							}

						}
					}
				}
			}
		}
	}

	/**
	 * Change the map's structure to satisfy the following steps.
	 * 
	 * @param map
	 *            The map to be parsed.
	 * @return The parsed map.
	 */
	private Graph<Point, Path> parseMapStructure(Graph<Point, Path> map) {
		Set<Path> paths = map.edgeSet();
		Graph<Point, Path> result = new SimpleGraph<Point, Path>(Path.class);
		for (Path path : paths) {
			int pathID = path.getPathID();
			double distance = path.getDistance();
			List<Point> points = path.getPoints();
			for (int i = 1; i < points.size(); i++) {
				Point start = points.get(i - 1);
				Point end = points.get(i);
				result.addVertex(start);
				result.addVertex(end);
				Path edge = result.addEdge(start, end);
				if (edge != null) {
					edge.setPathID(pathID);
					edge.setDistance(distance);
					edge.addPoint(start);
					edge.addPoint(end);
				}
			}
		}
		return result;
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
	 * Adjust the map's path length. So that the schematic map's path is neither
	 * so short nor so long. The method relocate the points in the map to a grid
	 * with the original topology reserved.
	 * 
	 * @param map
	 */
	private void adjustPathLength(Graph<Point, Path> map) {
		List<Point> longitudePoints = new ArrayList<Point>(map.vertexSet());
		List<Point> latitudePoints = new ArrayList<Point>(map.vertexSet());
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

		// Relocate the points to a gird.
		latitudePoints.get(0).setXPosition(0);
		int latitudeCounter = 0;
		for (int i = 1; i < latitudePoints.size(); i++) {
			Point point = latitudePoints.get(i);
			Point lastPoint = latitudePoints.get(i - 1);
			// Calculate the tolerance based on the distance of the two point.
			double tolerance = Utilities.pointToPointDistance(point, lastPoint)
					* DISTANCE_FACTOR;
			if (Math.abs(point.getLatitude() - lastPoint.getLatitude()) > tolerance) {
				latitudeCounter++;
			}
			point.setXPosition(latitudeCounter);
		}

		// When two points are almost in the same place, If the two points are
		// both key points, then they are separated. If one of them is non-key
		// point, the the point is replaced by the other.
		Point lastPoint = longitudePoints.get(0);
		lastPoint.setYPosition(0);
		int longitudeCounter = 0;
		for (int i = 1; i < longitudePoints.size(); i++) {
			Point currentPoint = longitudePoints.get(i);
			double tolerance = Utilities.pointToPointDistance(currentPoint,
					lastPoint) * DISTANCE_FACTOR;
			if (Math.abs(currentPoint.getLongitude() - lastPoint.getLongitude()) > tolerance) {
				longitudeCounter++;
				lastPoint = currentPoint;
				currentPoint.setYPosition(longitudeCounter);
			} else {
				if (currentPoint.getXPosition() == lastPoint.getXPosition()) {
					if (lastPoint.getType() == PointType.NON_KEY) {
						Set<Path> pathes = map.edgesOf(lastPoint);
						for (Path path : pathes) {
							Point source = map.getEdgeSource(path);
							Point target = map.getEdgeTarget(path);
							Point next = (lastPoint == source) ? target
									: source;
							if (next != currentPoint) {
								Path temp = map.addEdge(next, currentPoint);
								if (temp != null) {
									temp.setPathID(path.getPathID());
								}
							}
						}
						map.removeVertex(lastPoint);
						lastPoint = currentPoint;
						currentPoint.setYPosition(longitudeCounter);
					} else if (currentPoint.getType() == PointType.KEY
							|| currentPoint.getType() == PointType.USER) {
						longitudeCounter++;
						lastPoint = currentPoint;
						currentPoint.setYPosition(longitudeCounter);
					} else {
						Set<Path> pathes = map.edgesOf(currentPoint);
						for (Path path : pathes) {
							Point source = map.getEdgeSource(path);
							Point target = map.getEdgeTarget(path);
							Point next = (currentPoint == source) ? target
									: source;
							if (next != lastPoint) {
								Path temp = map.addEdge(next, lastPoint);
								if (temp != null) {
									temp.setPathID(path.getPathID());
								}
							}
						}
						map.removeVertex(currentPoint);
					}
				} else {
					lastPoint = currentPoint;
					currentPoint.setYPosition(longitudeCounter);
				}
			}
		}
		transformMap(map);
	}

	/**
	 * Transform xPosition to latitude and yPosition to longitude.
	 * 
	 * @param map
	 */
	private void transformMap(Graph<Point, Path> map) {
		Set<Point> points = map.vertexSet();
		for (Point point : points) {
			point.setLatitude(point.getXPosition());
			point.setLongitude(point.getYPosition());
		}
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
		if (Math.abs(source - target) < 0.005) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if a point with the same position is already in the map. If such
	 * point not exists, return the given point. Or if the give point is a key
	 * point, replace with the given point.
	 * 
	 * 
	 * @param map
	 *            The map which is checked in.
	 * @param point
	 *            The point to be checked.
	 * @return The result point.
	 */
	private Point getRealPointAtHere(Graph<Point, Path> map, Point point) {
		Set<Point> points = map.vertexSet();
		for (Point current : points) {
			if (Utilities.pointToPointDistance(current, point) < SAME_POINT_DISTANCE) {
				if (point.getType() == PointType.USER
						|| point.getType() == PointType.KEY) {
					current.setType(point.getType());
					current.setName(point.getName());
				}
				return current;
			}
		}
		return point;
	}

	/**
	 * Adjust the direction of the path in the map so that they are 0, 45, or 90
	 * degree.
	 * 
	 * @param map
	 *            The given map to be adjusted.
	 * @return The result map.
	 */
	private Graph<Point, Path> adjustAngle(Graph<Point, Path> map) {
		Graph<Point, Path> result = new SimpleGraph<Point, Path>(Path.class);
		Set<Path> pathes = map.edgeSet();
		for (Path path : pathes) {
			Point source = map.getEdgeSource(path);
			Point target = map.getEdgeTarget(path);
			Point temp_source = getRealPointAtHere(result, source);
			Point temp_target = getRealPointAtHere(result, target);
			// If both point has already in the map, we do not need to check
			// them.
			if (temp_source != source && target != temp_target) {
				continue;
			}
			source = temp_source;
			target = temp_target;
			double angleDegree = getAngle(source, target);
			result.addVertex(source);
			result.addVertex(target);
			if (!(angleEqual(COS_45, angleDegree) || angleEqual(1, angleDegree)
					|| angleEqual(-1, angleDegree) || angleEqual(-COS_45,
					angleDegree))) {
				double delta_latitude = target.getLatitude()
						- source.getLatitude();
				double delta_longitude = target.getLongitude()
						- source.getLongitude();
				double move_distance = Math.min(Math.abs(delta_longitude),
						Math.abs(delta_latitude));
				delta_latitude = delta_latitude > 0 ? move_distance
						: -move_distance;
				delta_longitude = delta_longitude > 0 ? move_distance
						: -move_distance;
				Point innerPoint = new Point(source.getLatitude()
						+ delta_latitude, source.getLongitude()
						+ delta_longitude);
				innerPoint = getRealPointAtHere(result, innerPoint);
				result.addVertex(innerPoint);
				if (innerPoint != source) {
					Path temp = result.addEdge(source, innerPoint);
					if (temp != null) {
						temp.setPathID(path.getPathID());
					}
				}
				if (innerPoint != target) {
					Path temp = result.addEdge(target, innerPoint);
					if (temp != null) {
						temp.setPathID(path.getPathID());
					}
				}
			} else {
				Path temp = result.addEdge(target, source);
				if (temp != null) {
					temp.setPathID(path.getPathID());
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param map
	 *            The source map.
	 * 
	 * @return The schematic map represent in a graph.
	 */
	public Graph<Point, Path> getSchematicMap(Graph<Point, Path> map) {
		 simplifyMap(map);
		 removeSharpCorner(map);
		map = parseMapStructure(map);
		 adjustPathLength(map);
		 map = adjustAngle(map);
		return map;
	}
}
