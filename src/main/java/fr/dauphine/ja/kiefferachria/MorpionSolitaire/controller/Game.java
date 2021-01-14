package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.setSize(new Dimension(1000, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Grid grid = new Grid(880,880,40);
		grid.catchCoordonnee();
		for(int i = 0 ; i<grid.getTabCoordonnee().size();i++) {
			System.out.println(grid.getTabCoordonnee().get(i));
		}
		System.out.println((int)grid.getNbColumn()/2);
		GridView d = new GridView(grid);
		this.gridView=d;
		
		c.gridx=1;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.01;
		c.weighty = 0.01;
		frame.add(new JButton(),c);
		
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.99;
		c.weighty =0.99;
		frame.add(d,c);
		
		
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
