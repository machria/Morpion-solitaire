package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Choice extends JFrame {
	
	private JButton five_d;
	private JButton five_t;
	 	
	public Choice() {
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
		
		this.five_d.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Game();
				
			}
			
		});
		
		this.five_t.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Game5T();
				
			}
			
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Choice c = new Choice();
	}
	
}