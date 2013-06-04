package com.passioncoder.qmap.test.preprocess;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.preprocess.SubwayProcessor;

public class SubwayProcessTest {
 
	@Test
	public void testGetSinglePath(){
		String string="{paths:[{spots:[{repeat_id:0,name:1,lat:32.7849295,lng:118.4194155},{repeat_id:0,name:2,lat:32.7949295,lng:118.4194155},{repeat_id:0,name:3,lat:32.8049295,lng:118.4194155},]},]},]}";
	    SubwayProcessor subwayProcessor=new SubwayProcessor();
		List<Path> paths=subwayProcessor.buildPathList(string);
		assertEquals(1, paths.size());
		assertEquals(3, paths.get(0).getPoints().size());
	}
	
	@Test
	public void testGetMultiPath(){
		String string="{paths:[{spots:[{repeat_id:1,name:1,lat:32.7849295,lng:118.4194155},{repeat_id:0,name:2,lat:32.7949295,lng:118.4194155},{repeat_id:0,name:3,lat:32.8049295,lng:118.4194155},]},{spots:[{repeat_id:0,name:2,lat:32.7949295,lng:119.4194155},{repeat_id:0,name:nju,lat:32.8049295,lng:119.4194155},{repeat_id:1,name:3,lat:32.7849295,lng:118.4194155},]},]}";
	    SubwayProcessor subwayProcessor=new SubwayProcessor();
		List<Path> paths=subwayProcessor.buildPathList(string);
		assertEquals(2, paths.size());
		assertEquals(3, paths.get(1).getPoints().size());
	}
	
	@Test
	public void testGetRepeatPath(){
		String string="{paths:[{spots:[{repeat_id:1,name:1,lat:32.7849295,lng:118.4194155},{repeat_id:0,name:2,lat:32.7949295,lng:118.4194155},{repeat_id:0,name:3,lat:32.8049295,lng:118.4194155},]},{spots:[{repeat_id:0,name:2,lat:32.7949295,lng:119.4194155},{repeat_id:0,name:nju,lat:32.8049295,lng:119.4194155},{repeat_id:1,name:3,lat:32.7849295,lng:118.4194155},]},]}";
	    SubwayProcessor subwayProcessor=new SubwayProcessor();
		List<Path> paths=subwayProcessor.buildPathList(string);
		assertEquals(paths.get(0).getPoints().get(0), paths.get(1).getPoints().get(2));
	}
}
