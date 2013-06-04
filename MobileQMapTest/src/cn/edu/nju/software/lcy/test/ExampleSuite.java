package cn.edu.nju.software.lcy.test;

import junit.framework.TestSuite;

public class ExampleSuite extends TestSuite{

	public ExampleSuite(){
		addTestSuite(ContectServerTest.class);
		addTestSuite(ExpandListenerTest.class);
		addTestSuite(MobileMapTest.class);
		addTestSuite(NarrowListenerTest.class);
		addTestSuite(MyMapViewTest.class);
		addTestSuite(MyRunnableTest.class);
		addTestSuite(PictureTest.class);
		addTestSuite(SitesOverlayTest.class);
	}
	
}
