package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Line;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GridView;

public class Game extends JFrame{

	private Grid grid;
	private GridView gridView;
	public Game() {
		JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridLayout());
		frame.setSize(new Dimension(800, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Grid grid = new Grid(800,800,40);
		grid.catchCoordonnee();
		for(int i = 0 ; i<grid.getTabCoordonnee().size();i++) {
			System.out.println(grid.getTabCoordonnee().get(i));
		}
		System.out.println((int)grid.getNbColumn()/2);
		GridView d = new GridView(grid);
		this.gridView=d;
		
		frame.add(d);

		frame.setVisible(true);
		frame.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
				grid.pointAvailable();
                Point z=grid.getNeigh(e.getX(), e.getY());
                grid.updateGrid(z);
                
                grid.updateGrid(z);
                grid.updateIA();
                gridView.repaint();
            }
		});
	}
	
	public static void main(String[] args) {
		Game g = new Game();
	}
	
}
