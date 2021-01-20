package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Graph;

public class GraphView extends JPanel{
	private Graph graph;

	public GraphView(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		
		for (int i = 0; i < graph.getPoint().size(); i++) {
			
			g.fillOval(graph.getPoint().get(i).x, graph.getPoint().get(i).y, 3, 3);
			
		}
		g.drawLine(0, graph.max()*2, graph.getPoint().size()*10, graph.max()*2);
	}
	

}
