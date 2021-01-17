package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid5T;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GridView;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GridView5T;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.ScoreView;

public class MouseController {

	private Grid5T grid;
	private GridView5T gridView;
	private ScoreView scoreView;
	
	public MouseController() {
		JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.setSize(new Dimension(1000, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Grid5T grid = new Grid5T(880,880,40);
		grid.catchCoordonnee();
		for(int i = 0 ; i<grid.getTabCoordonnee().size();i++) {
			System.out.println(grid.getTabCoordonnee().get(i));
		}
		System.out.println((int)grid.getNbColumn()/2);
		GridView5T d = new GridView5T(grid);
		this.gridView=d;
		
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.99;
		c.weighty =0.99;
		frame.add(d,c);
		
		
		this.scoreView = new ScoreView(grid.getScore());
		this.scoreView.getReset().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.reset();
				scoreView.reset();
				scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
				
			}
			
		});
		
		
		c.gridx=1;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.01;
		c.weighty = 0.01;
		frame.add(scoreView,c);
		
		
		frame.setVisible(true);
		frame.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
				grid.pointAvailable();
                Point z=grid.getNeigh(e.getX(), e.getY());
                grid.updateGrid(z,"player");
                
                //grid.updateGrid(z,"player");
                //grid.updateIA();
                gridView.repaint();
                
                scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
                
            }
		});
	}
	
	public static void main(String[] args) {
		MouseController g = new MouseController();
	}
	

}
