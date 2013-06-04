package cn.edu.nju.software.lcy;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**Add this listener to the narrow button*/
public class NarrowListener implements View.OnClickListener {

	private Picture pictureOnTab;
	private Context mobileMap;
	private MyMapView mapView;
	
	public NarrowListener(Picture pictureOnTab, Context mobileMap, MyMapView mapView){
		this.pictureOnTab = pictureOnTab;
		this.mobileMap = mobileMap;
		this.mapView = mapView;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (MobileMap.bounds < 14) {
			MobileMap.bounds++;
			Thread background = new Thread(new MyRunnable(pictureOnTab, mobileMap, mapView));
			background.start();
		} else {
			Toast.makeText(mobileMap,
					"It is the largest bounds already.", Toast.LENGTH_LONG)
					.show();
		}
	}

}
