package fr.dauphine.ja.kiefferachria.MorpionSolitaire.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import fr.dauphine.ja.kiefferachria.MorpionSolitaire.model.Score;

/**
 * This class is used to create the Menu view. It will contain the player's score, the computer score 
 * and the following buttons: Help, Solution, Distribution, Save and Import.
 * @author floryan/majid
 *
 */
public class MenuView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Score score;
	private JLabel score_joueur;
	private JLabel score_computer;
	private JButton reset;
	private JButton help;
	private JButton solution;
	private JButton sauvegarder;
	private JButton importer;
	private JLabel title;
	private JButton scoreDistrib;
	
	
	/**
	 * Creation of the JPanel scoreView.
	 * @param score Object score
	 */
	public MenuView(Score score) {
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
		
		this.score_computer = new JLabel(this.score.getScore_computeur()+"");
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
		this.add(score_computer,c);
		c.gridx=0;
		c.gridy=5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.add(this.reset = new JButton("RESET",new ImageIcon("reset.png")),c);
		c.gridx=0;
		c.gridy=6;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.help = new JButton("HELP",new ImageIcon("help.png"));
		this.add(this.help ,c);
		c.gridx=0;
		c.gridy=7;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.solution = new JButton("SOLUTION",new ImageIcon("solution.png"));
		this.add(this.solution ,c);
		c.gridx=0;
		c.gridy=8;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.sauvegarder = new JButton("SAVE",new ImageIcon("save2.png"));
		this.add(this.sauvegarder  ,c);
		c.gridx=0;
		c.gridy=9;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.importer = new JButton("IMPORT",new ImageIcon("import.png") );
		this.add(this.importer ,c);

		c.gridx=0;
		c.gridy=10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		this.scoreDistrib = new JButton("DISTRIBUTION", new ImageIcon("distribution.png"));
		this.add(this.scoreDistrib,c);
		this.setBackground(Color.CYAN);
		
	}
	
	/**
	 * Allows you to set the score of the player and the computer to 0.
	 */
	public void reset() {
		this.score.setScore_computeur(0);
		this.score.setScore_joueur(0);
	}
	
	/**
	 * Return the reset JButton.
	 * @return JButton reset.
	 */
	public JButton getReset() {
		return reset;
	}
	
	/**
	 * Return the player's score.
	 * @return score player
	 */
	public JLabel getScore_joueur() {
		return score_joueur;
	}
	
	/**
	 * Change the player's score.
	 * @param score_joueur score of the player
	 */
	public void setScore_joueur(JLabel score_joueur) {
		this.score_joueur = score_joueur;
	}
	
	/**
	 * Return the computer's score.
	 * @return score_computer
	 */
	public JLabel getScore_computeur() {
		return score_computer;
	}
	
	/**
	 * Change the computer's score.
	 * @param score_computer score of the computer
	 */
	public void setScore_computeur(JLabel score_computer) {
		this.score_computer = score_computer;
	}
	
	/**
	 * Return the save JButton
	 * @return JButton save
	 */
	public JButton getSauvegarder() {
		return sauvegarder;
	}
	
	/**
	 * Return the help JButton
	 * @return JButton help
	 */
	public JButton getHelp() {
		return help;
	}

	/**
	 * Return the solution JButton
	 * @return JButton solution
	 */
	public JButton getSolution() {
		return solution;
	}

	/**
	 * Return the import JButton
	 * @return JButton import
	 */
	public JButton getImporter() {
		return importer;
	}

	/**
	 * Return the score distribution JButton
	 * @return JButton score distribution
	 */
	public JButton getScoreDistrib() {
		return scoreDistrib;
	}
}
