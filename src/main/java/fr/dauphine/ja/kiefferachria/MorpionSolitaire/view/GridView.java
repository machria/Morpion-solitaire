package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

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
		g.setColor(Color.red);
		g.drawLine(40, 80, 0, 40);

	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridLayout());
		frame.setSize(new Dimension(600, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Grid grid = new Grid(600,600,40);
		grid.catchCoordonnee();
		for(int i = 0 ; i<grid.getTabCoordonnee().size();i++) {
			System.out.println(grid.getTabCoordonnee().get(i));
		}
		GridView d = new GridView(grid);
		frame.add(d);
		
		frame.setVisible(true);
	}
}
