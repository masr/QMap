package cn.edu.nju.software.lcy;

import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.Toast;

/**This will be run by a new thread to get a new map*/
public class MyRunnable implements Runnable {

	private Picture pictureOnTab;
	private Context mobileMap;
	private MyMapView mapView;
	
	public MyRunnable(Picture pictureOnTab, Context mobileMap, MyMapView mapView){
		this.pictureOnTab = pictureOnTab;
		this.mobileMap = mobileMap;
		this.mapView = mapView;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if ((!MobileMap.keyword.equals(""))
					&& (!MobileMap.keyword.equals("Enter your keywords"))) {
				String lan = Double.toString(MobileMap.laning);
				String lon = Double.toString(MobileMap.loning);
				try {
					MobileMap.view.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MobileMap.m = new Matrix();
							pictureOnTab.reset(MobileMap.m);
							MobileMap.view.setImageMatrix(MobileMap.m);
							MobileMap.view.setImageResource(R.drawable.wait);
						}
					});
					MobileMap.newMap = ContectServer.getAndshowMap(lan, lon, MobileMap.bounds, MobileMap.keyword);
					MobileMap.view.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MobileMap.m.preScale(0.4f, 0.4f);
							pictureOnTab.reset(MobileMap.m);    //Reset the Picture's matrix to a new one.
							MobileMap.view.setImageMatrix(MobileMap.m);
							MobileMap.view.setImageBitmap(MobileMap.newMap);
						}

					});
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					MobileMap.view.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MobileMap.m.postTranslate(92, 91);
							pictureOnTab.reset(MobileMap.m);
							MobileMap.view.setImageMatrix(MobileMap.m);
							MobileMap.view.setImageResource(R.drawable.jinggao);
							Toast.makeText(mobileMap, "Sorry, server connecting failed.", Toast.LENGTH_LONG).show();
						}

					});
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					MobileMap.view.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MobileMap.m.postTranslate(92, 91);
							pictureOnTab.reset(MobileMap.m);
							MobileMap.view.setImageMatrix(MobileMap.m);
							MobileMap.view.setImageResource(R.drawable.jinggao);
							Toast.makeText(mobileMap, "Sorry, no picture received.", Toast.LENGTH_LONG).show();
						}

					});
					e.printStackTrace();
				}
				mapView.setMoveable();
			} else {
				Toast.makeText(mobileMap,
						"You should enter your keywords.",
						Toast.LENGTH_LONG).show();
			}
		} catch (Throwable t) {
			// just end the background thread
		}
	}

}
