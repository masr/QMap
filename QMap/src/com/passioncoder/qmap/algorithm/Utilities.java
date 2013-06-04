package com.passioncoder.qmap.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;

public class Utilities {

    /**
     * Calculate the distance of a point to a line.
     *
     * @param point
     *            The given point.
     * @param lineStart
     *            The start point of the line.
     * @param lineEnd
     *            The end point of the line.
     * @return The distance.
     */
    public static double getPointToLineDistance(Point point, Point lineStart,
            Point lineEnd) {
        double point_start_square = Math.pow(
                point.getLatitude() - lineStart.getLatitude(), 2)
                + Math.pow(point.getLongitude() - lineStart.getLongitude(), 2);
        double start_end_square = Math.pow(
                (lineEnd.getLatitude() - lineStart.getLatitude())
                * (point.getLatitude() - lineStart.getLatitude())
                + (lineEnd.getLongitude() - lineStart.getLongitude())
                * (point.getLongitude() - lineStart.getLongitude()), 2)
                / (Math.pow(lineEnd.getLatitude() - lineStart.getLatitude(), 2) + Math.pow(lineEnd.getLongitude() - lineStart.getLongitude(),
                2));
        return Math.sqrt(point_start_square - start_end_square);
    }

    /**
     * Calculate the distance between the given two points.
     *
     * @param from
     *            The one point.
     * @param to
     *            The another point.
     * @return The distance.
     */
    public static double pointToPointDistance(Point from, Point to) {
        return Math.sqrt(Math.pow(from.getLatitude() - to.getLatitude(), 2)
                + Math.pow(to.getLongitude() - from.getLongitude(), 2));
    }

    /**
     * The recursion method is used by method "movePoint".
     *
     * @param map
     *            The source map
     * @param alreadyMoved
     *            The points that have already been moved. Used to avoid
     *            infinite loop caused by cycle.
     * @param toward
     *            The point that the recursion comes from.
     *
     * @param moved
     *            The point to be moved.
     * @param delta_lat
     *            The latitude to be moved.
     * @param delta_lon
     *            The longitude to be move.
     */
    private static void movePointImp(Graph<Point, Path> map,
            List<Point> alreadyMoved, Point toward, Point moved,
            double delta_lat, double delta_lon) {
        if (alreadyMoved.contains(moved)) {
            // If the point has been moved, then stop. If useful when cycle
            // exists.
            return;
        }
        moved.moveBy(delta_lat, delta_lon);
        alreadyMoved.add(moved);
        // Move the adjacent point.
        Set<Path> paths = map.edgesOf(moved);
        for (Path path : paths) {
            Point source = map.getEdgeSource(path);
            Point target = map.getEdgeTarget(path);
            if (source != toward && target != toward) {
                Point next = (moved == source) ? target : source;
                movePointImp(map, alreadyMoved, moved, next, delta_lat,
                        delta_lon);
            }
        }
    }

    /**
     * Move the given point by the given distance.
     *
     * @param map
     * @param toward
     * @param moved
     * @param delta_lat
     * @param delta_lon
     */
    public static void movePoint(Graph<Point, Path> map, Point toward,
            Point moved, double delta_lat, double delta_lon) {
        List<Point> alreadyMoved = new LinkedList<Point>();
        alreadyMoved.add(toward);
        movePointImp(map, alreadyMoved, toward, moved, delta_lat, delta_lon);
    }

    /**
     * Calculate the angle of two given edges.
     *
     * @param angle
     *            The angle point.
     * @param one
     *            The point of one edge.
     * @param two
     *            The point of another edge.
     * @return The degree
     */
    public static double caculateAngleDegree(Point angle, Point one, Point two) {
        double vector_one_x = one.getLatitude() - angle.getLatitude();
        double vector_one_y = one.getLongitude() - angle.getLongitude();
        double vector_two_x = two.getLatitude() - angle.getLatitude();
        double vector_two_y = two.getLongitude() - angle.getLongitude();
        double degree = (vector_one_x * vector_two_x + vector_one_y
                * vector_two_y)
                / (Math.sqrt(Math.pow(vector_one_x, 2)
                + Math.pow(vector_one_y, 2)) * Math.sqrt(Math.pow(
                vector_two_x, 2) + Math.pow(vector_two_y, 2)));
        return degree;
    }
}
