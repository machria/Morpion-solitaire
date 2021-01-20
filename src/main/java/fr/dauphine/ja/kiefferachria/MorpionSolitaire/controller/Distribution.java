package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Graph;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GraphView;

public class Distribution extends JFrame{

	public Distribution(ArrayList<Integer> toplot) {
		JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridLayout());
		
		Graph g = new Graph(toplot);
		
		GraphView gView = new GraphView(g);
		System.out.println(g.max()*2);
		frame.setSize((g.getPoint().size()*20)*2, (g.max()*4));
		
		frame.add(gView);
		frame.setVisible(true);
		
	}
	
	
}
