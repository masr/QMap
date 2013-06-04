package com.passioncoder.qmap.test.drawing;

import java.awt.Rectangle;

import org.junit.Test;

import com.passioncoder.qmap.drawing.NamePositionChecker;

public class NamePositionCheckerTest {

	/**
	 * The test is aimed to check whether we can successfully add a new
	 * rectangle to the list in the NamePositionChecker
	 */
	@Test
	public void testAddNameRectangle() {
		NamePositionChecker namePositionChecker = new NamePositionChecker();
		namePositionChecker.addNameRectangle(new Rectangle(45, 16), 133.00d,
				123.00d);
		assert (true);
	}

	/**
	 * The test is aimed to check whether the three rectangle we added into the
	 * list coincide and the three rectangle we added into the list coincide and
	 * we want the tested function to have a true return value
	 */
	@Test
	public void testIsCoincide1() {
		NamePositionChecker namePositionChecker = new NamePositionChecker();
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 0, 700);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 600, 0);
		boolean retValue = namePositionChecker.isCoincide(new Rectangle(100,
				100), 300, 350);
		assert (retValue == false);
	}

	/**
	 * The test is aimed to check whether the three rectangle we added into the
	 * list coincide and the three rectangle we added into the list do not
	 * coincide and we want the tested function have a false return value
	 */
	@Test
	public void testIsCoincide2() {
		NamePositionChecker namePositionChecker = new NamePositionChecker();
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 0, 700);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 600, 0);
		boolean retValue = namePositionChecker.isCoincide(new Rectangle(100,
				100), 50, 50);
		assert (retValue == true);
	}

	/**
	 * The test is aimed to check whether the three rectangle we added into the
	 * list coincide and the three rectangle we added into the list do not
	 * coincide and we want the tested function have a false return value
	 */
	@Test
	public void testIsCoincide3() {
		NamePositionChecker namePositionChecker = new NamePositionChecker();
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 0, 700);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 600, 0);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 500, 20);
		namePositionChecker.addNameRectangle(new Rectangle(20, 20), 200, 100);
		namePositionChecker.addNameRectangle(new Rectangle(50, 50), 300, 300);
		boolean retValue = namePositionChecker.isCoincide(new Rectangle(100,
				100), 5, 5);
		assert (retValue == true);
	}

	/**
	 * The test is aimed to check whether the three rectangle we added into the
	 * list coincide and the three rectangle we added into the list do not
	 * coincide and we want the tested function have a false return value
	 */
	@Test
	public void testIsCoincide4() {
		NamePositionChecker namePositionChecker = new NamePositionChecker();
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 0, 700);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 600, 0);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 500, 20);
		namePositionChecker.addNameRectangle(new Rectangle(20, 20), 200, 100);
		namePositionChecker.addNameRectangle(new Rectangle(50, 50), 300, 300);
		boolean retValue = namePositionChecker.isCoincide(new Rectangle(1, 1),
				500, 300);
		assert (retValue == true);
	}

	/**
	 * The test is aimed to check whether the three rectangle we added into the
	 * list coincide and the three rectangle we added into the list do not
	 * coincide and we want the tested function have a false return value
	 */
	@Test
	public void testIsCoincide5() {
		NamePositionChecker namePositionChecker = new NamePositionChecker();
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 0, 700);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 600, 0);
		namePositionChecker.addNameRectangle(new Rectangle(100, 100), 500, 20);
		namePositionChecker.addNameRectangle(new Rectangle(20, 20), 200, 100);
		namePositionChecker.addNameRectangle(new Rectangle(50, 50), 300, 300);
		boolean retValue = namePositionChecker.isCoincide(new Rectangle(1, 1),
				500, 300);
		assert (retValue == true);
	}

}
