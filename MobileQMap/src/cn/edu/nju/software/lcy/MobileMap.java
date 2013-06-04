package cn.edu.nju.software.lcy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.R.color;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.ZoomControls;

public class MobileMap extends MapActivity {

	/* These are the widgets named in the layout */
	public Button button;
	public MyMapView map;
	private Drawable marker;
	public EditText edit;
	public static ImageView view;
	public Button expand, narrow;

	public TabHost tabs;

	public static int bounds;
	public static Bitmap newMap;
	public static Matrix m;
	
	public Picture pictureOnTab;

	/* These are the variables used in the class to keep some characters */
	public static double laning = 33.422006, loning = 118.084095;
	public static String keyword;
	public LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		getWidget();

		bounds = 5;

		initialButton();
		initialKeyword();
		initialMap();
		initialTabs();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/** To get all widgets */
	public void getWidget() {
		button = (Button) findViewById(R.id.submit);
		map = (MyMapView) findViewById(R.id.map);
		edit = (EditText) findViewById(R.id.keyword);
		marker = getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		tabs = (TabHost) findViewById(R.id.tabhost);
		view = (ImageView) findViewById(R.id.imageView);
		pictureOnTab = new Picture();
		view.setOnTouchListener(pictureOnTab);
		expand = (Button) findViewById(R.id.bigger);
		narrow = (Button) findViewById(R.id.smaller);
	}

	/** Initial the tabs */
	public void initialTabs() {
		tabs.setup();
		TabHost.TabSpec spec = tabs.newTabSpec("tag1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("", getResources().getDrawable(R.drawable.google));
		tabs.addTab(spec);
		spec = tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("",
				getResources().getDrawable(R.drawable.simple));
		tabs.addTab(spec);
	}

	/** Initial the button, add a listener to it */
	public void initialButton() {
		// TODO Auto-generated method stub
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tabs.setCurrentTab(1);
				keyword = edit.getText().toString();
				Thread background = new Thread(new MyRunnable(pictureOnTab, MobileMap.this, map));
				background.start();
			}
		});

		expand.setOnClickListener(new ExpandListener(pictureOnTab, MobileMap.this, map));
		narrow.setOnClickListener(new NarrowListener(pictureOnTab, MobileMap.this, map));
	}

	/**
	 * Initial the EditText, set the text to "Enter your keyword", add a
	 * listener to it to initial the text
	 */
	public void initialKeyword() {
		keyword = "";
		edit.setText("Enter your keywords");
		edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edit.setText("");
			}
		});
	}

	/**
	 * Initial the MapView: set overlay, add location changed listeners
	 */
	public void initialMap() {
		map.getOverlays().add(new SitesOverlay(marker));
		map.setPictureOnTab(pictureOnTab);
		map.setContext(MobileMap.this);
		
		String service = Context.LOCATION_SERVICE;
		String gps_provider = LocationManager.GPS_PROVIDER;
		String net_provider = LocationManager.NETWORK_PROVIDER;
		String provider;

		locationManager = (LocationManager) getSystemService(service);

		boolean gps_enable = locationManager.isProviderEnabled(gps_provider);
		if (!gps_enable) {
			new AlertDialog.Builder(MobileMap.this)
					.setMessage(
							"You have not open your GPS function.Choose ok to open it and choose close to continue")
					.setTitle("Warning:")
					.setPositiveButton("Close",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}
							})
					.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									finish();
								}
							}).show();
			provider = net_provider;
		} else {
			provider = gps_provider;
		}

		FirstListener first = new FirstListener();
		locationManager.requestLocationUpdates(provider, 0, 0, first);

		int google_time = 60000;
		int google_distance = 10;

		MapLocationChanged mapLocationChanged = new MapLocationChanged();
		locationManager.requestLocationUpdates(provider, google_time,
				google_distance, mapLocationChanged);
	}

	/** The quickest listener to locate the map to the temporary location */
	private class FirstListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			float tmp_lan = (float) location.getLatitude();
			float tmp_lon = (float) location.getLongitude();

			laning = tmp_lan;
			loning = tmp_lon;

			map.getOverlays().remove(0);
			map.getOverlays().add(new SitesOverlay(marker));

			map.getController().setCenter(
					new GeoPoint((int) (tmp_lan * 1000000.0),
							(int) (tmp_lon * 1000000.0)));
			map.getController().setZoom(15);

			locationManager.removeUpdates(this);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * While the location of the user changed, the map will reload to the one
	 * using the new location as the center.
	 */
	private class MapLocationChanged implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			float tmp_lan = (float) location.getLatitude();
			float tmp_lon = (float) location.getLongitude();

			laning = tmp_lan;
			loning = tmp_lon;

			map.getOverlays().remove(0);
			map.getOverlays().add(new SitesOverlay(marker));

			map.getController().setCenter(
					new GeoPoint((int) (tmp_lan * 1000000.0),
							(int) (tmp_lon * 1000000.0)));
			map.getController().setZoom(16);

			Thread background = new Thread(new MyRunnable(pictureOnTab, MobileMap.this, map));
			background.start();
			view.setImageBitmap(newMap);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}
}
