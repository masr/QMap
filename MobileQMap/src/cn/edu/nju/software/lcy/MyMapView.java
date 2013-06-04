package cn.edu.nju.software.lcy;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class MyMapView extends MapView {

	static final int NONE = 0;
	static final int ZOOM = 2;
	int mode = NONE;
	
	private boolean moveable = true; 
	
	private Picture pictureOnTab;
	private Context mobileMap;
	
	public MyMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean  onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM && moveable) {
				moveable = false;
				int bounds = this.getZoomLevel();
				if (bounds > 5 && bounds < 21) {
					MobileMap.bounds = bounds - 6;
					Thread background = new Thread(new MyRunnable(pictureOnTab, mobileMap, this));
				    background.start();
				}
			}
			break;
		}
		return true;
	}

	public void setPictureOnTab(Picture pictureOnTab){
		this.pictureOnTab = pictureOnTab;
	}
	
	public void setContext(Context mobileMap){
		this.mobileMap = mobileMap;
	}
	
	public synchronized void setMoveable(){
		moveable = true;
	}
}
