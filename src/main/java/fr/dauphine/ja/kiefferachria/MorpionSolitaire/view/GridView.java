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
		for(int i=0; i <600;i=i+40)
			g.drawLine(0, 40+i, 600, 40+i);
		for(int i=0; i <600;i=i+40)
			g.drawLine( 40+i,0, 40+i,600);
		g.setColor(Color.red);
		g.drawLine(40, 80, 0, 40);

	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Morpion Solitaire");// Création d'une frame avec un nom qui sera le nom
		frame.setLayout(new GridLayout());
								// de la fenetre
		frame.setSize(new Dimension(600, 600));// Changement de taille de la fenetre
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Action à faire si on clique sur le close
		Grid grid = new Grid();
		GridView d = new GridView(grid);// Création de notre nouvelle objet
		frame.add(d);// On met notre objet dans la fenetre
		
		frame.setVisible(true);
	}
}
