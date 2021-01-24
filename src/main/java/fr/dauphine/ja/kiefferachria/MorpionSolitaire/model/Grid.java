package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;

public abstract class Grid {
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
	private ArrayList<Line> tabLine;
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
	 * Object Score (computer and player)
	 */
	private Score score;
	/**
	 * HashMap of all possible directions at a moment t.
	 */
	private HashMap<Direction, Boolean> possibleDirection;

	public boolean[][] getPoints() {
		return points;
	}

	public ArrayList<Point> getTabCoordonnee() {
		return tabCoordonnee;
	}

	public int getNbLine() {
		return nbLine;
	}

	public int getNbColumn() {
		return nbColumn;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getStep() {
		return step;
	}

	public int getCenter() {
		return center;
	}

	public ArrayList<Point> getTabCross() {
		return tabCross;
	}

	public ArrayList<Line> getTabLine() {
		return tabLine;
	}

	public ArrayList<Point> getPotentialMoveNext() {
		return potentialMoveNext;
	}

	public ArrayList<Point> getPotentialMove() {
		return potentialMove;
	}

	public ArrayList<Integer> getScoreHistory() {
		return scoreHistory;
	}

	public Score getScore() {
		return score;
	}

	public HashMap<Direction, Boolean> getPossibleDirection() {
		return possibleDirection;
	}

	public void setTabLine(ArrayList<Line> tabLine) {
		this.tabLine = tabLine;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public void setTabCross(ArrayList<Point> tabCross) {
		this.tabCross = tabCross;
	}

	public void setCenter(int center) {
		this.center = center;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setPoints(boolean[][] points) {
		this.points = points;
	}

	public void setTabCoordonnee(ArrayList<Point> tabCoordonnee) {
		this.tabCoordonnee = tabCoordonnee;
	}

	public void setNbLine(int nbLine) {
		this.nbLine = nbLine;
	}

	public void setNbColumn(int nbColumn) {
		this.nbColumn = nbColumn;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setPotentialMoveNext(ArrayList<Point> potentialMoveNext) {
		this.potentialMoveNext = potentialMoveNext;
	}

	public void setPotentialMove(ArrayList<Point> potentialMove) {
		this.potentialMove = potentialMove;
	}

	public void setPointUser(HashMap<Point, Direction> pointUser) {
		this.pointUser = pointUser;
	}

	public void setScoreHistory(ArrayList<Integer> scoreHistory) {
		this.scoreHistory = scoreHistory;
	}

	public void setPossibleDirection(HashMap<Direction, Boolean> possibleDirection) {
		this.possibleDirection = possibleDirection;
	}

	/**
	 * This method makes it possible to transform a position on the grid into a
	 * point.
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

		return res;
	}

	public HashMap<Point, Direction> getPointUser() {
		return pointUser;
	}

	public Grid(int h, int w, int step) {

		this.step = step;
		this.height = h;
		this.width = w;
		this.scoreHistory = new ArrayList<Integer>();
		this.reset();

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
//		  //
//Update methods //
//		  //
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
				final int a = i;
				c.gridx = 0;
				c.gridy = i;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridwidth = 1;
				c.weightx = 1 / tmp.size();
				c.weighty = 1 / tmp.size();
				JButton b = new JButton(tmp.get(i).toString());
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateGrid(z, tmp.get(a), s);

						choix.dispose();
					}

				});
				choix.add(b, c);
			}

			choix.setVisible(true);

		} else if (tmp.size() > 1 && s == "IA") {
			Collections.shuffle(tmp);
			updateGrid(z, tmp.get(0), s);
		} else if (tmp.size() == 1) {
			updateGrid(z, tmp.get(0), s);

		} else {

		}

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

		}

	}

	/**
	 * Update the grid according to a point and a direction.
	 * 
	 * @param z (Point)
	 * @param d (Direction)
	 * @param s (String) which corresponds to the user
	 */
	public void updateGrid(Point z, Direction d, String s) {
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

		this.incrementeScore(s);
	}

	/**
	 * This method able to increment score.
	 * 
	 * @param s (player or IA)
	 */
	public void incrementeScore(String s) {
		if (s.equals("IA")) {
			this.score.setScore_computeur(this.score.getScore_computeur() + 1);

		} else {
			this.score.setScore_joueur(this.score.getScore_joueur() + 1);

		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	  //
//Methods which allow to capture the possible points and also to capture the possible points with respect to a point //
//	  //
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
						&& (checkPossibleMoveDiagonalRight(z) || checkPossibleMoveDiagonalLeft(z)
								|| checkPossibleMoveHorizontal(z) || checkPossibleMoveVertical(z))) {
					this.potentialMove.add(z);
				}
			}
		}
	}

	/**
	 * This method return the number of possible possibilities after adding the
	 * point passed in parameter.
	 * 
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
		int max=-1;
		Collections.shuffle(this.potentialMove);
		for (int i=0;i<this.potentialMove.size();i++) {
			int x=this.pointAvailableNext(this.potentialMove.get(i));
			if(x>max) {
				max=x;
			}
		}
		ArrayList<Point> tmp = new ArrayList<Point>();
		for (int i=0;i<this.potentialMove.size();i++) {
			int x=this.pointAvailableNext(this.potentialMove.get(i));
			if(x==max) {
				tmp.add(this.potentialMove.get(i));
			}
		}
		int tmpMax = 0;
		for(int i = 0 ; i<tmp.size();i++) {
			int x=this.pointAvailableNext(tmp.get(i));
			if(x>tmpMax) {
				tmpMax = x;
			}
		}
		ArrayList<Point> choice = new ArrayList<Point>();
		for(int i = 0 ; i<tmp.size();i++) {
			int x=this.pointAvailableNext(tmp.get(i));
			if(x==tmpMax) {
				choice.add(tmp.get(i));
			}
		}
		Point best;
		if(choice.size()>1) {
			Collections.shuffle(choice);
			best = choice.get(0);
		}
		else {
			best = choice.get(0);
		}
		
		
		if(!this.potentialMove.isEmpty()) {
			this.updateGrid(best,"IA");
		}else {

		}
		
	}
	
	/**
	 * This method able to found a solution (but not the best and weaker than NMCS)
	 */
	public void NMCS2() {
		int max=-1;
		int indice=-1;
		Collections.shuffle(this.potentialMove);
		for (int i=0;i<this.potentialMove.size();i++) {
			int x=this.pointAvailableNext(this.potentialMove.get(i));
			if(x>max) {
				max=x;
				indice = i;
			}
		}
		
		if(!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(indice);
			this.updateGrid(x,"IA");
		}else {

		}
		
	}
	
	
	/**
	 * This method able to make a simulation and return the score of the simulation with this point
	 * @param z (Point)
	 * @param index (int)
	 * @param g (Grid5D)
	 * @return int which represents the score with this point used
	 */
	public int simulation(Point z, int index, Grid g) {
		g.pointAvailable();
		if (g.potentialMove.size() == 0) {
			System.out.println(index);
			return index;
		}else {
			int max = -1;
			int indice = -1;
			Point zz;
			Collections.shuffle(g.potentialMove);
			for (int i = 0; i < g.potentialMove.size(); i++) {
				int x = g.pointAvailableNext(g.potentialMove.get(i));
				if (x > max) {
					max = x;
					indice = i;
				}
			}

			zz = g.potentialMove.get(indice);
			g.updateGrid(zz, "IA");
			return simulation(zz, index + 1, g);
		}

	}
	
	

	public abstract void drawMoveDiagonalRight2(Point z);

	public abstract void drawMoveDiagonalLeft(Point z);

	public abstract void drawMoveHorizontal2(Point z);

	public abstract void drawMoveVertical(Point z);

	public abstract void drawMoveDiagonalRight(Point z);

	public abstract void drawMoveDiagonalLeft2(Point z);

	public abstract void drawMoveHorizontal(Point z);

	public abstract void drawMoveVertical2(Point z);

	public abstract void initiatePoint();

	public abstract void reset();
	
	public abstract boolean checkPossibleMoveHorizontal(Point z);

	public abstract boolean checkPossibleMoveVertical(Point z);

	public abstract boolean checkPossibleMoveDiagonalLeft(Point z);

	public abstract boolean checkPossibleMoveDiagonalRight(Point z);
	
	public abstract void possibleDirectionOnClick(Point z);
}
