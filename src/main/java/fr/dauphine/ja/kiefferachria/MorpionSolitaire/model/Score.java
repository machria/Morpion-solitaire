package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

/**
 * This class is the model for scores. There is player score and computer score.
 * @author floryan
 *
 */
public class Score {
	
	private int score_computeur;
	private int score_joueur;
	
	
	public Score() {
		this.score_computeur=0;
		this.score_joueur=0;
	}
	
	public int getScore_computeur() {
		return score_computeur;
	}
	public void setScore_computeur(int score_computeur) {
		this.score_computeur = score_computeur;
	}
	public int getScore_joueur() {
		return score_joueur;
	}
	public void setScore_joueur(int score_joueur) {
		this.score_joueur = score_joueur;
	}
	
	

}
