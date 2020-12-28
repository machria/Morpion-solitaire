package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid;

public class GridView extends JPanel {

	private Grid grid;
	
	public GridView(Grid grid) {
		this.grid = grid;
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.darkGray);
		for(int i=0; i <this.grid.getWidth();i=i+this.grid.getStep())
			g.drawLine(0, this.grid.getStep()+i, this.grid.getWidth(), this.grid.getStep()+i);
		for(int i=0; i <this.grid.getHeight();i=i+this.grid.getStep())
			g.drawLine( this.grid.getStep()+i,0, this.grid.getStep()+i,this.grid.getHeight());
		g.setColor(Color.black);
		
		g.fillOval((int)this.grid.firstPoint().getX()-5, (int)this.grid.firstPoint().getY()-5, 10, 10);
		
		for (Point p:grid.getTabCross()) {
			g.fillOval((int)p.getX()-5, (int)p.getY()-5, 10, 10);

		}
		//System.out.println(((int)240/this.grid.getStep()));
		int c=0;
		for (int i = 0; i < grid.getNbLine(); i++) {
			for (int j = 0; j < grid.getNbColumn(); j++) {
				if(grid.getPoints()[i][j])
					c++;
			}
		}
		System.out.println(c);
		 
		
		
		/*
		g.fillOval(240-5, 240-5, 10, 10);
		g.fillOval(240-5, 200-5, 10, 10);
		g.fillOval(240-5, 160-5, 10, 10);
		g.fillOval(240-5, 120-5, 10, 10);
		g.fillOval(280-5, 120-5, 10, 10);
		g.fillOval(320-5, 120-5, 10, 10);
		g.fillOval(360-5, 120-5, 10, 10);
		g.fillOval(360-5, 160-5, 10, 10);
		g.fillOval(360-5, 200-5, 10, 10);
		g.fillOval(360-5, 240-5, 10, 10);
		g.fillOval(400-5, 240-5, 10, 10);
		g.fillOval(440-5, 240-5, 10, 10);
		
		g.fillOval(480-5, 240-5, 10, 10);
		g.fillOval(480-5, 280-5, 10, 10);
		g.fillOval(480-5, 320-5, 10, 10);
		g.fillOval(480-5, 360-5, 10, 10);
		*/
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridLayout());
		frame.setSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Grid grid = new Grid(1000,1000,40);
		grid.catchCoordonnee();
		for(int i = 0 ; i<grid.getTabCoordonnee().size();i++) {
			System.out.println(grid.getTabCoordonnee().get(i));
		}
		System.out.println((int)grid.getNbColumn()/2);
		GridView d = new GridView(grid);
		frame.add(d);
		
		frame.setVisible(true);
	}
}
