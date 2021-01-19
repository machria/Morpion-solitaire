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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid5T;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GridView;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GridView5T;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.ScoreView;

public class Game5T {

	private Grid5T grid;
	private GridView5T gridView;
	private ScoreView scoreView;
	
	public Game5T() {
		final JFrame frame = new JFrame("Morpion Solitaire");
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
		
		this.scoreView.getHelp().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.pointAvailable();
				grid.NMCS();
                gridView.repaint();
                scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");

			}
			
		});
		this.scoreView.getSolution().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.pointAvailable();
				while(!grid.getPotentialMove().isEmpty()) {
					grid.NMCS();
					grid.pointAvailable();
	                gridView.repaint();
	                scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
	                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
				}
				
			}
			
		});
		
		
		c.gridx=1;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.01;
		c.weighty = 0.01;
		frame.add(scoreView,c);
		
		scoreView.getSauvegarder().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(frame);
				File f = fc.getSelectedFile();
				try {
					FileWriter fw = new FileWriter(f);
					for(int i = 0;i<grid.getNbLine();i++) {
						for(int j = 0; j<grid.getNbColumn();j++) {
							if(grid.getPoints()[i][j]==true) {
								String s = "("+i*grid.getStep()+";"+j*grid.getStep()+")";
								fw.write(s);
							}
						}
					}
					fw.close();
					
				}
				catch(IOException e1) {
					System.out.println(e1);
				}
			}
			
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
				grid.pointAvailable();
                Point z=grid.getNeigh(e.getX(), e.getY());
                grid.updateGrid(z,"player");
                gridView.repaint();

                //grid.NMCS();
                //grid.updateIA();
                gridView.repaint();
                
                scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
                
            }
		});
	}
	
}
