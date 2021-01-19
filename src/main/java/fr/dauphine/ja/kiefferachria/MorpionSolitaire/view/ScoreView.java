package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Score;

public class ScoreView extends JPanel {
	
	Score score;
	private JLabel score_joueur;
	private JLabel score_computeur;
	private JButton reset;
	private JButton help;
	private JButton solution;
	private JButton five_d;
	private JButton five_t;
	private JLabel title;
	
	public ScoreView(Score score) {
		super();
		this.score=score;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.title = new JLabel("Morpion Solitaire");
		this.title.setFont(new Font("default", Font.BOLD, 20));
		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel joueur = new JLabel("Score du Joueur : ");
		JLabel computeur = new JLabel("Score du Computeur : ");
		this.score_joueur = new JLabel(this.score.getScore_joueur()+"");
		
		this.score_computeur = new JLabel(this.score.getScore_computeur()+"");
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.add(title,c);
		c.gridx=0;
		c.gridy=1;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.add(joueur,c);
		c.gridx=0;
		c.gridy=2;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.add(score_joueur,c);
		c.gridx=0;
		c.gridy=3;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.add(computeur,c);
		c.gridx=0;
		c.gridy=4;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.add(score_computeur,c);
		c.gridx=0;
		c.gridy=5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.add(this.reset = new JButton("RESET"),c);
		c.gridx=0;
		c.gridy=6;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.help = new JButton("HELP");
		this.add(this.help ,c);
		c.gridx=0;
		c.gridy=7;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.solution = new JButton("SOLUTION");
		this.add(this.solution ,c);
		c.gridx=0;
		c.gridy=8;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.five_d = new JButton("Jouer en 5D");
		this.add(this.five_d  ,c);
		c.gridx=0;
		c.gridy=9;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.five_t = new JButton("Jouer en 5T");
		this.add(this.five_t ,c);
		this.setBackground(Color.CYAN);
		
	}
	
	public void reset() {
		this.score.setScore_computeur(0);
		this.score.setScore_joueur(0);
	}
	
	public JButton getReset() {
		return reset;
	}
	public void setReset(JButton reset) {
		this.reset = reset;
	}
	
	
	public JLabel getScore_joueur() {
		return score_joueur;
	}
	public void setScore_joueur(JLabel score_joueur) {
		this.score_joueur = score_joueur;
	}
	public JLabel getScore_computeur() {
		return score_computeur;
	}
	public void setScore_computeur(JLabel score_computeur) {
		this.score_computeur = score_computeur;
	}

	public JButton getHelp() {
		// TODO Auto-generated method stub
		return this.help;
	}

	public JButton getSolution() {
		// TODO Auto-generated method stub
		return this.solution;
	}
	
	

}
