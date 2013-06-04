package cn.edu.nju.software.lcy;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/** The overlay to mark the temporary location */
public class SitesOverlay extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> items = new ArrayList<OverlayItem>();
	private Drawable marker = null;

	public SitesOverlay(Drawable marker) {
		super(marker);
		this.marker = marker;

		items.add(new OverlayItem(new GeoPoint((int) (MobileMap.laning * 1000000.0),
				(int) (MobileMap.loning * 1000000.0)), null, null));
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return (items.get(i));
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		boundCenterBottom(marker);
	}

	@Override
	public int size() {
		return (items.size());
	}
}
