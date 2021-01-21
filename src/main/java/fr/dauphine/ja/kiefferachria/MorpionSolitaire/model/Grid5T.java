
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
 * @author floryan
 *
 */
public class Grid5T {

	/**
	 * Table of points validated by the model.
	 */
	private boolean[][] points;
	/**
	 * Table of coordinates of all intersections of the game grid.
	 */
	private ArrayList<Point> tabCoordonnee;
	/**
	 * Number of line of the grid.
	 */
	private int nbLine;
	/**
	 * Number of column of the grid.
	 */
	private int nbColumn;
	/**
	 * Grid size in pixel (height).
	 */
	private int height;
	/**
	 * Grid size in pixel (width).
	 */
	private int width;
	/**
	 * Distance between each intersection.
	 */
	private int step;
	/**
	 * Point of grid center.
	 */
	private int center;
	/**
	 * All the points of the starting cross.
	 */
	private ArrayList<Point> tabCross;
	/**
	 * Table of each validate lines.
	 */
	private List<Line> tabLine;
	/**
	 * HashMap of all possible movements for a given point.
	 */
	private HashMap<Point, HashMap<Direction, Integer>> tabUsed;
	/**
	 * ArrayList of all potential moves next (n+1).
	 */
	private ArrayList<Point> potentialMoveNext;
	/**
	 * ArrayList of all potential moves.
	 */
	private ArrayList<Point> potentialMove;
	/**
	 * HashMap of all validate points with associated direction.
	 */
	private HashMap<Point, Direction> pointUser;
	/**
	 * ArrayList which keep an historic of all scores.
	 */
	private ArrayList<Integer> scoreHistory;
	/**
	 * HashMap of all possible directions at a moment t.
	 */
	private HashMap<Direction, Boolean> possibleDirection;
	/**
	 * Object Score (computer and player)
	 */
	private Score score;

	/**
	 * 5T grid constructor.
	 * 
	 * @param h (heigth)
	 * @param w (width)
	 * @param step (gap)
	 */
	public Grid5T(int h, int w, int step) {

		this.step = step;
		this.height = h;
		this.width = w;
		this.scoreHistory = new ArrayList<Integer>();
		this.reset();

	}
	
/////////////////////////
//					   //	
// Getters and Setters //
//					   //
/////////////////////////
	
	public List<Line> getTabLine() {
		return tabLine;
	}

	public void setTabLine(List<Line> tabLine) {
		this.tabLine = tabLine;
	}

	public ArrayList<Point> getPotentialMove() {
		return potentialMove;
	}

	public void setPotentialMove(ArrayList<Point> potentialMove) {
		this.potentialMove = potentialMove;
	}

	public ArrayList<Point> getTabCross() {
		return tabCross;
	}

	public void setTabCross(ArrayList<Point> tabCross) {
		this.tabCross = tabCross;
	}

	public int getCenter() {
		return center;
	}

