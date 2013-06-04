package com.passioncoder.qmap.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Set;
import org.jgrapht.Graph;
import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.PointType;

public class DrawingGenerator {

	/**
	 * Drawing the schematic map used in the mobile phone and return the image
	 * to the servlet
	 * 
	 * @param graph
	 *            The data structure of the schematic map from the algorithm
	 * 
	 * @return The generated buffered image object
	 */
	public BufferedImage generateMobileImage(Graph<Point, Path> graph) {
		// do the pre-processing work to the graph
		DrawingPreProcessor drawingPreprocessor = new DrawingPreProcessor();
		drawingPreprocessor.setDrawingCoordinate(graph);
		// get the width and height of the drawing
		int imageWidth = (int) (Drawing.actualXSize + 2 * Drawing.xPadding);
		int imageHeight = (int) (Drawing.actualYSize + 2 * Drawing.yPadding);
		// create a buffered image of the drawing
		BufferedImage bufferedImage = new BufferedImage(imageWidth,
				imageHeight, BufferedImage.TYPE_INT_RGB);
		// get the graphic drawing context
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		g2.setBackground(Drawing.backgroundColor);
		g2.clearRect(0, 0, imageWidth, imageHeight);
		g2.setPaint(Color.BLACK);
		// draw the background of the image
		drawBackground(g2);
		// draw all the paths in the image
		drawPath(g2, graph);
		// draw all the points in the image
		drawPoint(g2, graph);
		// draw the title in the image
		drawTitle(g2);
		return bufferedImage;
	}

	/**
	 * Drawing the schematic map and return the generated image to servlet
	 * 
	 * @param graph
	 *            The data structure of the schematic map from the algorithm
	 * 
	 * @return The generated buffered image object
	 */
	public BufferedImage generateImage(Graph<Point, Path> graph) {
		// do the pre-processing work to the graph
		DrawingPreProcessor drawingPreprocessor = new DrawingPreProcessor();
		drawingPreprocessor.setDrawingCoordinate(graph);
		// get the width and height of the drawing
		int imageWidth = (int) (Drawing.actualXSize + 2 * Drawing.xPadding);
		int imageHeight = (int) (Drawing.actualYSize + 2 * Drawing.yPadding);
		// create a buffered image of the drawing
		BufferedImage bufferedImage = new BufferedImage(imageWidth,
				imageHeight, BufferedImage.TYPE_INT_RGB);
		// get the graphic drawing context
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		g2.setBackground(Drawing.backgroundColor);
		g2.clearRect(0, 0, imageWidth, imageHeight);
		g2.setPaint(Color.BLACK);
		// draw the background of the image
		drawBackground(g2);
		// draw all the paths in the image
		drawPath(g2, graph);
		// draw all the points in the image
		drawPoint(g2, graph);
		// draw the title in the image
		drawTitle(g2);
		return bufferedImage;
	}

	/**
	 * Draw the background of the image
	 * 
	 * @param g2
	 *            the drawing context of the buffered image
	 * 
	 */
	private void drawBackground(Graphics2D g2) {
		// TODO Auto-generated method stub
		// g2.drawImage();
	}

	/**
	 * Draw the tile of the image
	 * 
	 * @param g2
	 *            The drawing context of the buffered image
	 */
	private void drawTitle(Graphics2D g2) {
		Font oldFont = g2.getFont();
		g2.setFont(Drawing.titleFont);
		FontRenderContext fontRenderContext = g2.getFontRenderContext();
		Rectangle2D fontRectangle = Drawing.titleFont.getStringBounds(
				Drawing.titleString, fontRenderContext);
		int padding = 33;
		g2.drawString(Drawing.titleString, (int) (Drawing.actualXSize
				+ Drawing.xPadding - fontRectangle.getWidth() + padding),
				(int) (Drawing.actualYSize + Drawing.yPadding + padding));
		g2.setFont(oldFont);
	}

