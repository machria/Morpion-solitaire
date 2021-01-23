package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class ChoiceView extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This button allows you to choose the 5D mode.
	 */
	private JButton five_d;
	/**
	 * This button allows you to choose the 5T mode.
	 */
	private JButton five_t;
	
	/**
	 * Window constructor which selects the game mode.
	 */
	public ChoiceView() {
		JFrame frame = new JFrame("Morpion Solitaire");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.setSize(new Dimension(300, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		this.five_d = new JButton("Jouer en 5D");
		frame.add(five_d,c);
		c.gridx=0;
		c.gridy=1;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		this.five_t = new JButton("Jouer en 5T");
		frame.add(five_t,c);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public JButton getFive_d() {
		return five_d;
	}

	public void setFive_d(JButton five_d) {
		this.five_d = five_d;
	}

	public JButton getFive_t() {
		return five_t;
	}

	public void setFive_t(JButton five_t) {
		this.five_t = five_t;
	}
	
	
}