	public void setCenter(int center) {
		this.center = center;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean[][] getPoints() {
		return points;
	}

	public void setPoints(boolean[][] points) {
		this.points = points;
	}

	public int getNbLine() {
		return nbLine;
	}

	public void setNbLine(int nbLine) {
		this.nbLine = nbLine;
	}

	public int getNbColumn() {
		return nbColumn;
	}

	public void setNbColumn(int nbColumn) {
		this.nbColumn = nbColumn;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public ArrayList<Point> getTabCoordonnee() {
		return tabCoordonnee;
	}

	public void setTabCoordonnee(ArrayList<Point> tabCoordonnee) {
		this.tabCoordonnee = tabCoordonnee;
	}

	public HashMap<Point, Direction> getPointUser() {
		return pointUser;
	}

	public ArrayList<Integer> getScoreHistory() {
		return scoreHistory;
	}

	public void setScoreHistory(ArrayList<Integer> scoreHistory) {
		this.scoreHistory = scoreHistory;
	}
	
	/**
	 * This method makes it possible to transform a position on the grid into a point.
	 * 
	 * @param x coordinate x
	 * @param y coordinate y
	 * @return Point
	 */
	public Point getNeigh(int x, int y) {

		Point t = new Point(x, y);
		Point res = t;
		double dist = Point.distanceSq(t.getX(), t.getY() - this.step / 2, this.tabCoordonnee.get(0).getX(),
				this.tabCoordonnee.get(0).getY());
		;
		double min = dist;
		for (int i = 1; i < this.tabCoordonnee.size(); i++) {
			dist = Point.distanceSq(t.getX(), t.getY() - this.step / 2, this.tabCoordonnee.get(i).getX(),
					this.tabCoordonnee.get(i).getY());
			if (dist < min) {
				min = dist;
				res = this.tabCoordonnee.get(i);
			}
		}
		System.out.println(t);
		System.out.println(res);
		return res;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

////////////////////////
//					  //
// Game setup methods //
//					  //
////////////////////////
	
	/**
	 * Initiate instance of class
	 */
	private void initiatePoint() {
		HashMap<Direction, Integer> dir = new HashMap<Direction, Integer>();
		dir.put(Direction.VERTICAL, 0);
		dir.put(Direction.HORIZONTAL, 0);
		dir.put(Direction.DIAGLEFT, 0);
		dir.put(Direction.DIAGRIGHT, 0);
		this.possibleDirection.put(Direction.VERTICAL_BOTTOM, false);
		this.possibleDirection.put(Direction.VERTICAL_TOP, false);

		this.possibleDirection.put(Direction.HORIZONTAL_LEFT, false);
		this.possibleDirection.put(Direction.HORIZONTAL_RIGHT, false);

		this.possibleDirection.put(Direction.DIAGLEFT_TOPLEFT, false);
		this.possibleDirection.put(Direction.DIAGLEFT_BOTTOMRIGHT, false);

		this.possibleDirection.put(Direction.DIAGRIGHT_BOTTOMLEFT, false);
		this.possibleDirection.put(Direction.DIAGRIGHT_TOPRIGHT, false);

		for (int i = 0; i < nbLine; i++) {
			for (int j = 0; j < nbColumn; j++) {
				this.points[i][j] = false;
				this.tabUsed.put(new Point(i * this.step, j * this.step), (HashMap<Direction, Integer>) dir.clone());
			}
		}

	}

	/**
	 * Add all starting point in tabCross and put at true this points in points.
	 */
	public void generateCross() {
		Point c = firstPoint();
		Point temp = c;
		for (int i = 0; i < 4; i++) {
			c = new Point((int) c.getX(), (int) (c.getY() - (this.step * i)));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX() + (this.step * i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX(), (int) (c.getY() + (this.step * i)));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX() + (this.step * i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX(), (int) (c.getY() + (this.step * i)));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX() - (this.step * i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX(), (int) (c.getY() + (this.step * i)));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX() - (this.step * i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX(), (int) (c.getY() - (this.step * i)));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX() - (this.step * i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 4; i++) {
			c = new Point((int) c.getX(), (int) (c.getY() - (this.step * i)));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
		c = this.tabCross.get(this.tabCross.size() - 1);
		temp = c;
		for (int i = 1; i < 3; i++) {
			c = new Point((int) c.getX() + (this.step * i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int) c.getY() / this.step)][((int) c.getX() / this.step)] = true;
			c = temp;
		}
	}
	
	/**
	 * This method is used to determine the point of the center
	 * 
	 * @return center point
	 */
	public Point firstPoint() {
		return new Point((this.center - 1) * this.step, (this.center - 1) * this.step);
	}
	
	/**
	 * Reset all instances of Grid5T Object.
	 */
	public void reset() {
		// TODO Auto-generated method stub
		this.tabLine = new ArrayList<Line>();
		this.nbLine = (int) width / step;
		this.nbColumn = (int) height / step;
		if (!(this.pointUser == null))
			this.scoreHistory.add(this.pointUser.size());
		this.possibleDirection = new HashMap<Direction, Boolean>();
		this.potentialMove = new ArrayList<Point>();
		this.points = new boolean[nbLine][nbColumn];
		this.tabCoordonnee = new ArrayList<Point>();
		this.center = (int) nbLine / 2;// Same height and width
		this.tabCross = new ArrayList<Point>();
		this.tabUsed = new HashMap<Point, HashMap<Direction, Integer>>();
		this.initiatePoint();
		this.generateCross();
		this.catchCoordonnee();
		this.pointUser = new LinkedHashMap();
		this.score = new Score();
		this.potentialMoveNext = new ArrayList<Point>();
	}
	
	/**
	 * Catch all coordinates of the grid.
	 */
	public void catchCoordonnee() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < this.nbLine - 1; i++) {
			y = y + step;
			for (int j = 0; j < this.nbColumn - 1; j++) {
				x = x + step;
				this.tabCoordonnee.add(new Point(x, y));
			}
			x = 0;

		}
	}

////////////////////
//				  //
// Update methods //
//				  //
////////////////////
	
	/**
	 * Update the grid according to the choice of the players.
	 * 
	 * @param z (Point)
	 * @param s (player or IA)
	 */
	public void updateGrid(Point z, String s) {
		
		this.possibleDirectionOnClick(z);
		ArrayList<Direction> tmp = new ArrayList<Direction>(
				this.possibleDirection.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), true))
						.map(Map.Entry::getKey).collect(Collectors.toList()));
		if (tmp.size() > 1 && s != "IA") {
			JFrame choix = new JFrame("Choix direction");
			choix.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			choix.setSize(new Dimension(200, 200));
			choix.setLocationRelativeTo(null);
			for (int i = 0; i < tmp.size(); i++) {
				final int a = new Integer(i);
				c.gridx = 0;
				c.gridy = i;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridwidth = 1;
				c.weightx = 1 / tmp.size();
				c.weighty = 1 / tmp.size();
				JButton b = new JButton(tmp.get(i).toString());
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateGrid(z, tmp.get(a));
						System.out.println(tmp.get(a));
						System.out.println(tabLine.size());
						choix.dispose();
					}

				});
				choix.add(b, c);
			}

			choix.setVisible(true);
			
		} else if (tmp.size() > 1 && s == "IA") {
			Collections.shuffle(tmp);
			updateGrid(z, tmp.get(0));
		} else if (tmp.size() == 1) {
			updateGrid(z, tmp.get(0));

		} else {
			System.out.println("Pas de mouvement possible");
			System.out.println(this.tabUsed.get(z));
		}

	}

	/**
	 * Update the grid according to a point and a direction.
	 * 
	 * @param z (Point)
	 * @param d (Direction)
	 */
	public void updateGrid(Point z, Direction d) {
		if (d == Direction.VERTICAL_BOTTOM) {
			drawMoveVertical2(z);
			getPointUser().put(z, Direction.VERTICAL_BOTTOM);
		}
		if (d == Direction.HORIZONTAL_LEFT) {
			drawMoveHorizontal(z);
			getPointUser().put(z, Direction.HORIZONTAL_LEFT);

		}
		if (d == Direction.DIAGLEFT_BOTTOMRIGHT) {
			drawMoveDiagonalLeft2(z);
			getPointUser().put(z, Direction.DIAGLEFT_BOTTOMRIGHT);

		}
		if (d == Direction.DIAGRIGHT_TOPRIGHT) {
			drawMoveDiagonalRight(z);
			getPointUser().put(z, Direction.DIAGRIGHT_TOPRIGHT);

		}
		if (d == Direction.VERTICAL_TOP) {
			drawMoveVertical(z);
			getPointUser().put(z, Direction.VERTICAL_TOP);
		}
		if (d == Direction.HORIZONTAL_RIGHT) {
			drawMoveHorizontal2(z);
			getPointUser().put(z, Direction.HORIZONTAL_RIGHT);

		}
		if (d == Direction.DIAGLEFT_TOPLEFT) {
			drawMoveDiagonalLeft(z);
			getPointUser().put(z, Direction.DIAGLEFT_TOPLEFT);

		}
		if (d == Direction.DIAGRIGHT_BOTTOMLEFT) {
			drawMoveDiagonalRight2(z);
			getPointUser().put(z, Direction.DIAGRIGHT_BOTTOMLEFT);

		}
		this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()] = true;

		this.incrementeScore("player");
	}

	/**
	 * Computer play and makes random choices.
	 */
	public void updateIA() {
		Collections.shuffle(this.potentialMove);
		if (!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(0);
			this.updateGrid(x, "IA");
		} else {
			System.out.println("No solution");
		}

	}
	
	/**
	 * This method able to increment score. 
	 * 
	 * @param s (player or IA)
	 */
	public void incrementeScore(String s) {
		if (s.equals("IA")) {
			this.score.setScore_computeur(this.score.getScore_computeur() + 1);
			System.out.println("score ordi: " + this.score.getScore_computeur());
		} else {
			this.score.setScore_joueur(this.score.getScore_joueur() + 1);
			System.out.println("score joueur: " + this.score.getScore_joueur());
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//																													  //
// Methods which allow to capture the possible points and also to capture the possible points with respect to a point //
//																													  //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * This method able to fills the table of possible available points at time t.
	 */
	public void pointAvailable() {
		this.potentialMove.clear();
		for (int i = 0; i < nbLine; i++) {
			for (int j = 0; j < nbColumn; j++) {
				Point z = new Point(i * this.step, j * this.step);
				if (!this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()]
						&& (checkPossibleMoveVertical(z) || checkPossibleMoveHorizontal(z)
								|| checkPossibleMoveDiagonalLeft(z) || checkPossibleMoveDiagonalRight(z))) {
					this.potentialMove.add(z);
				}
			}
		}
	}
	
	/**
	 * This method return the number of possible possibilities after adding the point passed in parameter.
	 * @param b (Point)
	 * @return number of available points.
	 */
	public int pointAvailableNext(Point b) {
		this.potentialMoveNext.clear();
		boolean test = false;
		if (this.getPoints()[((int) b.getX() / this.getStep())][(int) b.getY() / this.getStep()] == false) {
			this.getPoints()[((int) b.getX() / this.getStep())][(int) b.getY() / this.getStep()] = true;
			test = true;
		}
		for (int i = 0; i < nbLine; i++) {
			for (int j = 0; j < nbColumn; j++) {
				Point z = new Point(i * this.step, j * this.step);
				if (!this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()]
						&& (checkPossibleMoveDiagonalRight(z) || checkPossibleMoveDiagonalLeft(z)
								|| checkPossibleMoveHorizontal(z) || checkPossibleMoveVertical(z))) {
					this.potentialMoveNext.add(z);
				}
			}

		}
		if (test) {
			this.getPoints()[((int) b.getX() / this.getStep())][(int) b.getY() / this.getStep()] = false;
		}
		return this.potentialMoveNext.size();

	}
	
	/**
	 * This method able to found a solution (but not the best)
	 */
	public void NMCS() {
		int max = -1;
		int indice = -1;
		Collections.shuffle(this.potentialMove);
		for (int i = 0; i < this.potentialMove.size(); i++) {
			int x = this.pointAvailableNext(this.potentialMove.get(i));
			if (x > max) {
				max = x;
				indice = i;
			}
		}

		if (!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(indice);
			this.updateGrid(x, "IA");
		} else {
			System.out.println("No solution");
		}

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
						.get(new Point((coordX - i) * this.step, coordY * this.step)).get(Direction.HORIZONTAL) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {
						if (coordX + a < this.nbColumn && coordY >= 0) {
							if (!this.getPoints()[coordX + a][coordY]
									|| this.tabUsed.get(new Point((coordX + a) * this.step, coordY * this.step))
											.get(Direction.HORIZONTAL) == 2) {
								System.out.println("Pas de line possible sur l'horizontale");
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
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY * this.step)))
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
				System.out.println(this.getPoints()[coordX][coordY - i]);
				if (!this.getPoints()[coordX][coordY - i] || this.tabUsed
						.get(new Point(coordX * this.step, (coordY - i) * this.step)).get(Direction.VERTICAL) == 2) {
					for (int a = 1; a <= 4 - cpt_haut; a++) {
						// System.out.println(a);
						if (coordX >= 0 && coordY + a < this.nbColumn) {
							if (!this.getPoints()[coordX][coordY + a]) {
								System.out.println("Pas de line possible sur la vertical");
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
					if (this.tabUsed.get(new Point(coordX * this.step, (coordY - i) * this.step))
							.get(Direction.VERTICAL) < 2) {
						cpt_haut++;
						System.out.println("cpt_haut++");
						if (cpt_haut == 4) {
							i = 6;
						}
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * this.step), (coordY - cpt_haut) * this.step))
						.get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * this.step), (coordY + cpt_bas) * this.step))
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
				System.out.println(this.getPoints()[coordX - i][coordY - i]);
				if (!this.getPoints()[coordX - i][coordY - i]
						|| this.tabUsed.get(new Point((coordX - i) * this.step, (coordY - i) * this.step))
								.get(Direction.DIAGLEFT) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {
						// System.out.println(a);
						if (coordX + a < this.nbColumn && coordY + a < this.nbColumn) {
							if (!this.getPoints()[coordX + a][coordY + a]) {
								System.out.println("Pas de line possible sur la vertical");
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
					if (this.tabUsed.get(new Point((coordX - i) * this.step, (coordY - i) * this.step))
							.get(Direction.DIAGLEFT) < 2) {
						cpt_gauche++;
						System.out.println("cpt_gauche++");
						if (cpt_gauche == 4) {
							i = 6;
						}
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step))
						.get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY + cpt_droite) * this.step))
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
			if (coordX + i < this.nbColumn && coordY - i >= 0) {
				System.out.println(this.getPoints()[coordX + i][coordY - i]);
				if (!this.getPoints()[coordX + i][coordY - i]
						|| this.tabUsed.get(new Point((coordX + i) * this.step, (coordY - i) * this.step))
								.get(Direction.DIAGRIGHT) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {
						// System.out.println(a);
						if (coordX - a >= 0 && coordY + a < this.nbColumn) {
							if (!this.getPoints()[coordX - a][coordY + a]) {
								System.out.println("Pas de line possible sur la vertical");
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
					if (this.tabUsed.get(new Point((coordX + i) * this.step, (coordY - i) * this.step))
							.get(Direction.DIAGRIGHT) < 2) {
						cpt_gauche++;
						System.out.println("cpt_gauche++");
						if (cpt_gauche == 4) {
							i = 6;
						}
					}

				}
			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step))
						.get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_droite) * this.step, (coordY + cpt_droite) * this.step))
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
			System.out.println(this.getPoints()[coordX + i][coordY - i]);
			if (!this.getPoints()[coordX + i][coordY - i] || this.tabUsed
					.get(new Point((coordX + i) * this.step, (coordY - i) * this.step)).get(Direction.DIAGRIGHT) == 2) {
				for (int a = 1; a <= 4 - cpt_gauche; a++) {
					// System.out.println(a);
					if (!this.getPoints()[coordX - a][coordY + a]) {
						System.out.println("Pas de line possible sur la vertical");
						return;
					} else {
						cpt_droite++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							System.out.println("Je suis dans if");
							System.out.println("Gauche" + cpt_gauche);
							System.out.println("Droite" + cpt_droite);
						}
						System.out.println("cpt_droite++");
						System.out.println(cpt_gauche + cpt_droite);
					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX + i) * this.step, (coordY - i) * this.step))
						.get(Direction.DIAGRIGHT) < 2) {
					cpt_gauche++;
					System.out.println("cpt_gauche++");
					if (cpt_gauche == 4) {
						i = 6;
						System.out.println("Je suis dans else");
						System.out.println("Haut" + cpt_gauche);
						System.out.println("Bas" + cpt_droite);
					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step))
						.get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_droite) * this.step, (coordY + cpt_droite) * this.step))
						.get(Direction.DIAGRIGHT) < 2) {


			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX + cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step);
				fin = new Point((coordX - cpt_droite) * this.step, (coordY + cpt_droite) * this.step);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX + i) * this.step, ((coordY - i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré haut");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX - i) * this.step, ((coordY + i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré bas");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.DIAGRIGHT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}
		System.out.println("Gauche" + cpt_gauche);
		System.out.println("Droite" + cpt_droite);
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
			System.out.println(this.getPoints()[coordX - i][coordY - i]);
			if (!this.getPoints()[coordX - i][coordY - i] || this.tabUsed
					.get(new Point((coordX - i) * this.step, (coordY - i) * this.step)).get(Direction.DIAGLEFT) == 2) {
				for (int a = 1; a <= 4 - cpt_gauche; a++) {
					// System.out.println(a);
					if (!this.getPoints()[coordX + a][coordY + a]) {
						System.out.println("Pas de line possible sur la vertical");
						return;
					} else {
						cpt_droite++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							System.out.println("Je suis dans if");
							System.out.println("Gauche" + cpt_gauche);
							System.out.println("Droite" + cpt_droite);
						}
						System.out.println("cpt_droite++");
						System.out.println(cpt_gauche + cpt_droite);
					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX - i) * this.step, (coordY - i) * this.step))
						.get(Direction.DIAGLEFT) < 2) {
					cpt_gauche++;
					System.out.println("cpt_gauche++");
					if (cpt_gauche == 4) {
						i = 6;
						System.out.println("Je suis dans else");
						System.out.println("Haut" + cpt_gauche);
						System.out.println("Bas" + cpt_droite);
					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step))
						.get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY + cpt_droite) * this.step))
						.get(Direction.DIAGLEFT) < 2) {


			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX - cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step);
				fin = new Point((coordX + cpt_droite) * this.step, (coordY + cpt_droite) * this.step);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * this.step, ((coordY - i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré haut");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * this.step, ((coordY + i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré bas");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.DIAGLEFT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}
		System.out.println("Gauche" + cpt_gauche);
		System.out.println("Droite" + cpt_droite);
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
			System.out.println(this.getPoints()[coordX][coordY - i]);
			if (!this.getPoints()[coordX][coordY - i] || this.tabUsed
					.get(new Point(coordX * this.step, (coordY - i) * this.step)).get(Direction.VERTICAL) == 2) {
				for (int a = 1; a <= 4 - cpt_haut; a++) {
					// System.out.println(a);
					if (!this.getPoints()[coordX][coordY + a]) {
						System.out.println("Pas de line possible sur la vertical");
						return;
					} else {
						cpt_bas++;
						if (cpt_haut + cpt_bas == 4) {
							a = 10;
							i = 10;
							System.out.println("Je suis dans if");
							System.out.println("Haut" + cpt_haut);
							System.out.println("Bas" + cpt_bas);
						}
						System.out.println("cpt_bas++");
						System.out.println(cpt_haut + cpt_bas);
					}
				}
			} else {
				if (this.tabUsed.get(new Point(coordX * this.step, (coordY - i) * this.step))
						.get(Direction.VERTICAL) < 2) {
					cpt_haut++;
					System.out.println("cpt_haut++");
					if (cpt_haut == 4) {
						i = 6;
						System.out.println("Je suis dans else");
						System.out.println("Haut" + cpt_haut);
						System.out.println("Bas" + cpt_bas);
					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * this.step), (coordY - cpt_haut) * this.step))
						.get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * this.step), (coordY + cpt_bas) * this.step))
						.get(Direction.VERTICAL) < 2) {

			System.out.println(this.tabUsed.get(z));

			if (cpt_haut + cpt_bas == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX * this.step), (coordY - cpt_haut) * this.step);
				fin = new Point((coordX * this.step), (coordY + cpt_bas) * this.step);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempHaut; i <= cpt_haut; i++) {
					Point t = new Point((coordX) * this.step, ((coordY - i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré haut");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				for (int i = tempBas; i <= cpt_bas; i++) {
					Point t = new Point((coordX) * this.step, ((coordY + i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré bas");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.VERTICAL, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}
		System.out.println("Haut" + cpt_haut);
		System.out.println("Bas" + cpt_bas);
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
						.get(new Point((coordX - i) * this.step, coordY * this.step)).get(Direction.HORIZONTAL) == 2) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {
						if (coordX + a < this.nbColumn && coordY >= 0) {
							if (!this.getPoints()[coordX + a][coordY]
									|| this.tabUsed.get(new Point((coordX + a) * this.step, coordY * this.step))
											.get(Direction.HORIZONTAL) == 2) {
								System.out.println("Pas de line possible sur l'horizontale");
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
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL) < 2) {

			if (cpt_gauche + cpt_droite == 4) {
				System.out.println(this.tabUsed.get(z));
				int tempLeft = 1;
				int tempRight = 1;
				debut = new Point((coordX - cpt_gauche) * this.step, (coordY * this.step));
				fin = new Point((coordX + cpt_droite) * this.step, (coordY * this.step));
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempLeft; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * this.step, (coordY * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré left");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				System.out.println("^-------------------------------------------------------");
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("After for");
				for (int i = tempRight; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * this.step, (coordY * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré right");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);

					}

				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.HORIZONTAL, 0, 2);
				}

				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}

	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a right diagonal line) 
	 * if the point chosen by the player is valid.
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
			System.out.println(this.getPoints()[coordX + i][coordY + i]);
			if (!this.getPoints()[coordX + i][coordY + i] || this.tabUsed
					.get(new Point((coordX + i) * this.step, (coordY + i) * this.step)).get(Direction.DIAGLEFT) == 2) {
				for (int a = 1; a <= 4 - cpt_droite; a++) {
					// System.out.println(a);
					if (!this.getPoints()[coordX - a][coordY - a]) {
						System.out.println("Pas de line possible sur la vertical");
						return;
					} else {
						cpt_gauche++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							System.out.println("Je suis dans if");
							System.out.println("Gauche" + cpt_gauche);
							System.out.println("Droite" + cpt_droite);
						}
						System.out.println("cpt_droite++");
						System.out.println(cpt_gauche + cpt_droite);
					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX + i) * this.step, (coordY + i) * this.step))
						.get(Direction.DIAGLEFT) < 2) {
					cpt_droite++;
					System.out.println("cpt_gauche++");
					if (cpt_droite == 4) {
						i = 6;
						System.out.println("Je suis dans else");
						System.out.println("Haut" + cpt_gauche);
						System.out.println("Bas" + cpt_droite);
					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY + cpt_droite) * this.step))
						.get(Direction.DIAGLEFT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step))
						.get(Direction.DIAGLEFT) < 2) {

			System.out.println(this.tabUsed.get(z));

			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX + cpt_droite) * this.step, (coordY + cpt_droite) * this.step);
				fin = new Point((coordX - cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * this.step, ((coordY - i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré haut");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * this.step, ((coordY + i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré bas");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGLEFT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.DIAGLEFT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}
		System.out.println("Gauche" + cpt_gauche);
		System.out.println("Droite" + cpt_droite);

	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a left diagonal line) 
	 * if the point chosen by the player is valid.
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
			System.out.println(this.getPoints()[coordX - i][coordY + i]);
			if (!this.getPoints()[coordX - i][coordY + i] || this.tabUsed
					.get(new Point((coordX - i) * this.step, (coordY + i) * this.step)).get(Direction.DIAGRIGHT) == 2) {
				for (int a = 1; a <= 4 - cpt_droite; a++) {
					// System.out.println(a);
					if (!this.getPoints()[coordX + a][coordY - a]) {
						System.out.println("Pas de line possible sur la vertical");
						return;
					} else {
						cpt_gauche++;
						if (cpt_gauche + cpt_droite == 4) {
							a = 10;
							i = 10;
							System.out.println("Je suis dans if");
							System.out.println("Gauche" + cpt_gauche);
							System.out.println("Droite" + cpt_droite);
						}
						System.out.println("cpt_droite++");
						System.out.println(cpt_gauche + cpt_droite);
					}
				}
			} else {
				if (this.tabUsed.get(new Point((coordX - i) * this.step, (coordY + i) * this.step))
						.get(Direction.DIAGRIGHT) < 2) {
					cpt_droite++;
					System.out.println("cpt_droite++");
					if (cpt_droite == 4) {
						i = 6;
						System.out.println("Je suis dans else");
						System.out.println("Gauche" + cpt_gauche);
						System.out.println("Droite" + cpt_droite);
					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_droite) * this.step, (coordY + cpt_droite) * this.step))
						.get(Direction.DIAGRIGHT) < 2
				&& this.tabUsed.get(new Point((coordX + cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step))
						.get(Direction.DIAGRIGHT) < 2) {

			System.out.println(this.tabUsed.get(z));

			if (cpt_gauche + cpt_droite == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX - cpt_droite) * this.step, (coordY + cpt_droite) * this.step);
				fin = new Point((coordX + cpt_gauche) * this.step, (coordY - cpt_gauche) * this.step);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempHaut; i <= cpt_gauche; i++) {
					Point t = new Point((coordX + i) * this.step, ((coordY - i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré haut");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				for (int i = tempBas; i <= cpt_droite; i++) {
					Point t = new Point((coordX - i) * this.step, ((coordY + i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré bas");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.DIAGRIGHT, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.DIAGRIGHT, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}
		System.out.println("Gauche" + cpt_gauche);
		System.out.println("Droite" + cpt_droite);

	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a vertical line) 
	 * if the point chosen by the player is valid.
	 * 
	 * @param z
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
			System.out.println(this.getPoints()[coordX][coordY + i]);
			if (!this.getPoints()[coordX][coordY + i] || this.tabUsed
					.get(new Point(coordX * this.step, (coordY + i) * this.step)).get(Direction.VERTICAL) == 2) {
				for (int a = 1; a <= 4 - cpt_bas; a++) {
					// System.out.println(a);
					if (!this.getPoints()[coordX][coordY - a]) {
						System.out.println("Pas de line possible sur la vertical");
						return;
					} else {
						cpt_haut++;
						if (cpt_haut + cpt_bas == 4) {
							a = 10;
							i = 10;
							System.out.println("Je suis dans if");
							System.out.println("Haut" + cpt_haut);
							System.out.println("Bas" + cpt_bas);
						}
						System.out.println("cpt_bas++");
						System.out.println(cpt_haut + cpt_bas);
					}
				}
			} else {
				if (this.tabUsed.get(new Point(coordX * this.step, (coordY + i) * this.step))
						.get(Direction.VERTICAL) < 2) {
					cpt_bas++;
					System.out.println("cpt_haut++");
					if (cpt_bas == 4) {
						i = 6;
						System.out.println("Je suis dans else");
						System.out.println("Haut" + cpt_haut);
						System.out.println("Bas" + cpt_bas);
					}
				}

			}

		}
		if (this.tabUsed.get(z).get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * this.step), (coordY + cpt_bas) * this.step))
						.get(Direction.VERTICAL) < 2
				&& this.tabUsed.get(new Point((coordX * this.step), (coordY - cpt_haut) * this.step))
						.get(Direction.VERTICAL) < 2) {

			System.out.println(this.tabUsed.get(z));

			if (cpt_haut + cpt_bas == 4) {

				int tempHaut = 1;
				int tempBas = 1;
				debut = new Point((coordX * this.step), (coordY + cpt_bas) * this.step);
				fin = new Point((coordX * this.step), (coordY - cpt_haut) * this.step);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempHaut; i <= cpt_haut; i++) {
					Point t = new Point((coordX) * this.step, ((coordY - i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré haut");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				for (int i = tempBas; i <= cpt_bas; i++) {
					Point t = new Point((coordX) * this.step, ((coordY + i) * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré bas");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.VERTICAL, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.VERTICAL, 0, 2);
				}
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}
		System.out.println("Haut" + cpt_haut);
		System.out.println("Bas" + cpt_bas);
	}

	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a horizontal line) 
	 * if the point chosen by the player is valid.
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
			if (coordX + i < this.nbColumn && coordY >= 0) {
				if (!this.getPoints()[coordX + i][coordY] || this.tabUsed
						.get(new Point((coordX + i) * this.step, coordY * this.step)).get(Direction.HORIZONTAL) == 2) {
					for (int a = 1; a <= 4 - cpt_droite; a++) {
						if (coordX + a < this.nbColumn && coordY >= 0) {
							if (!this.getPoints()[coordX - a][coordY]
									|| this.tabUsed.get(new Point((coordX - a) * this.step, coordY * this.step))
											.get(Direction.HORIZONTAL) == 2) {
								System.out.println("Pas de line possible sur l'horizontale");
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
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL) < 2
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL) < 2) {

			if (cpt_gauche + cpt_droite == 4) {
				System.out.println(this.tabUsed.get(z));
				int tempLeft = 1;
				int tempRight = 1;
				debut = new Point((coordX + cpt_droite) * this.step, (coordY * this.step));
				fin = new Point((coordX - cpt_gauche) * this.step, (coordY * this.step));
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before if");
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
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Before for");
				for (int i = tempLeft; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * this.step, (coordY * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré left");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);
						System.out.println(this.tabUsed.get(t));

					}
				}
				System.out.println("^-------------------------------------------------------");
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("After for");
				for (int i = tempRight; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * this.step, (coordY * this.step));
					if ((!t.equals(debut) && !t.equals(fin))) {
						System.out.println("entré right");
						System.out.println(t.equals(debut));
						System.out.println(t.equals(fin));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, 0, 2);

					}

				}
				if ((!z.equals(debut) && !z.equals(fin))) {
					System.out.println("entré z");
					System.out.println(z.equals(debut));
					System.out.println(z.equals(fin));
					this.tabUsed.get(z).replace(Direction.HORIZONTAL, 0, 2);
				}

				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println(this.tabUsed.get(debut));
				System.out.println(this.tabUsed.get(fin));
				System.out.println("Ajout");
			}
		}
		for (int v = 0; v < this.tabCoordonnee.size(); v++) {
			System.out.println(this.tabUsed.get(tabCoordonnee.get(v)));
		}

	}

	/**
	 * This method makes it possible to fill possibleDirection in order to determine, for a given point, which are the 
	 * different possible directions.
	 * 
	 * @param z
	 */
	public void possibleDirectionOnClick(Point z) {
		this.possibleDirection.replace(Direction.VERTICAL_BOTTOM, false);
		this.possibleDirection.replace(Direction.VERTICAL_TOP, false);

		this.possibleDirection.replace(Direction.HORIZONTAL_LEFT, false);
		this.possibleDirection.replace(Direction.HORIZONTAL_RIGHT, false);

		this.possibleDirection.replace(Direction.DIAGLEFT_TOPLEFT, false);
		this.possibleDirection.replace(Direction.DIAGLEFT_BOTTOMRIGHT, false);

		this.possibleDirection.replace(Direction.DIAGRIGHT_BOTTOMLEFT, false);
		this.possibleDirection.replace(Direction.DIAGRIGHT_TOPRIGHT, false);

		

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

		List<Line> t1 = new ArrayList<Line>();
		List<Line> t2 = new ArrayList<Line>();

		for(Line a:this.tabLine) {
			t1.add(a);
			t2.add(a);
		}
			

		
		  if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
		  getStep()] && checkPossibleMoveDiagonalLeft(z)==true) {
		  
		  this.drawMoveDiagonalLeft(z); Point p1=
		  this.tabLine.get(this.tabLine.size()-1).getP1(); Point p2=
		  this.tabLine.get(this.tabLine.size()-1).getP5(); if(tabLine.size()>0)
		  this.tabLine.remove(this.tabLine.size()-1); 
			this.setTabLine(t1);
			this.tabUsed = new HashMap<>(shallowCopy1);
		  
		  this.drawMoveDiagonalLeft2(z); Point p12=
		  this.tabLine.get(this.tabLine.size()-1).getP1(); Point p22=
		  this.tabLine.get(this.tabLine.size()-1).getP5(); if(tabLine.size()>0)
		  this.tabLine.remove(this.tabLine.size()-1);
		  if(!((p1.equals(p12)||p1.equals(p22))&&(p2.equals(p12)||p2.equals(p22)))) {
		  this.possibleDirection.replace(Direction.DIAGLEFT_BOTTOMRIGHT, true);
		  this.possibleDirection.replace(Direction.DIAGLEFT_TOPLEFT, true); }else {
		  this.possibleDirection.replace(Direction.DIAGLEFT_BOTTOMRIGHT, true); }
			this.setTabLine(t2);
			this.tabUsed = new HashMap<>(shallowCopy2);		  
		  } if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
		  getStep()] && checkPossibleMoveDiagonalRight(z)==true) {
		  this.drawMoveDiagonalRight(z); Point p1=
		  this.tabLine.get(this.tabLine.size()-1).getP1(); Point p2=
		  this.tabLine.get(this.tabLine.size()-1).getP5();
		  this.tabLine.remove(this.tabLine.size()-1); 
			this.setTabLine(t1);
			this.tabUsed = new HashMap<>(shallowCopy1);
		  this.drawMoveDiagonalRight2(z);
		  Point p12= this.tabLine.get(this.tabLine.size()-1).getP1(); Point p22=
		  this.tabLine.get(this.tabLine.size()-1).getP5();
		  this.tabLine.remove(this.tabLine.size()-1);
		  if(!((p1.equals(p12)||p1.equals(p22))&&(p2.equals(p12)||p2.equals(p22)))) {
		  this.possibleDirection.replace(Direction.DIAGRIGHT_BOTTOMLEFT, true);
		  this.possibleDirection.replace(Direction.DIAGRIGHT_TOPRIGHT, true); }else {
		  this.possibleDirection.replace(Direction.DIAGRIGHT_BOTTOMLEFT, true); }
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
			System.out.println("---------------- P");
			System.out.println(this.tabLine.size());
			System.out.println(this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()]);
			if (this.tabLine.size() > 0) {
				p1 = this.tabLine.get(this.tabLine.size() - 1).getP1();
				p2 = this.tabLine.get(this.tabLine.size() - 1).getP5();
				this.tabLine.remove(this.tabLine.size() - 1);

			}
			this.setTabLine(t1);
			this.tabUsed = new HashMap<>(shallowCopy1);
			this.drawMoveHorizontal2(z);
			System.out.println("---------------- P");
			System.out.println(tabLine.size());
			System.out.println(this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()]);

			if (this.tabLine.size() > 0) {
				p12 = this.tabLine.get(this.tabLine.size() - 1).getP1();
				p22 = this.tabLine.get(this.tabLine.size() - 1).getP5();
				this.tabLine.remove(this.tabLine.size() - 1);
			}
			System.out.println("---------------- P");
			System.out.println(p1);
			System.out.println(p12);
			System.out.println(p2);
			System.out.println(p22);

			if (!((p1.equals(p12) || p1.equals(p22)) && (p2.equals(p12) || p2.equals(p22)))) {
				this.possibleDirection.replace(Direction.HORIZONTAL_LEFT, true);
				this.possibleDirection.replace(Direction.HORIZONTAL_RIGHT, true);
			}

			else {
				this.possibleDirection.replace(Direction.HORIZONTAL_LEFT, true);
				System.out.println("cc");
			}
			this.setTabLine(t2);
			this.tabUsed = new HashMap<>(shallowCopy2);
			

		} 
			  if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
			  getStep()] && checkPossibleMoveVertical(z)==true) {
			  this.drawMoveVertical(z); Point p1=
			  this.tabLine.get(this.tabLine.size()-1).getP1(); Point p2=
			  this.tabLine.get(this.tabLine.size()-1).getP5();
			  this.tabLine.remove(this.tabLine.size()-1); 
				this.setTabLine(t1);
				this.tabUsed = new HashMap<>(shallowCopy1);
			  this.drawMoveVertical2(z); Point
			  p12= this.tabLine.get(this.tabLine.size()-1).getP1(); Point p22=
			  this.tabLine.get(this.tabLine.size()-1).getP5();
			  this.tabLine.remove(this.tabLine.size()-1);
			  if(!((p1.equals(p12)||p1.equals(p22))&&(p2.equals(p12)||p2.equals(p22)))) {
			  this.possibleDirection.replace(Direction.VERTICAL_BOTTOM, true);
			  this.possibleDirection.replace(Direction.VERTICAL_TOP, true); }
			  else {
			  this.possibleDirection.replace(Direction.VERTICAL_BOTTOM, true);
			  } 
				this.setTabLine(t2);
				this.tabUsed = new HashMap<>(shallowCopy2);
			  }
			 
	}
}