	/**
	 * Get the direction of the path
	 * 
	 * @param path
	 *            The path which we want to get the direction of
	 * @param startPoint
	 *            The start point of the path
	 * @param graph
	 *            The data structure of the schematic map generated by the
	 *            algorithm
	 * @return An integer in presentation of the direction of the path
	 */
	private int getPathQuadrant(Path path, Point startPoint,
			Graph<Point, Path> graph) {
		// get the two points of the given path
		Point point1 = graph.getEdgeSource(path);
		Point point2 = graph.getEdgeTarget(path);
		Point anotherPoint = (point1 == startPoint) ? point2 : point1;
		// check out which quadrant the path points to
		if (anotherPoint.getXPosition() > startPoint.getXPosition()
				&& anotherPoint.getYPosition() > startPoint.getYPosition()) {
			return 4;
		} else if (anotherPoint.getXPosition() > startPoint.getXPosition()
				&& anotherPoint.getYPosition() < startPoint.getYPosition()) {
			return 1;
		} else if (anotherPoint.getXPosition() < startPoint.getXPosition()
				&& anotherPoint.getYPosition() > startPoint.getYPosition()) {
			return 3;
		} else if (anotherPoint.getXPosition() < startPoint.getXPosition()
				&& anotherPoint.getYPosition() < startPoint.getYPosition()) {
			return 2;
		} else {
			// the function returns 0 if the path does not belong to any
			// quadrant
			return 0;
		}
	}

