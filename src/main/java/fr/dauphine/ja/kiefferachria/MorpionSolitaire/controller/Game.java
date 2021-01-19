package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Line;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Score;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GridView;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.ScoreView;

public class Game extends JFrame{

	private Grid grid;
	private GridView gridView;
	private ScoreView scoreView;
	
	public Game() {
		final JFrame frame = new JFrame("Morpion Solitaire");
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
		
		scoreView.getSauvegarder().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(frame);
				File f = fc.getSelectedFile();
				try {
					FileWriter fw = new FileWriter(f);
					for(int i = 0;i<grid.getPointUser().size();i++) {
							String s = ""+grid.getPointUser().get(i).getX()+";"+grid.getPointUser().get(i).getY()+"\n";
							fw.write(s);
						
					}
					fw.close();
					
				}
				catch(IOException e1) {
					System.out.println(e1);
				}
			}
			
		});
		
		this.scoreView.getImporter().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.reset();
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(frame);
				File f = fc.getSelectedFile();
				try {
					Scanner myReader = new Scanner(f);
				    while (myReader.hasNextLine()) {
				        String data = myReader.nextLine();
				        Point x = new Point((int)Double.parseDouble(data.split(";")[0]),(int)Double.parseDouble(data.split(";")[1]));
				        grid.pointAvailable();
				        grid.updateGrid(x, "player");
				        scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
		                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
				    }
					myReader.close();
				}
				catch(IOException e1) {
					System.out.println(e1);
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
		
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.99;
		c.weighty =0.99;
		frame.add(d,c);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
				grid.pointAvailable();
                Point z=grid.getNeigh(e.getX(), e.getY());
                grid.updateGrid(z, "player");
                //grid.NMCS();
                gridView.repaint();
                
                scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
                
            }
		});
		
		
	}
	
}
