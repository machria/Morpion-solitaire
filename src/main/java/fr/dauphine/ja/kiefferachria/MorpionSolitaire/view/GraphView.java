package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Font;
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
		
		for (int i = 0; i < graph.getPoint().size()-1; i++) {
			g.setColor(Color.black);
			g.fillOval(graph.getPoint().get(i).x-2, graph.getPoint().get(i).y-2, 5, 5);
			g.drawLine(graph.getPoint().get(i).x, graph.getPoint().get(i).y, graph.getPoint().get(i+1).x, graph.getPoint().get(i+1).y);
			g.setColor(Color.red);
			this.setFont(new Font("default", Font.BOLD, 8));
			g.drawString(i+"", (i*20)+5, (graph.max()*2)+10);
			
		}
		g.setColor(Color.black);
		g.fillOval(graph.getPoint().get(graph.getPoint().size()-1).x-2, graph.getPoint().get(graph.getPoint().size()-1).y-2, 5, 5);
		g.setColor(Color.red);

		g.drawString(graph.getPoint().size()-1+"", ((graph.getPoint().size()-1)*20)+5, (graph.max()*2)+10);

		g.setColor(Color.black);
		g.drawLine(0, graph.max()*2, graph.getPoint().size()*20, graph.max()*2);
		
		
		g.setColor(Color.darkGray);
		g.drawLine(0, graph.max()*2-(graph.max()), ((graph.getPoint().size()-1)*20)+5, graph.max()*2-(graph.max()));
		
		g.setColor(Color.black);
		g.drawString("Best score : "+graph.max(), (((graph.getPoint().size()-1)*20)+5)/2, graph.max()*2-(graph.max())-5);
		
		
	}
	

}
