package com.passioncoder.qmap.preprocess;

import java.awt.image.BufferedImage;
import java.util.List;

import org.jgrapht.Graph;

import com.passioncoder.qmap.algorithm.Path;
import com.passioncoder.qmap.algorithm.Point;
import com.passioncoder.qmap.algorithm.SchematicMapGenerator;
import com.passioncoder.qmap.drawing.Drawing;
import com.passioncoder.qmap.drawing.DrawingGenerator;

public class GraphProcessor {
	public BufferedImage processData(Graph<Point, Path> graph) {
		SchematicMapGenerator generator = new SchematicMapGenerator();
		graph = generator.getSchematicMap(graph);
		DrawingGenerator drawing = new DrawingGenerator();
		BufferedImage image = drawing.generateImage(graph);
		return image;
	}
}
 