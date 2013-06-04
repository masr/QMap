package com.passioncoder.qmap.servlet;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.jgrapht.Graph;

import com.passioncoder.qmap.ESRI.ESRIProcessor;
import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.drawing.Drawing;
import com.passioncoder.qmap.preprocess.GraphProcessor;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Servlet implementation class ShapeFile
 */
public class ShapeFile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<Integer, BufferedImage> imageMap;
	private int curId = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShapeFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		imageMap = new HashMap<Integer, BufferedImage>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("imgId") != null) {
			try {
				String imgIdString = request.getParameter("imgId");
				int imgId = Integer.parseInt(imgIdString);
				BufferedImage image = imageMap.get(imgId);
				this.writeImage(image, response);
				imageMap.remove(imgId);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
        
		Drawing.setMaxActualSize(1);
		String filePath = saveFile(request);
		ESRIProcessor processor = new ESRIProcessor();
		Graph<Point, Path> graph = processor.parseESRI(filePath);
		GraphProcessor graphProcessor = new GraphProcessor();
		BufferedImage image = graphProcessor.processData(graph);
		curId++;
		imageMap.put(curId, image);
		response.sendRedirect("/QMap/shapefile.jsp?imgId="+curId);
        
	}

	@SuppressWarnings("deprecation")
	private String saveFile(HttpServletRequest request) {
		String saveDirString = request.getRealPath("/") + "/shapefile/";
		String tmpDirString = request.getRealPath("/") + "/shapefile_tmp/";
		File saveDir = new File(saveDirString);
		File tmpDir = new File(tmpDirString);
         
		String shapFileName = (new Date()) + ""
				+ (int) (Math.random() * 100000);
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory dff = new DiskFileItemFactory();// 创建该对象
				dff.setRepository(tmpDir);// 指定上传文件的临时目录
				dff.setSizeThreshold(1024000);// 指定在内存中缓存数据大小,单位为byte
				ServletFileUpload sfu = new ServletFileUpload(dff);// 创建该对象
				sfu.setFileSizeMax(5000000);// 指定单个上传文件的最大尺寸
				sfu.setSizeMax(10000000);// 指定一次上传多个文件的总尺寸
				FileItemIterator fii = sfu.getItemIterator(request);// 解析request
				// 请求,并返回FileItemIterator集合
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();// 从集合中获得一个文件流
					if (!fis.isFormField() && fis.getName().length() > 0) {// 过滤掉表单中非文件域
						int index = fis.getName().lastIndexOf(".");// 取文件的扩展名
						String idd = index != -1 ? fis.getName().substring(
								index) : "";
						String formalName = shapFileName+idd;
						BufferedInputStream in = new BufferedInputStream(fis
								.openStream());// 获得文件输入流
						BufferedOutputStream out = new BufferedOutputStream(
								new FileOutputStream(new File(saveDir + "/"
										+ formalName)));// 获得文件输出流
						Streams.copy(in, out, true);// 开始把文件写到你指定的上传文件夹
					}
				}
				return saveDir + "/" + shapFileName;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void writeImage(BufferedImage image, HttpServletResponse response)
			throws IOException {
		response.setContentType("image/jpeg");
		OutputStream out = response.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
