package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Direction;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Grid5T;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.GridView5T;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.MenuView;


/**
 * This class is the game controller associated with the join five game 5T.
 * @author floryan/majid
 *
 */
public class Game5T {
	
	/**
	 * 5T game board instance.
	 */
	private final Grid5T grid;
	/**
	 * View of the 5T grid.
	 */
	private GridView5T gridView;
	/**
	 * View of score (also contains buttons)
	 */
	private MenuView scoreView;
	
	/**
	 * Creation of the window that will allow you to play the game and use the associated features.
	 */
	public Game5T() {
		final JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.setSize(new Dimension(1000, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.grid = new Grid5T(880,880,40);
		grid.catchCoordonnee();
		GridView5T d = new GridView5T(grid);
		this.gridView=d;
		
		
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.99;
		c.weighty =0.99;
		frame.add(d,c);
		
		
		this.scoreView = new MenuView(grid.getScore());
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
				while(grid.getPointUser().size()<20) {
					grid.reset();
					while(!grid.getPotentialMove().isEmpty()) {
						grid.updateIA();
						grid.pointAvailable();
		                gridView.repaint();
		                scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
		                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
		                
					}
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
					ArrayList<Point> listP = new ArrayList<Point>();
					listP.addAll(grid.getPointUser().keySet());
					for(int i = 0;i<listP.size();i++) {
							String s = ""+listP.get(i).getX()+";"+listP.get(i).getY()+";"+grid.getPointUser().get(listP.get(i))+"\n";
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
				        Direction d = Direction.valueOf(data.split(";")[2]);
				        grid.pointAvailable();
				        grid.updateGrid(x,d,"player");
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
		this.scoreView.getScoreDistrib().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Distribution(grid.getScoreHistory());
			}
			
		});
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
				grid.pointAvailable();
				if(grid.getPotentialMove().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No point available");
				}else {
					 Point z=grid.getNeigh(e.getX(), e.getY());
		                grid.updateGrid(z, "player");
		                gridView.repaint();
		                
		                scoreView.getScore_computeur().setText(grid.getScore().getScore_computeur()+"");
		                scoreView.getScore_joueur().setText(grid.getScore().getScore_joueur()+"");
				}
                
            }
		});
	}
	
}
