
package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class is the model of the game board associated with the game of join five 5T.
 * 
 * @author floryan/majid
 *
 */
public class Grid5T extends Grid {

	
	/**
	 * HashMap of all possible movements for a given point.
	 */
	private HashMap<Point, HashMap<Direction, Integer>> tabUsed;


	/**
	 * 5T grid constructor.
	 * 
	 * @param h (heigth)
	 * @param w (width)
	 * @param step (gap)
	 */
	public Grid5T(int h, int w, int step) {
		super(h,w,step);
		this.reset();

	}
	
/////////////////////////
//					   //	
// Getters and Setters //
//					   //
/////////////////////////
	
	public HashMap<Point, HashMap<Direction, Integer>> getTabUsed() {
		return tabUsed;
	}

	public void setTabUsed(HashMap<Point, HashMap<Direction, Integer>> tabUsed) {
		this.tabUsed = tabUsed;
	}

////////////////////////
//					  //
// Game setup methods //
//					  //
////////////////////////
	
	/**
	 * Initiate instance of class
	 */
	public void initiatePoint() {
		HashMap<Direction, Integer> dir = new HashMap<Direction, Integer>();
		dir.put(Direction.VERTICAL, 0);
		dir.put(Direction.HORIZONTAL, 0);
		dir.put(Direction.DIAGLEFT, 0);
		dir.put(Direction.DIAGRIGHT, 0);
		getPossibleDirection().put(Direction.VERTICAL_BOTTOM, false);
		getPossibleDirection().put(Direction.VERTICAL_TOP, false);

		getPossibleDirection().put(Direction.HORIZONTAL_LEFT, false);
		getPossibleDirection().put(Direction.HORIZONTAL_RIGHT, false);

		getPossibleDirection().put(Direction.DIAGLEFT_TOPLEFT, false);
		getPossibleDirection().put(Direction.DIAGLEFT_BOTTOMRIGHT, false);

		getPossibleDirection().put(Direction.DIAGRIGHT_BOTTOMLEFT, false);
		getPossibleDirection().put(Direction.DIAGRIGHT_TOPRIGHT, false);

		for (int i = 0; i < getNbLine(); i++) {
			for (int j = 0; j < getNbColumn(); j++) {
				this.getPoints()[i][j] = false;
				this.tabUsed.put(new Point(i * getStep(), j * getStep()), (HashMap<Direction, Integer>) dir.clone());
			}
		}

	}	


	/**
	 * Reset all instances of Grid5T Object.
	 */
	public void reset() {
    	this.setNbLine((int)getWidth()/getStep());
    	this.setNbColumn((int)getHeight()/getStep());
    	if(!(this.getPointUser()==null))
    		this.getScoreHistory().add(this.getPointUser().size());
    	this.setPotentialMove(new ArrayList<Point>());
    	this.setPotentialMoveNext(new ArrayList<Point>());
    	this.setPossibleDirection(new HashMap<Direction,Boolean>(4));

    	this.setPoints(new boolean [getNbLine()][getNbColumn()]);
    	this.setTabCoordonnee(new ArrayList<Point>());
    	this.setCenter((int) getNbLine()/2);//Same height and width
    	this.setTabCross(new ArrayList<Point>());
    	this.setTabLine(new ArrayList<Line>());
    	this.tabUsed = new HashMap<Point, HashMap<Direction, Integer>>();
    	this.initiatePoint();
    	this.generateCross();
    	this.catchCoordonnee();
    	this.setPointUser(new LinkedHashMap<Point, Direction>());
    	this.setScore(new Score());
	}
	

	
//////////////////////////
// 						//
// Verification methods //
//						//
//////////////////////////
	
