package cn.edu.nju.software.lcy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ContectServer {

	public static Bitmap getAndshowMap(String lan, String lon, int bounds,
			String keywords) throws IOException, MalformedURLException {
		keywords = URLEncoder.encode(keywords, "UTF-8");
		String bound = Integer.toString(bounds);

		URL url = new URL("http://192.168.1.101:8080/QMap/Mobile?center=" + lan
				+ "," + lon + "&bound=" + bound + "&keyword=" + keywords);
		Bitmap resizedBitmap = BitmapFactory.decodeStream(url.openStream());

		return resizedBitmap;
	}
	
}
