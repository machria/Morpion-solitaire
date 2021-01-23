package fr.dauphine.ja.kiefferachria.MorpionSolitaire.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.view.ChoiceView;


/**
 * This class allows to choose the 5T or 5D game engine.
 * @author floryan/majid
 *
 */
public class Choice {
	
	private ChoiceView choice;
	
	public Choice() {
		this.choice = new ChoiceView();
		this.choice.getFive_d().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Game5D();
				
			}
			
		});
		
		this.choice.getFive_t().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Game5T();
				
			}
			
		});
	}

	/**
	 * Game launch.
	 * @param args not used
	 */
	public static void main(String[] args) {
		new Choice();
	}
	
}