	/**
	 * This method allows to verify if an horizontal movement is possible when a player makes a choice.
	 * 
	 * @param z (Point)
	 * @return boolean
	 */
	public boolean checkPossibleMoveHorizontal(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (coordX - i >= 0 && coordY >= 0) {
				if (!this.getPoints()[coordX - i][coordY] || this.tabUsed
						.get(new Point((coordX - i) * getStep(), coordY * getStep())).get(Direction.HORIZONTAL) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {
						if (coordX + a < getNbColumn() && coordY >= 0) {
							if (!this.getPoints()[coordX + a][coordY]
									|| this.tabUsed.get(new Point((coordX + a) * getStep(), coordY * getStep()))
											.get(Direction.HORIZONTAL) == 2) {

								return false;
							} else {
								cpt_droite++;
								if (cpt_gauche + cpt_droite == 4) {
									a = 6;
									i = 6;
								}
							}
						}

					}
				} else {
					cpt_gauche++;
					if (cpt_gauche == 4) {
						i = 6;
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * getStep(), (coordY * getStep())))
						.get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * getStep(), (coordY * getStep())))
						.get(Direction.HORIZONTAL) < 2) {

			if (cpt_gauche + cpt_droite == 4) {
				return true;
			}

		}

		return false;
	}
	
	/**
	 * This method allows to verify if a vertical movement is possible when a player makes a choice.
	 * 
	 * @param z (Point)
	 * @return boolean
	 */
	public boolean checkPossibleMoveVertical(Point z) {
		int cpt_haut = 0;
		int cpt_bas = 0;
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (coordX >= 0 && coordY - i >= 0) {

				if (!this.getPoints()[coordX][coordY - i] || this.tabUsed
						.get(new Point(coordX * getStep(), (coordY - i) * getStep())).get(Direction.VERTICAL) == 2) {
					for (int a = 1; a <= 4 - cpt_haut; a++) {
						if (coordX >= 0 && coordY + a < getNbColumn()) {
							if (!this.getPoints()[coordX][coordY + a]) {

								return false;
							} else {
								cpt_bas++;
								if (cpt_haut + cpt_bas == 4) {
									a = 10;
									i = 10;
								}
							}
						}

					}
				} else {
					if (this.tabUsed.get(new Point(coordX * getStep(), (coordY - i) * getStep()))
							.get(Direction.VERTICAL) < 2) {
						cpt_haut++;

						if (cpt_haut == 4) {
							i = 6;
						}
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * getStep()), (coordY - cpt_haut) * getStep()))
						.get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * getStep()), (coordY + cpt_bas) * getStep()))
						.get(Direction.VERTICAL) < 2) {
			if (cpt_haut + cpt_bas == 4) {
				return true;
			}
		}
		return false;

	}

	/**
	 * This method allows to verify if a left diagonal movement is possible when a player makes a choice.
	 * Left Diagonal movement : diagonal from the top left.
	 * 
	 * @param z (Point)
	 * @return boolean
	 */
	public boolean checkPossibleMoveDiagonalLeft(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (coordX - i >= 0 && coordY - i >= 0) {

				if (!this.getPoints()[coordX - i][coordY - i]
						|| this.tabUsed.get(new Point((coordX - i) * getStep(), (coordY - i) * getStep()))
								.get(Direction.DIAGLEFT) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {

						if (coordX + a < getNbColumn() && coordY + a < getNbColumn()) {
							if (!this.getPoints()[coordX + a][coordY + a]) {

								return false;
							} else {
								cpt_droite++;
								if (cpt_gauche + cpt_droite == 4) {
									a = 10;
									i = 10;
								}
							}
						}

					}
				} else {
					if (this.tabUsed.get(new Point((coordX - i) * getStep(), (coordY - i) * getStep()))
							.get(Direction.DIAGLEFT) < 2) {
						cpt_gauche++;

						if (cpt_gauche == 4) {
							i = 6;
						}
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep()))
						.get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * getStep(), (coordY + cpt_droite) * getStep()))
						.get(Direction.DIAGLEFT) < 2) {
			if (cpt_gauche + cpt_droite == 4) {
				return true;
			}
		}
		return false;

	}

	/**
	 * This method allows to verify if a right diagonal movement is possible when a player makes a choice.
	 * Right Diagonal movement : diagonal from the top right.
	 *
	 * @param z (Point)
	 * @return boolean
	 */
	public boolean checkPossibleMoveDiagonalRight(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (coordX + i < getNbColumn() && coordY - i >= 0) {

				if (!this.getPoints()[coordX + i][coordY - i]
						|| this.tabUsed.get(new Point((coordX + i) * getStep(), (coordY - i) * getStep()))
								.get(Direction.DIAGRIGHT) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {

						if (coordX - a >= 0 && coordY + a < getNbColumn()) {
							if (!this.getPoints()[coordX - a][coordY + a]) {

								return false;
							} else {
								cpt_droite++;
								if (cpt_gauche + cpt_droite == 4) {
									a = 10;
									i = 10;
								}
							}
						}

					}
				} else {
					if (this.tabUsed.get(new Point((coordX + i) * getStep(), (coordY - i) * getStep()))
							.get(Direction.DIAGRIGHT) < 2) {
						cpt_gauche++;

						if (cpt_gauche == 4) {
							i = 6;
						}
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep()))
						.get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_droite) * getStep(), (coordY + cpt_droite) * getStep()))
						.get(Direction.DIAGRIGHT) < 2) {
			if (cpt_gauche + cpt_droite == 4) {
				return true;
			}
		}
		return false;

	}

