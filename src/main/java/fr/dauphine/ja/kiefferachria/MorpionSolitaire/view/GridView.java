package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
			g.drawLine( this.grid.getStep()+i,this.grid.getStep(), this.grid.getStep()+i,this.grid.getHeight());
		g.setColor(Color.black);
		for (int i = 0; i < grid.getNbLine(); i++) {
			for (int j = 0; j < grid.getNbColumn(); j++) {
				if(grid.getPoints()[i][j])
					g.fillOval((int)(i*this.grid.getStep())-7, (int)(j*this.grid.getStep())-7, 15, 15);
			}
		}
		
		g.setColor(Color.red);
		for (int i = 0;i < grid.getTabLine().size();i++) {
			g.drawLine((int)grid.getTabLine().get(i).getP1().getX(), (int)grid.getTabLine().get(i).getP1().getY(), (int)grid.getTabLine().get(i).getP5().getX(), (int)grid.getTabLine().get(i).getP5().getY());
		}
		g.setColor(Color.white);
		int co=0;
		this.setFont(new Font("default", Font.BOLD, 12));
		for( int i=0;i<grid.getPointUser().size();i++) {
			co=i+1;
			g.drawString(""+co, grid.getPointUser().get(i).x-7, grid.getPointUser().get(i).y+7);
		}
		
	}
	
	
}
