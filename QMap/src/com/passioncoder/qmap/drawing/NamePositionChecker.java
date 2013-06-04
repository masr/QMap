package com.passioncoder.qmap.drawing;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class NamePositionChecker {
	class NameRectangle extends Rectangle {
		private static final long serialVersionUID = 6955129291628818512L;

		// the X position and Y position of the leftdown point of the string
		// rectangle
		private double xPositionOfLeftDownPoint;
		private double yPositionOfLeftDownPoint;

		/**
		 * The constructor of the class named NameRectangle
		 * 
		 * @param rectangle
		 *            The rectangle where the string are drawn
		 * @param xPositionOfLDPoint
		 *            The X position of the leftdown point of the string
		 *            rectangle
		 * @param yPositionOfLDPoint
		 *            The Y position of the leftdown point of the string
		 *            rectangle
		 */
		public NameRectangle(Rectangle rectangle, double xPositionOfLDPoint,
				double yPositionOfLDPoint) {
			super(rectangle);
			this.xPositionOfLeftDownPoint = xPositionOfLDPoint;
			this.yPositionOfLeftDownPoint = yPositionOfLDPoint;
		}

		/**
		 * Get the X position of the leftdown point of the string rectangle
		 * 
		 * @return The X position of the leftdown point of the string rectangle
		 */
		public double getXPositionOfLeftDownPoint() {
			return this.xPositionOfLeftDownPoint;
		}

		/**
		 * Get the Y position of the leftdown point of the string rectangle
		 * 
		 * @return The Y position of the leftdown point of the string rectangle
		 */
		public double getYPositionOfLeftDownPoint() {
			return this.yPositionOfLeftDownPoint;
		}

		/**
		 * Set the X position of the leftdown point of the string rectangle
		 * 
		 * @param xPositionOfLeftDownPoint
		 *            The X position of the leftdown point of the string
		 *            rectangle
		 */
		public void setXPositionOfLeftDownPoint(double xPositionOfLeftDownPoint) {
			this.xPositionOfLeftDownPoint = xPositionOfLeftDownPoint;
		}

		/**
		 * Set the Y position of the leftdown point of the string rectangle
		 * 
		 * @param yPositionOfLeftDownPoint
		 *            The Y position of the leftdown point of the string
		 *            rectangle
		 */
		public void setYPositionOfLeftUpPoint(double yPositionOfLeftDownPoint) {
			this.yPositionOfLeftDownPoint = yPositionOfLeftDownPoint;
		}

		/**
		 * Check whether the string rectangle coincides with another string
		 * rectangle
		 * 
		 * @param anotherNameRectangle
		 *            Another string rectangle
		 * @return The boolean value represents whether the two rectangles
		 *         coincide
		 */
		public boolean isCoincide(NameRectangle anotherNameRectangle) {
			double xPositionOfLDPointOfFirstRectangle = this
					.getXPositionOfLeftDownPoint();
			double yPositionOfLDPointOfFirstRectangle = this
					.getYPositionOfLeftDownPoint();
			double xPositionOfRUPointOfFirstRectangle = xPositionOfLDPointOfFirstRectangle
					+ this.getWidth();
			double yPositionOfRUPointOfFirstRectangle = yPositionOfLDPointOfFirstRectangle
					- this.getHeight();
			double xPositionOfLDPointOfSecondRectangle = anotherNameRectangle
					.getXPositionOfLeftDownPoint();
			double yPositionOfLDPointOfSecondRectangle = anotherNameRectangle
					.getYPositionOfLeftDownPoint();
			double xPositionOfRUPonitOfSecondRectangle = xPositionOfLDPointOfSecondRectangle
					+ anotherNameRectangle.getWidth();
			double yPositionOfRUPointOfSecondRectangle = yPositionOfLDPointOfSecondRectangle
					- anotherNameRectangle.getHeight();
			double xPositionOfLDPoint = Math.min(
					xPositionOfLDPointOfFirstRectangle,
					xPositionOfLDPointOfSecondRectangle);
			double yPositionOfLDPoint = Math.max(
					yPositionOfLDPointOfFirstRectangle,
					yPositionOfLDPointOfSecondRectangle);
			double xPositionOfRUPoint = Math.max(
					xPositionOfRUPointOfFirstRectangle,
					xPositionOfRUPonitOfSecondRectangle);
			double yPositionOfRUPoint = Math.min(
					yPositionOfRUPointOfFirstRectangle,
					yPositionOfRUPointOfSecondRectangle);
			double widthOfActualRectangle = xPositionOfRUPoint
					- xPositionOfLDPoint;
			double heightOfActualRectangle = yPositionOfLDPoint
					- yPositionOfRUPoint;
			double addOfWidthOfTwoRectangle = this.getWidth()
					+ anotherNameRectangle.getWidth();
			double addOfHeightOfTwoRectangle = this.getHeight()
					+ anotherNameRectangle.getHeight();
			if (widthOfActualRectangle < addOfWidthOfTwoRectangle
					&& heightOfActualRectangle < addOfHeightOfTwoRectangle) {
				return true;
			} else {
				return false;
			}
		}
	}

	// the data structure used to store the namerectangles
	List<NameRectangle> rectangleSet = null;

	/**
	 * The constructor of the class named NamePositionChecker
	 */
	public NamePositionChecker() {
		rectangleSet = new ArrayList<NamePositionChecker.NameRectangle>();
	}

	/**
	 * Add one string rectangle to the list data structure of the class
	 * 
	 * @param nameRectangle
	 *            The name rectangle we want to add into the list
	 * @param xPositionOfLDPoint
	 *            The X position of the leftdown point of the string rectangle
	 * @param yPositionOfLDPoint
	 *            The Y position of the leftdown point of the string rectangle
	 */
	public void addNameRectangle(Rectangle nameRectangle,
			double xPositionOfLDPoint, double yPositionOfLDPoint) {
		this.rectangleSet.add(new NameRectangle(nameRectangle,
				xPositionOfLDPoint, yPositionOfLDPoint));
		System.out.println(this.rectangleSet.size());
	}

	/**
	 * Check whether the string rectangle coincides with all of the rectangles
	 * in the list
	 * 
	 * @param nameRectangle
	 *            The name rectangle of the string where the string will be
	 *            drawn
	 * @param xPositionOfLDPoint
	 *            The X position of the leftdown point of the string rectangle
	 * @param yPositionOfLDPoint
	 *            The Y position of the leftdown point of the string rectangle
	 * @return The boolean value represents whether the string rectangle
	 *         coincides with all the rectangls in the list
	 */
	public boolean isCoincide(Rectangle nameRectangle,
			double xPositionOfLDPoint, double yPositionOfLDPoint) {
		NameRectangle newNameRectangle = new NameRectangle(nameRectangle,
				xPositionOfLDPoint, yPositionOfLDPoint);
		for (NameRectangle existingNameRectangle : rectangleSet) {
			if (newNameRectangle.isCoincide(existingNameRectangle)) {
				return true;
			}
		}
		return false;
	}

}
