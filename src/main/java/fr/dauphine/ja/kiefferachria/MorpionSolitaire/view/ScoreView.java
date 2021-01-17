package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JButton five_D;
	private JButton five_T;
	
	public ScoreView(Score score) {
		super();
		this.score=score;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel title = new JLabel("Morpion Solitaire");
		title.setFont(new Font("default", Font.BOLD, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel joueur = new JLabel("Score du Joueur : ");
		JLabel computeur = new JLabel("Score du Computeur : ");
		this.score_joueur = new JLabel(this.score.getScore_joueur()+"");
		
		this.score_computeur = new JLabel(this.score.getScore_computeur()+"");
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.add(title,c);
		c.gridx=0;
		c.gridy=1;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.add(joueur,c);
		c.gridx=0;
		c.gridy=2;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.add(score_joueur,c);
		c.gridx=0;
		c.gridy=3;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.add(computeur,c);
		c.gridx=0;
		c.gridy=4;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.add(score_computeur,c);
		c.gridx=0;
		c.gridy=5;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.add(this.reset = new JButton("RESET"),c);
		c.gridx=0;
		c.gridy=6;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.five_D = new JButton("5D");
		this.add(this.five_D ,c);
		c.gridx=0;
		c.gridy=7;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.125;
		c.weighty = 0.125;
		this.five_T = new JButton("5T");
		this.add(this.five_T ,c);
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
	public JButton getFive_D() {
		return five_D;
	}
	public void setFive_D(JButton five_D) {
		this.five_D = five_D;
	}
	public JButton getFive_T() {
		return five_T;
	}
	public void setFive_T(JButton five_T) {
		this.five_T = five_T;
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
	
	

}
