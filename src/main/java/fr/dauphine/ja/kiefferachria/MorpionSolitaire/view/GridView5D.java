package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid5D;

/**
 * This class is used to create the grid view 5D.
 * @author floryan/majid
 *
 */
public class GridView5D extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 5D game board instance.
	 */
	private Grid5D grid;
	
	/**
	 * Constructor which receive the model
	 * @param grid represent grid5D
	 */
	public GridView5D(Grid5D grid) {
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
		ArrayList<Point> listP = new ArrayList<Point>();
		listP.addAll(grid.getPointUser().keySet());
		
		for( int i=0;i<listP.size();i++) {
			co=i+1;
			g.drawString(""+co, listP.get(i).x-7, listP.get(i).y+7);
		}
		
	}
	
	
}