	/**
	 * Draw the path in the schematic image
	 * 
	 * @param g2
	 *            The drawing context of the buffered image
	 * @param graph
	 *            The data structure of the schematic map generated by the
	 *            algorithm
	 */
	private void drawPath(Graphics2D g2, Graph<Point, Path> graph) {
		// get all the paths in the graph
		Set<Path> pathSet = graph.edgeSet();
		Stroke oldStroke = g2.getStroke();
		// set endpoints of lines to be round
		// g2.setStroke(new BasicStroke(Drawing.lineThick,
		// BasicStroke.CAP_SQUARE,
		// BasicStroke.JOIN_ROUND));
		g2.setStroke(new BasicStroke(Drawing.lineThick, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		for (Path tempPath : pathSet) {
			// set the color of the path according to the ID of the path and
			// reserve the old color of the graphics context
			Color oldColor = g2.getColor();
			g2.setColor(Drawing.colorArray[tempPath.getPathID() % 9]);
			g2.drawLine(
					(int) (tempPath.getPoint1XPosition() + Drawing.xPadding),
					(int) (tempPath.getPoint1YPosition() + Drawing.yPadding),
					(int) (tempPath.getPoint2XPosition() + Drawing.xPadding),
					(int) (tempPath.getPoint2YPosition() + Drawing.yPadding));
			// return the initial color of the graphics context
			g2.setColor(oldColor);
		}
		g2.setStroke(oldStroke);
	}

	/**
	 * Draw the point in the schematic image
	 * 
	 * @param g2
	 *            The drawing context of the buffered image
	 * @param graph
	 *            The data structure of the schematic map generated by the
	 *            algorithm
	 */
	private void drawPoint(Graphics2D g2, Graph<Point, Path> graph) {
		// draw every point on the drawing
		Set<Point> pointSet = graph.vertexSet();
		// construct a NamePositionChecker object to check the drawing position
		// of the name of the point to be drawn
		NamePositionChecker checker = new NamePositionChecker();
		for (Point tempPoint : pointSet) {
			Color oldColor = Color.BLACK;
			// draw the point in red if the point is included in two complete
			// paths or the point is the place where the user is standing
			if (tempPoint.getRepeatId() != 0
					|| tempPoint.getType() == PointType.USER) {
				oldColor = g2.getColor();
				g2.setColor(Color.RED);
			}
			if (tempPoint.getType() == PointType.KEY
					|| tempPoint.getType() == PointType.USER) {
				// draw the point in a special shape if the point is a key point
				// or the point is the place where the user is standing
				g2.drawOval(
						(int) (tempPoint.getXPosition() - 0.5
								* Drawing.outerDiameterOfKeyPoint + Drawing.xPadding),
						(int) (tempPoint.getYPosition() - 0.5
								* Drawing.outerDiameterOfKeyPoint + Drawing.yPadding),
						Drawing.outerDiameterOfKeyPoint,
						Drawing.outerDiameterOfKeyPoint);
				g2.fillOval(
						(int) (tempPoint.getXPosition() - 0.5
								* Drawing.outerDiameterOfKeyPoint + Drawing.xPadding),
						(int) (tempPoint.getYPosition() - 0.5
								* Drawing.outerDiameterOfKeyPoint + Drawing.yPadding),
						Drawing.outerDiameterOfKeyPoint,
						Drawing.outerDiameterOfKeyPoint);
				Color oldColor2 = g2.getColor();
				g2.setColor(Color.WHITE);
				g2.fillOval(
						(int) (tempPoint.getXPosition() + Drawing.xPadding - 0.5 * Drawing.innerDiameterOfKeyPoint),
						(int) (tempPoint.getYPosition() + Drawing.yPadding - 0.5 * Drawing.innerDiameterOfKeyPoint),
						Drawing.innerDiameterOfKeyPoint,
						Drawing.innerDiameterOfKeyPoint);
				g2.setColor(oldColor2);
			} else if (tempPoint.getType() == PointType.NON_KEY) {
				// draw the point in a normal shape is the point is not a
				// non-key point
				g2.drawOval(
						(int) (tempPoint.getXPosition() - 0.5
								* Drawing.diameterOfNonkeyPoint + Drawing.xPadding),
						(int) (tempPoint.getYPosition() - 0.5
								* Drawing.diameterOfNonkeyPoint + Drawing.yPadding),
						Drawing.diameterOfNonkeyPoint,
						Drawing.diameterOfNonkeyPoint);
				g2.fillOval(
						(int) (tempPoint.getXPosition() - 0.5
								* Drawing.diameterOfNonkeyPoint + Drawing.xPadding),
						(int) (tempPoint.getYPosition() - 0.5
								* Drawing.diameterOfNonkeyPoint + Drawing.yPadding),
						Drawing.diameterOfNonkeyPoint,
						Drawing.diameterOfNonkeyPoint);
			}
			// recover the drawing color of the graphics context
			if (tempPoint.getRepeatId() != 0
					|| tempPoint.getType() == PointType.USER) {
				g2.setColor(oldColor);
			}
			// here we use the new drawing function
			// drawPointName(tempPoint, g2, graph);
			DrawPointName(checker, tempPoint, g2, graph);
		}
	}

	/**
	 * Draw the name of the point in the schematic map
	 * 
	 * @param checker
	 *            The object records the string rectangles already in the image
	 *            and judges whether the new string rectangle can be drawn in a
	 *            proper space
	 * @param tempPoint
	 *            The point of which we want to draw the name
	 * @param g2
	 *            The drawing context of the buffered image
	 * @param graph
	 *            The data structure of the schematic map generated by the
	 *            algorithm
	 */
	private void DrawPointName(NamePositionChecker checker, Point tempPoint,
			Graphics2D g2, Graph<Point, Path> graph) {
		boolean canNameBeDrawn = true;
		if (tempPoint.getName() != null&&tempPoint.getType()!=PointType.NON_KEY) {
			// reserve the old font of the graphics context
			Font currentFont = g2.getFont();
			Rectangle2D fontRectangle = currentFont.getStringBounds(
					tempPoint.getName(), g2.getFontRenderContext());
			// the drawing position (the coordinate of the left down point of
			// the rectangle) of the string to be drawn
			int stringXPosition = 0;
			int stringYPosition = 0;
			// get the surrounding paths around the point
			Set<Path> pathSetOfOnePoint = graph.edgesOf(tempPoint);
			boolean[] fourQuadrant = new boolean[4];
			// set the member of the boolean array to be true
			for (int index = 0; index < 4; index++) {
				fourQuadrant[index] = true;
			}
			// if there is one line in one quadrant, the name of the point
			// cannot be drawn there
			for (Path tempPath : pathSetOfOnePoint) {
				int pathQuadrant = getPathQuadrant(tempPath, tempPoint, graph);
				if (pathQuadrant == 0) {
					// do nothing
				} else {
					fourQuadrant[pathQuadrant - 1] = false;
				}
			}
			// int padding = 6;
			// check the four quadrants again on whether the string will pass
			// the border of the image or coincide with other strings already
			// drawn in the image
			if (fourQuadrant[0]) {
				if (Drawing.actualXSize - tempPoint.getXPosition()
						- Drawing.namePadding + Drawing.xPadding < fontRectangle
						.getWidth()
						|| tempPoint.getYPosition() - Drawing.namePadding
								+ Drawing.yPadding < fontRectangle.getHeight()) {
					fourQuadrant[0] = false;
				}
				if (checker
						.isCoincide(
								new Rectangle((int) fontRectangle.getWidth(),
										(int) fontRectangle.getHeight()),
								(int) tempPoint.getXPosition()
										+ Drawing.namePadding
										+ Drawing.xPadding,
								(int) (tempPoint.getYPosition()
										- Drawing.namePadding + Drawing.yPadding))) {
					fourQuadrant[0] = false;
				}
			}
			if (fourQuadrant[1]) {
				if (tempPoint.getXPosition() - Drawing.namePadding
						+ Drawing.xPadding < fontRectangle.getWidth()
						|| tempPoint.getYPosition() - Drawing.namePadding
								+ Drawing.yPadding < fontRectangle.getHeight()) {
					fourQuadrant[1] = false;
				}
				if (checker
						.isCoincide(
								new Rectangle((int) fontRectangle.getWidth(),
										(int) fontRectangle.getHeight()),
								(int) (tempPoint.getXPosition()
										- Drawing.namePadding
										- fontRectangle.getWidth() + Drawing.xPadding),
								(int) (tempPoint.getYPosition()
										- Drawing.namePadding + Drawing.yPadding))) {
					fourQuadrant[1] = false;
				}
			}
			if (fourQuadrant[2]) {
				if (tempPoint.getXPosition() - Drawing.namePadding
						+ Drawing.xPadding < fontRectangle.getWidth()
						|| Drawing.actualYSize - tempPoint.getYPosition()
								- Drawing.namePadding + Drawing.yPadding < fontRectangle
								.getHeight()) {
					fourQuadrant[2] = false;
				}
				if (checker.isCoincide(
						new Rectangle((int) fontRectangle.getWidth(),
								(int) fontRectangle.getHeight()),
						(int) (tempPoint.getXPosition() - Drawing.namePadding
								- fontRectangle.getWidth() + Drawing.xPadding),
						(int) (tempPoint.getYPosition()
								+ fontRectangle.getHeight()
								+ Drawing.namePadding + Drawing.yPadding))) {
					fourQuadrant[2] = false;
				}
			}
			if (fourQuadrant[3]) {
				if (Drawing.actualXSize - tempPoint.getXPosition()
						- Drawing.namePadding + Drawing.xPadding < fontRectangle
						.getWidth()
						|| Drawing.actualYSize - tempPoint.getYPosition()
								- Drawing.namePadding + Drawing.yPadding < fontRectangle
								.getHeight()) {
					fourQuadrant[3] = false;
				}
				if (checker.isCoincide(
						new Rectangle((int) fontRectangle.getWidth(),
								(int) fontRectangle.getHeight()),
						(int) tempPoint.getXPosition() + Drawing.namePadding
								+ Drawing.xPadding,
						(int) (tempPoint.getYPosition()
								+ fontRectangle.getHeight()
								+ Drawing.namePadding + Drawing.yPadding))) {
					fourQuadrant[3] = false;
				}
			}
			// compute the new position of the string to be drawn
			if (fourQuadrant[0]) {
				canNameBeDrawn = true;
				stringXPosition = (int) tempPoint.getXPosition()
						+ Drawing.namePadding;
				stringYPosition = (int) (tempPoint.getYPosition() - Drawing.namePadding);
			} else if (fourQuadrant[1]) {
				canNameBeDrawn = true;
				stringXPosition = (int) (tempPoint.getXPosition()
						- Drawing.namePadding - fontRectangle.getWidth());
				stringYPosition = (int) (tempPoint.getYPosition() - Drawing.namePadding);
			} else if (fourQuadrant[2]) {
				canNameBeDrawn = true;
				stringXPosition = (int) (tempPoint.getXPosition()
						- Drawing.namePadding - fontRectangle.getWidth());
				stringYPosition = (int) (tempPoint.getYPosition()
						+ fontRectangle.getHeight() + Drawing.namePadding);
			} else if (fourQuadrant[3]) {
				canNameBeDrawn = true;
				stringXPosition = (int) tempPoint.getXPosition()
						+ Drawing.namePadding;
				stringYPosition = (int) (tempPoint.getYPosition()
						+ fontRectangle.getHeight() + Drawing.namePadding);
			} else {
				// there is no proper place to draw the name string so draw
				// it to a default place
				canNameBeDrawn = false;
				// stringXPosition = (int) tempPoint.getXPosition() + padding;
				// stringYPosition = (int) tempPoint.getYPosition() - padding;
			}
			if (canNameBeDrawn) {
				// add the padding to the position of the string
				stringXPosition += Drawing.xPadding;
				stringYPosition += Drawing.yPadding;
				// add the position of the string to the checker
				checker.addNameRectangle(
						new Rectangle((int) fontRectangle.getWidth(),
								(int) fontRectangle.getHeight()),
						stringXPosition, stringYPosition);
				Color oldColor = Color.BLACK;
				if (tempPoint.getRepeatId() != 0
						|| tempPoint.getType() == PointType.USER) {
					oldColor = g2.getColor();
					g2.setColor(Color.RED);
				}
				// draw the string
				g2.drawString(tempPoint.getName(), stringXPosition,
						stringYPosition - 2);
//				 g2.drawRect((int) stringXPosition,
//				 (int) (stringYPosition - fontRectangle.getHeight()),
//				 (int) (fontRectangle.getWidth()),
//				 (int) (fontRectangle.getHeight()));
				if (tempPoint.getRepeatId() != 0
						|| tempPoint.getType() == PointType.USER) {
					g2.setColor(oldColor);
				}
//				assert(false);
//				double delta=Math.abs(stringXPosition-(Drawing.xPadding+tempPoint.getXPosition()));
//				if(Math.abs(delta-(Drawing.namePadding))<1.0d||Math.abs(delta-(Drawing.namePadding+fontRectangle.getWidth()))<1.0d){
//					System.out.println("CORRECT");
//				}else{
//					System.out.println("ERROR");
//				}
			}

		}
	}

	/**
	 * The primitive method used to draw the name of the point
	 * 
	 * @param tempPoint
	 *            The point of which we want to draw the name
	 * @param g2
	 *            The drawing context of the buffered image
	 * @param graph
	 *            The data structure of the schematic map generated by the
	 *            algorithm
	 */
	private void drawPointName(Point tempPoint, Graphics2D g2,
			Graph<Point, Path> graph) {
		// draw the name of the point on the drawing
		if (tempPoint.getName() != null) {
			Font currentFont = g2.getFont();
			Rectangle2D fontRectangle = currentFont.getStringBounds(
					tempPoint.getName(), g2.getFontRenderContext());
			// System.out.println(fontrectangle.getWidth()+"   "+fontrectangle.getHeight());
			int stringXPosition = 0;
			int stringYPosition = 0;
			Set<Path> pathSetOfOnePoint = graph.edgesOf(tempPoint);
			// System.out.println(pathsetofonepoint.size());
			// assert (pathsetofonepoint.size() < 3);
			boolean[] fourQuadrant = new boolean[4];
			// set the member of the boolean array to be true
			for (int index = 0; index < 4; index++) {
				fourQuadrant[index] = true;
			}
			// if there is one line in one quadrant, the name of the point
			// cannot be drawn there
			for (Path tempPath : pathSetOfOnePoint) {
				int pathQuadrant = getPathQuadrant(tempPath, tempPoint, graph);
				if (pathQuadrant == 0) {
					// do nothing
				} else {
					fourQuadrant[pathQuadrant - 1] = false;
				}
			}
			int padding = 8;
			if (fourQuadrant[0]) {
				if (Drawing.actualXSize - tempPoint.getXPosition() - padding
						+ Drawing.xPadding < fontRectangle.getWidth()
						|| tempPoint.getYPosition() - padding
								+ Drawing.yPadding < fontRectangle.getHeight()) {
					fourQuadrant[0] = false;
				}
			}
			if (fourQuadrant[1]) {
				if (tempPoint.getXPosition() - padding + Drawing.xPadding < fontRectangle
						.getWidth()
						|| tempPoint.getYPosition() - padding
								+ Drawing.yPadding < fontRectangle.getHeight()) {
					fourQuadrant[1] = false;
				}
			}
			if (fourQuadrant[2]) {
				if (tempPoint.getXPosition() - padding + Drawing.xPadding < fontRectangle
						.getWidth()
						|| Drawing.actualYSize - tempPoint.getYPosition()
								- padding + Drawing.yPadding < fontRectangle
								.getHeight()) {
					fourQuadrant[2] = false;
				}
			}
			if (fourQuadrant[3]) {
				if (Drawing.actualXSize - tempPoint.getXPosition() - padding
						+ Drawing.xPadding < fontRectangle.getWidth()
						|| Drawing.actualYSize - tempPoint.getYPosition()
								- padding + Drawing.yPadding < fontRectangle
								.getHeight()) {
					fourQuadrant[3] = false;
				}
			}
			if (fourQuadrant[0]) {
				stringXPosition = (int) tempPoint.getXPosition() + padding;
				stringYPosition = (int) (tempPoint.getYPosition() - padding);
			} else if (fourQuadrant[1]) {
				stringXPosition = (int) (tempPoint.getXPosition() - padding - fontRectangle
						.getWidth());
				stringYPosition = (int) (tempPoint.getYPosition() - padding);
			} else if (fourQuadrant[2]) {
				stringXPosition = (int) (tempPoint.getXPosition() - padding - fontRectangle
						.getWidth());
				stringYPosition = (int) (tempPoint.getYPosition()
						+ fontRectangle.getHeight() + padding);
			} else if (fourQuadrant[3]) {
				stringXPosition = (int) tempPoint.getXPosition() + padding;
				stringYPosition = (int) (tempPoint.getYPosition()
						+ fontRectangle.getHeight() + padding);
			} else {
				// there is no proper place to draw the name string so draw
				// it to a default place
				stringXPosition = (int) tempPoint.getXPosition();
				stringYPosition = (int) tempPoint.getYPosition();
			}
			stringXPosition += Drawing.xPadding;
			stringYPosition += Drawing.yPadding;
			Color oldColor = Color.BLACK;
			if (tempPoint.getRepeatId() != 0) {
				oldColor = g2.getColor();
				g2.setColor(Color.RED);
			}
			g2.drawString(tempPoint.getName(), stringXPosition, stringYPosition);
			if (tempPoint.getRepeatId() != 0) {
				g2.setColor(oldColor);
			}
		}
	}
}