////////////////////////////////////////////////////////////////////
//                                                                //
// Methods that allow you to draw the lines when they are correct //
//                                                                //
////////////////////////////////////////////////////////////////////
	
	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a right diagonal line) 
	 * if the point chosen by the player is valid.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveDiagonalRight(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null, null, null, null, null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {

			if (!this.getPoints()[coordX + i][coordY - i] || this.tabUsed
					.get(new Point((coordX + i) * getStep(), (coordY - i) * getStep())).get(Direction.DIAGRIGHT) == 2) {
				for (int a = 1; a <= 4 - cpt_gauche; a++) {

					if (!this.getPoints()[coordX - a][coordY + a]) {

						return;
					} else {
						cpt_droite++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							

						}
						

					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX + i) * getStep(), (coordY - i) * getStep()))
						.get(Direction.DIAGRIGHT) < 2) {
					cpt_gauche++;

					if (cpt_gauche == 4) {
						i = 6;



					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep()))
						.get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_droite) * getStep(), (coordY + cpt_droite) * getStep()))
						.get(Direction.DIAGRIGHT) < 2) {


			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX + cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep());
				fin = new Point((coordX - cpt_droite) * getStep(), (coordY + cpt_droite) * getStep());



				if (this.tabUsed.get(debut).get(Direction.DIAGRIGHT) == 1) {
					this.tabUsed.get(debut).replace(Direction.DIAGRIGHT, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.DIAGRIGHT, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.DIAGRIGHT) == 1) {
					this.tabUsed.get(fin).replace(Direction.DIAGRIGHT, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.DIAGRIGHT, 0, 1);

				}



				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX + i) * getStep(), ((coordY - i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);


					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX - i) * getStep(), ((coordY + i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);


					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					

					this.tabUsed.get(z).replace(Direction.DIAGRIGHT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				

			}
		}
		

	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a left diagonal line) 
	 * if the point chosen by the player is valid.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveDiagonalLeft(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null, null, null, null, null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {

			if (!this.getPoints()[coordX - i][coordY - i] || this.tabUsed
					.get(new Point((coordX - i) * getStep(), (coordY - i) * getStep())).get(Direction.DIAGLEFT) == 2) {
				for (int a = 1; a <= 4 - cpt_gauche; a++) {

					if (!this.getPoints()[coordX + a][coordY + a]) {

						return;
					} else {
						cpt_droite++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							

						}
						

					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX - i) * getStep(), (coordY - i) * getStep()))
						.get(Direction.DIAGLEFT) < 2) {
					cpt_gauche++;

					if (cpt_gauche == 4) {
						i = 6;
						

					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep()))
						.get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * getStep(), (coordY + cpt_droite) * getStep()))
						.get(Direction.DIAGLEFT) < 2) {


			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX - cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep());
				fin = new Point((coordX + cpt_droite) * getStep(), (coordY + cpt_droite) * getStep());
				

				if (this.tabUsed.get(debut).get(Direction.DIAGLEFT) == 1) {
					this.tabUsed.get(debut).replace(Direction.DIAGLEFT, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.DIAGLEFT, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.DIAGLEFT) == 1) {
					this.tabUsed.get(fin).replace(Direction.DIAGLEFT, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.DIAGLEFT, 0, 1);

				}
				

				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * getStep(), ((coordY - i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);


					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * getStep(), ((coordY + i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);


					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					
					this.tabUsed.get(z).replace(Direction.DIAGLEFT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				
			}
		}
		
	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a vertical line) 
	 * if the point chosen by the player is valid.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveVertical(Point z) {
		int cpt_haut = 0;
		int cpt_bas = 0;
		Line line = new Line(null, null, null, null, null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (!this.getPoints()[coordX][coordY - i] || this.tabUsed
					.get(new Point(coordX * getStep(), (coordY - i) * getStep())).get(Direction.VERTICAL) == 2) {
				for (int a = 1; a <= 4 - cpt_haut; a++) {
					if (!this.getPoints()[coordX][coordY + a]) {
						return;
					} else {
						cpt_bas++;
						if (cpt_haut + cpt_bas == 4) {
							a = 10;
							i = 10;
							
						}
						
					}
				}
			} else {
				if (this.tabUsed.get(new Point(coordX * getStep(), (coordY - i) * getStep()))
						.get(Direction.VERTICAL) < 2) {
					cpt_haut++;
					if (cpt_haut == 4) {
						i = 6;
						
					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * getStep()), (coordY - cpt_haut) * getStep()))
						.get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * getStep()), (coordY + cpt_bas) * getStep()))
						.get(Direction.VERTICAL) < 2) {


			if (cpt_haut + cpt_bas == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX * getStep()), (coordY - cpt_haut) * getStep());
				fin = new Point((coordX * getStep()), (coordY + cpt_bas) * getStep());
				
				if (this.tabUsed.get(debut).get(Direction.VERTICAL) == 1) {
					this.tabUsed.get(debut).replace(Direction.VERTICAL, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.VERTICAL, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.VERTICAL) == 1) {
					this.tabUsed.get(fin).replace(Direction.VERTICAL, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.VERTICAL, 0, 1);

				}
				
				for (int i = tempHaut; i <= cpt_haut; i++) {
					Point t = new Point((coordX) * getStep(), ((coordY - i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						
						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);

					}
				}
				for (int i = tempBas; i <= cpt_bas; i++) {
					Point t = new Point((coordX) * getStep(), ((coordY + i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						
						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);

					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					
					this.tabUsed.get(z).replace(Direction.VERTICAL, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				
			}
		}
		
	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a horizontal line) 
	 * if the point chosen by the player is valid.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveHorizontal(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		Point debut;
		Point fin;
		Line line = new Line(null, null, null, null, null);

		for (int i = 1; i <= 4; i++) {
			if (coordX - i >= 0 && coordY >= 0) {
				if (!this.getPoints()[coordX - i][coordY] || this.tabUsed
						.get(new Point((coordX - i) * getStep(), coordY * getStep())).get(Direction.HORIZONTAL) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {
						if (coordX + a < this.getNbColumn() && coordY >= 0) {
							if (!this.getPoints()[coordX + a][coordY]
									|| this.tabUsed.get(new Point((coordX + a) * getStep(), coordY * getStep()))
											.get(Direction.HORIZONTAL) == 2) {
								return;
							} else {
								cpt_droite++;
								if (cpt_gauche + cpt_droite == 4) {
									a = 6;
									i = 6;
								}
							}
						}

					}
				} else {
					cpt_gauche++;
					if (cpt_gauche == 4) {
						i = 6;
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * getStep(), (coordY * getStep())))
						.get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * getStep(), (coordY * getStep())))
						.get(Direction.HORIZONTAL) < 2) {

			if (cpt_gauche + cpt_droite == 4) {
				int tempLeft = 1;
				int tempRight = 1;
				debut = new Point((coordX - cpt_gauche) * getStep(), (coordY * getStep()));
				fin = new Point((coordX + cpt_droite) * getStep(), (coordY * getStep()));
				

				if (this.tabUsed.get(debut).get(Direction.HORIZONTAL) == 1) {
					this.tabUsed.get(debut).replace(Direction.HORIZONTAL, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.HORIZONTAL, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.HORIZONTAL) == 1) {
					this.tabUsed.get(fin).replace(Direction.HORIZONTAL, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.HORIZONTAL, 0, 1);

				}
				

				for (int i = tempLeft; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * getStep(), (coordY * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);


					}
				}
				

				for (int i = tempRight; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * getStep(), (coordY * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);

					}

				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					

					this.tabUsed.get(z).replace(Direction.HORIZONTAL, 0, 2);
				}

				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				
			}
		}

	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a left diagonal line) 
	 * if the point chosen by the player is valid. It begins the search at right.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveDiagonalLeft2(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null, null, null, null, null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (!this.getPoints()[coordX + i][coordY + i] || this.tabUsed
					.get(new Point((coordX + i) * getStep(), (coordY + i) * getStep())).get(Direction.DIAGLEFT) == 2) {
				for (int a = 1; a <= 4 - cpt_droite; a++) {

					if (!this.getPoints()[coordX - a][coordY - a]) {

						return;
					} else {
						cpt_gauche++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							

						}
						

					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX + i) * getStep(), (coordY + i) * getStep()))
						.get(Direction.DIAGLEFT) < 2) {
					cpt_droite++;

					if (cpt_droite == 4) {
						i = 6;
						

					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * getStep(), (coordY + cpt_droite) * getStep()))
						.get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep()))
						.get(Direction.DIAGLEFT) < 2) {



			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX + cpt_droite) * getStep(), (coordY + cpt_droite) * getStep());
				fin = new Point((coordX - cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep());
				

				if (this.tabUsed.get(debut).get(Direction.DIAGLEFT) == 1) {
					this.tabUsed.get(debut).replace(Direction.DIAGLEFT, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.DIAGLEFT, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.DIAGLEFT) == 1) {
					this.tabUsed.get(fin).replace(Direction.DIAGLEFT, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.DIAGLEFT, 0, 1);

				}
				

				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * getStep(), ((coordY - i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);


					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * getStep(), ((coordY + i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);


					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					

					this.tabUsed.get(z).replace(Direction.DIAGLEFT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				

			}
		}
		


	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a right diagonal line) 
	 * if the point chosen by the player is valid. The search begin at left.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveDiagonalRight2(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null, null, null, null, null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {

			if (!this.getPoints()[coordX - i][coordY + i] || this.tabUsed
					.get(new Point((coordX - i) * getStep(), (coordY + i) * getStep())).get(Direction.DIAGRIGHT) == 2) {
				for (int a = 1; a <= 4 - cpt_droite; a++) {

					if (!this.getPoints()[coordX + a][coordY - a]) {

						return;
					} else {
						cpt_gauche++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							

						}
						

					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX - i) * getStep(), (coordY + i) * getStep()))
						.get(Direction.DIAGRIGHT) < 2) {
					cpt_droite++;

					if (cpt_droite == 4) {
						i = 6;
						

					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_droite) * getStep(), (coordY + cpt_droite) * getStep()))
						.get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep()))
						.get(Direction.DIAGRIGHT) < 2) {



			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX - cpt_droite) * getStep(), (coordY + cpt_droite) * getStep());
				fin = new Point((coordX + cpt_gauche) * getStep(), (coordY - cpt_gauche) * getStep());
				

				if (this.tabUsed.get(debut).get(Direction.DIAGRIGHT) == 1) {
					this.tabUsed.get(debut).replace(Direction.DIAGRIGHT, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.DIAGRIGHT, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.DIAGRIGHT) == 1) {
					this.tabUsed.get(fin).replace(Direction.DIAGRIGHT, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.DIAGRIGHT, 0, 1);

				}
				

				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX + i) * getStep(), ((coordY - i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);


					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX - i) * getStep(), ((coordY + i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);


					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					

					this.tabUsed.get(z).replace(Direction.DIAGRIGHT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				

			}
		}
		


	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a vertical line) 
	 * if the point chosen by the player is valid. The search begin by bottom.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveVertical2(Point z) {
		int cpt_haut = 0;
		int cpt_bas = 0;
		Line line = new Line(null, null, null, null, null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {

			if (!this.getPoints()[coordX][coordY + i] || this.tabUsed
					.get(new Point(coordX * getStep(), (coordY + i) * getStep())).get(Direction.VERTICAL) == 2) {
				for (int a = 1; a <= 4 - cpt_bas; a++) {

					if (!this.getPoints()[coordX][coordY - a]) {

						return;
					} else {
						cpt_haut++;
						if (cpt_haut + cpt_bas == 4) {
							a = 10;
							i = 10;
							

						}
						

					}
				}
			} else {
				if (this.tabUsed.get(new Point(coordX * getStep(), (coordY + i) * getStep()))
						.get(Direction.VERTICAL) < 2) {
					cpt_bas++;

					if (cpt_bas == 4) {
						i = 6;
						

					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * getStep()), (coordY + cpt_bas) * getStep()))
						.get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * getStep()), (coordY - cpt_haut) * getStep()))
						.get(Direction.VERTICAL) < 2) {



			if (cpt_haut + cpt_bas == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX * getStep()), (coordY + cpt_bas) * getStep());
				fin = new Point((coordX * getStep()), (coordY - cpt_haut) * getStep());
				

				if (this.tabUsed.get(debut).get(Direction.VERTICAL) == 1) {
					this.tabUsed.get(debut).replace(Direction.VERTICAL, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.VERTICAL, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.VERTICAL) == 1) {
					this.tabUsed.get(fin).replace(Direction.VERTICAL, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.VERTICAL, 0, 1);

				}
				

				for (int i = tempHaut; i <= cpt_haut; i++) {
					Point t = new Point((coordX) * getStep(), ((coordY - i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);


					}
				}
				for (int i = tempBas; i <= cpt_bas; i++) {
					Point t = new Point((coordX) * getStep(), ((coordY + i) * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);


					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					

					this.tabUsed.get(z).replace(Direction.VERTICAL, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				

			}
		}
		
	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a horizontal line) 
	 * if the point chosen by the player is valid. The search begins at right.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveHorizontal2(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		Point debut;
		Point fin;
		Line line = new Line(null, null, null, null, null);

		for (int i = 1; i <= 4; i++) {
			if (coordX + i < this.getNbColumn() && coordY >= 0) {
				if (!this.getPoints()[coordX + i][coordY] || this.tabUsed
						.get(new Point((coordX + i) * getStep(), coordY * getStep())).get(Direction.HORIZONTAL) == 2) {
					for (int a = 1; a <= 4 - cpt_droite; a++) {
						if (coordX + a < this.getNbColumn() && coordY >= 0) {
							if (!this.getPoints()[coordX - a][coordY]
									|| this.tabUsed.get(new Point((coordX - a) * getStep(), coordY * getStep()))
											.get(Direction.HORIZONTAL) == 2) {

								return;
							} else {
								cpt_gauche++;
								if (cpt_gauche + cpt_droite == 4) {
									a = 6;
									i = 6;
								}
							}
						}

					}
				} else {
					cpt_droite++;
					if (cpt_droite == 4) {
						i = 6;
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * getStep(), (coordY * getStep())))
						.get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * getStep(), (coordY * getStep())))
						.get(Direction.HORIZONTAL) < 2) {

			if (cpt_gauche + cpt_droite == 4) {

				int tempLeft = 1;
				int tempRight = 1;
				debut = new Point((coordX + cpt_droite) * getStep(), (coordY * getStep()));
				fin = new Point((coordX - cpt_gauche) * getStep(), (coordY * getStep()));
				

				if (this.tabUsed.get(debut).get(Direction.HORIZONTAL) == 1) {
					this.tabUsed.get(debut).replace(Direction.HORIZONTAL, 1, 2);

				} else {
					this.tabUsed.get(debut).replace(Direction.HORIZONTAL, 0, 1);
				}
				if (this.tabUsed.get(fin).get(Direction.HORIZONTAL) == 1) {
					this.tabUsed.get(fin).replace(Direction.HORIZONTAL, 1, 2);
				} else {
					this.tabUsed.get(fin).replace(Direction.HORIZONTAL, 0, 1);

				}
				

				for (int i = tempLeft; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * getStep(), (coordY * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);


					}
				}
				

				for (int i = tempRight; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * getStep(), (coordY * getStep()));
					if ((!t.equals(debut) && !t.equals(fin))) {
						

						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);

					}

				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					

					this.tabUsed.get(z).replace(Direction.HORIZONTAL, 0, 2);
				}

				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				

			}
		}
		

	}

	/**
	 * This method makes it possible to fill possibleDirection in order to determine, for a given point, which are the 
	 * different possible directions.
	 * 
	 * @param z 
	 * (Point)
	 */
	public void possibleDirectionOnClick(Point z) {
		getPossibleDirection().replace(Direction.VERTICAL_BOTTOM, false);
		getPossibleDirection().replace(Direction.VERTICAL_TOP, false);

		getPossibleDirection().replace(Direction.HORIZONTAL_LEFT, false);
		getPossibleDirection().replace(Direction.HORIZONTAL_RIGHT, false);

		getPossibleDirection().replace(Direction.DIAGLEFT_TOPLEFT, false);
		getPossibleDirection().replace(Direction.DIAGLEFT_BOTTOMRIGHT, false);

		getPossibleDirection().replace(Direction.DIAGRIGHT_BOTTOMLEFT, false);
		getPossibleDirection().replace(Direction.DIAGRIGHT_TOPRIGHT, false);

		

		Set<Entry<Point, HashMap<Direction, Integer>>> entries = this.tabUsed.entrySet();
		HashMap<Point, HashMap<Direction, Integer>> shallowCopy1 = new HashMap<>();

		HashMap<Point, HashMap<Direction, Integer>> shallowCopy2 = new HashMap<>();
		for (Entry<Point, HashMap<Direction, Integer>> p : entries) {
			Set<Entry<Direction, Integer>> b = p.getValue().entrySet();
			HashMap<Direction, Integer> a = new HashMap<>();
			for (Entry<Direction, Integer> c : b) {
				a.put(c.getKey(), c.getValue());
			}
			shallowCopy1.put(p.getKey(), (HashMap<Direction, Integer>) a.clone());
			shallowCopy2.put(p.getKey(), (HashMap<Direction, Integer>) a.clone());

		}

		ArrayList<Line> t1 = new ArrayList<Line>();
		ArrayList<Line> t2 = new ArrayList<Line>();

		for(Line a:getTabLine()) {
			t1.add(a);
			t2.add(a);
		}
			

		
		  if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
		  getStep()] && checkPossibleMoveDiagonalLeft(z)==true) {
		  
		  this.drawMoveDiagonalLeft(z); Point p1=
		  getTabLine().get(getTabLine().size()-1).getP1(); Point p2=
		  getTabLine().get(getTabLine().size()-1).getP5(); if(getTabLine().size()>0)
		  getTabLine().remove(getTabLine().size()-1); 
			this.setTabLine(t1);
			this.tabUsed = new HashMap<>(shallowCopy1);
		  
		  this.drawMoveDiagonalLeft2(z); Point p12=
		  getTabLine().get(getTabLine().size()-1).getP1(); Point p22=
		  getTabLine().get(getTabLine().size()-1).getP5(); if(getTabLine().size()>0)
		  getTabLine().remove(getTabLine().size()-1);
		  if(!((p1.equals(p12)||p1.equals(p22))&&(p2.equals(p12)||p2.equals(p22)))) {
		  getPossibleDirection().replace(Direction.DIAGLEFT_BOTTOMRIGHT, true);
		  getPossibleDirection().replace(Direction.DIAGLEFT_TOPLEFT, true); }else {
		  getPossibleDirection().replace(Direction.DIAGLEFT_BOTTOMRIGHT, true); }
			this.setTabLine(t2);
			this.tabUsed = new HashMap<>(shallowCopy2);		  
		  } if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
		  getStep()] && checkPossibleMoveDiagonalRight(z)==true) {
		  this.drawMoveDiagonalRight(z); Point p1=
		  getTabLine().get(getTabLine().size()-1).getP1(); Point p2=
		  getTabLine().get(getTabLine().size()-1).getP5();
		  getTabLine().remove(getTabLine().size()-1); 
			this.setTabLine(t1);
			this.tabUsed = new HashMap<>(shallowCopy1);
		  this.drawMoveDiagonalRight2(z);
		  Point p12= getTabLine().get(getTabLine().size()-1).getP1(); Point p22=
		  getTabLine().get(getTabLine().size()-1).getP5();
		  getTabLine().remove(getTabLine().size()-1);
		  if(!((p1.equals(p12)||p1.equals(p22))&&(p2.equals(p12)||p2.equals(p22)))) {
		  getPossibleDirection().replace(Direction.DIAGRIGHT_BOTTOMLEFT, true);
		  getPossibleDirection().replace(Direction.DIAGRIGHT_TOPRIGHT, true); }else {
		  getPossibleDirection().replace(Direction.DIAGRIGHT_BOTTOMLEFT, true); }
			this.setTabLine(t2);
			this.tabUsed = new HashMap<>(shallowCopy2);
			}
		 
		if (!this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()]
				&& checkPossibleMoveHorizontal(z) == true) {
			this.drawMoveHorizontal(z);
			
			Point p1 = null;
			Point p2 = null;
			Point p12 = null;
			Point p22 = null;
			

			if (getTabLine().size() > 0) {
				p1 = getTabLine().get(getTabLine().size() - 1).getP1();
				p2 = getTabLine().get(getTabLine().size() - 1).getP5();
				getTabLine().remove(getTabLine().size() - 1);

			}
			this.setTabLine(t1);
			this.tabUsed = new HashMap<>(shallowCopy1);
			this.drawMoveHorizontal2(z);
			

			if (getTabLine().size() > 0) {
				p12 = getTabLine().get(getTabLine().size() - 1).getP1();
				p22 = getTabLine().get(getTabLine().size() - 1).getP5();
				getTabLine().remove(getTabLine().size() - 1);
			}
			

			if (!((p1.equals(p12) || p1.equals(p22)) && (p2.equals(p12) || p2.equals(p22)))) {
				getPossibleDirection().replace(Direction.HORIZONTAL_LEFT, true);
				getPossibleDirection().replace(Direction.HORIZONTAL_RIGHT, true);
			}

			else {
				getPossibleDirection().replace(Direction.HORIZONTAL_LEFT, true);
			}
			this.setTabLine(t2);
			this.tabUsed = new HashMap<>(shallowCopy2);
			

		} 
			  if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
			  getStep()] && checkPossibleMoveVertical(z)==true) {
			  this.drawMoveVertical(z); Point p1=
			  getTabLine().get(getTabLine().size()-1).getP1(); Point p2=
			  getTabLine().get(getTabLine().size()-1).getP5();
			  getTabLine().remove(getTabLine().size()-1); 
				this.setTabLine(t1);
				this.tabUsed = new HashMap<>(shallowCopy1);
			  this.drawMoveVertical2(z); Point
			  p12= getTabLine().get(getTabLine().size()-1).getP1(); Point p22=
			  getTabLine().get(getTabLine().size()-1).getP5();
			  getTabLine().remove(getTabLine().size()-1);
			  if(!((p1.equals(p12)||p1.equals(p22))&&(p2.equals(p12)||p2.equals(p22)))) {
			  getPossibleDirection().replace(Direction.VERTICAL_BOTTOM, true);
			  getPossibleDirection().replace(Direction.VERTICAL_TOP, true); }
			  else {
			  getPossibleDirection().replace(Direction.VERTICAL_BOTTOM, true);
			  } 
				this.setTabLine(t2);
				this.tabUsed = new HashMap<>(shallowCopy2);
			  }
			 
	}
}
