package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Graph;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GraphView;

public class Distribution extends JFrame{

	public Distribution() {
		JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridLayout());
		ArrayList<Integer> toplot = new ArrayList<Integer>();
		toplot.add(8);
		toplot.add(50);
		toplot.add(70);
		toplot.add(8);
		toplot.add(50);
		toplot.add(70);
		Graph g = new Graph(toplot);
		for (int i = 0;i<g.getPoint().size();i++) {
			System.out.println(g.getPoint().get(i));
		}
		GraphView gView = new GraphView(g);
		System.out.println(g.max()*2);
		frame.setSize((g.getPoint().size()*10), (g.max()*2));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gView);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		Distribution c = new Distribution();
	}
}
