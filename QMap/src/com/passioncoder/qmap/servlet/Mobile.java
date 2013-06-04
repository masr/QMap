package com.passioncoder.qmap.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.PointType;
import com.passioncoder.qmap.preprocess.UserCenterProcessor;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Servlet implementation class Mobile
 */
public class Mobile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Mobile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,IOException {
		
		String center = request.getParameter("center").trim();
		String bound = request.getParameter("bound").trim();
		String range=getRange(bound);

		String keyword = URLEncoder.encode(request.getParameter("keyword").trim(),
				"ISO-8859-1");
		keyword = URLDecoder.decode(keyword, "UTF-8");

		UserCenterProcessor processor = new UserCenterProcessor();

		List<Point> points = processor.getPoints(center, range, keyword);
		if (points == null || points.size() == 0) {
		 returnErrorImage(request, response);
		 return;
		}
		double lat = Double.parseDouble(center.split(",")[0]);
		double lng = Double.parseDouble(center.split(",")[1]);
		
		Point userPoint = new Point("user", lat, lng);
		userPoint.setType(PointType.USER);
		List<Path> paths = processor.getPaths(points, userPoint);

		
		BufferedImage image = processor.getImage(paths);

		writeImage(image, response);

	}
	
	private String getRange(String bound){
		int boundInteger=Integer.parseInt(bound);
		double b= 0.001*Math.pow(2, boundInteger);
		return b+","+b;
	}
	
	
	private void returnErrorImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String pathString = request.getRealPath("/");
		File file = new File(pathString + "/images/error.jpg");
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			writeImage(image, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	private void writeImage(BufferedImage image, HttpServletResponse response)
			throws IOException {
		response.setContentType("image/jpeg");
		OutputStream out = response.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}




}
