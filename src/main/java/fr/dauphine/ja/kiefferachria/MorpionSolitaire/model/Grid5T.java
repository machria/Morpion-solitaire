package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

public class Grid5T {

	private boolean[][] points;
	private ArrayList<Point> tabCoordonnee;
	private int nbLine;
	private int nbColumn;
	private int height;
	private int width;
	private int step;
	private int center;
	private ArrayList<Point> tabCross;
	private ArrayList<Line> tabLine;
	private HashMap<Point, HashMap<Direction, List<Boolean>>> tabUsed;

	private ArrayList<Point> potentialMove;
	private ArrayList<Point> pointUser;
	private Score score;

	public Grid5T(int h, int w, int step) {

		this.step = step;
		this.height = h;
		this.width = w;
		this.reset();

	}

	public void reset() {
		// TODO Auto-generated method stub
		this.nbLine = (int) width / step;
		this.nbColumn = (int) height / step;
		this.potentialMove = new ArrayList<Point>();
		this.points = new boolean[nbLine][nbColumn];
		this.tabCoordonnee = new ArrayList<Point>();
		this.center = (int) nbLine / 2;// Same height and width
		this.tabCross = new ArrayList<Point>();
		this.tabLine = new ArrayList<Line>();
		this.tabUsed = new HashMap<Point, HashMap<Direction, List<Boolean>>>();
		this.initiatePoint();
		this.generateCross();
		this.catchCoordonnee();
		this.pointUser = new ArrayList<Point>();
		this.score = new Score();
	}

	public ArrayList<Line> getTabLine() {
		return tabLine;
	}

	public void setTabLine(ArrayList<Line> tabLine) {
		this.tabLine = tabLine;
	}

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

	private void initiatePoint() {
		// TODO Auto-generated method stub
		HashMap<Direction, List<Boolean>> dir = new HashMap<Direction, List<Boolean>>();
		List<Boolean> b = new ArrayList<>();
		b.add(false);
		b.add(false);
		dir.put(Direction.VERTICAL, new ArrayList<>(b));
		dir.put(Direction.HORIZONTAL, new ArrayList<>(b));
		dir.put(Direction.DIAGLEFT, new ArrayList<>(b));
		dir.put(Direction.DIAGRIGHT, new ArrayList<>(b));
		for (int i = 0; i < nbLine; i++) {
			for (int j = 0; j < nbColumn; j++) {
				this.points[i][j] = false;
				this.tabUsed.put(new Point(i * this.step, j * this.step),
						(HashMap<Direction, List<Boolean>>) dir.clone());
			}
		}
	}

	public ArrayList<Point> getTabCross() {
		return tabCross;
	}

	public void setTabCross(ArrayList<Point> tabCross) {
		this.tabCross = tabCross;
	}

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

	public Point firstPoint() {
		return new Point((this.center - 1) * this.step, (this.center - 1) * this.step);
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

	public ArrayList<Point> getPointUser() {
		return pointUser;
	}

	public void setPointUser(ArrayList<Point> pointUser) {
		this.pointUser = pointUser;
	}

	public void updateGrid(Point z, String s) {
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		System.out.println(this.tabUsed.get(z));
		/*
		 * if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
		 * getStep()] && checkPossibleMoveDiagonaleRight(z)) {
		 * this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()
		 * ]=true; this.drawMoveDiagonaleRight(z); this.pointUser.add(z);
		 * this.incrementeScore(s); }
		 * if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
		 * getStep()] && checkPossibleMoveDiagonaleLeft(z)) {
		 * this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()
		 * ]=true; this.drawMoveDiagonaleLeft(z); this.pointUser.add(z);
		 * this.incrementeScore(s); }
		 */
		if (!this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()]
				&& checkPossibleMoveHorizontale(z)) {
			this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()] = true;
			this.drawMoveHorizontale(z);
			this.pointUser.add(z);

			this.incrementeScore(s);
		} else {
			System.out.println("existe deja");
		}		
		System.out.println(this.tabUsed.get(z));

		/*
		 * if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.
		 * getStep()] && checkPossibleMoveVerticale(z)) {
		 * this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()
		 * ]=true; this.drawMoveVerticale(z); this.pointUser.add(z);
		 * this.incrementeScore(s); }
		 */

	}

	public void incrementeScore(String s) {
		if (s.equals("IA")) {
			this.score.setScore_computeur(this.score.getScore_computeur() + 1);
			System.out.println("score ordi: " + this.score.getScore_computeur());
		} else {
			this.score.setScore_joueur(this.score.getScore_joueur() + 1);
			System.out.println("score joueur: " + this.score.getScore_joueur());
		}
	}

	public void pointAvailable() {
		this.potentialMove.clear();
		for (int i = 0; i < nbLine; i++) {
			for (int j = 0; j < nbColumn; j++) {
				Point z = new Point(i * this.step, j * this.step);
				// try {
				if (!this.getPoints()[((int) z.getX() / this.getStep())][(int) z.getY() / this.getStep()]
						&& (
								checkPossibleMoveHorizontale(z) )) {
					this.potentialMove.add(z);
				}
				// }catch(ArrayIndexOutOfBoundsException e) {
				// System.out.println();
				// }

			}
		}
	}

	public void updateIA() {
		Collections.shuffle(this.potentialMove);
		if (!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(0);
			this.updateGrid(x, "IA");
		} else {
			System.out.println("No solution");
		}

	}

	public boolean checkPossibleMoveHorizontale(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (coordX - i >= 0 && coordY >= 0) {
				if (!this.getPoints()[coordX - i][coordY]
						|| this.tabUsed.get(new Point((coordX - i) * this.step, coordY * this.step))
								.get(Direction.HORIZONTAL).get(1).equals(true)) {
					for (int a = 1; a <= 4 - cpt_gauche; a++) {
						if (coordX + a < this.nbColumn && coordY >= 0) {
							if (!this.getPoints()[coordX + a][coordY]) {
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
		if (this.tabUsed.get(z).get(Direction.HORIZONTAL).get(1).equals(false)
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL).get(1).equals(false)
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL).get(1).equals(false)) {

			if (cpt_gauche + cpt_droite == 4) {
				return true;
			}

		}

		return false;
	}

	public void drawMoveHorizontale(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null, null, null, null, null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX = ((int) z.getX() / this.getStep());
		int coordY = ((int) z.getY() / this.getStep());
		for (int i = 1; i <= 4; i++) {
			if (!this.getPoints()[coordX - i][coordY]
					|| this.tabUsed.get(new Point((coordX - i) * this.step, coordY * this.step))
							.get(Direction.HORIZONTAL).get(1).equals(true)) {
				for (int a = 1; a <= 4 - cpt_gauche; a++) {
					if (!this.getPoints()[coordX + a][coordY]) {
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
			} else {
				cpt_gauche++;
				if (cpt_gauche == 4) {
					i = 6;
				}

			}
		}
		if (this.tabUsed.get(z).get(Direction.HORIZONTAL).get(1).equals(false)
				&& this.tabUsed.get(new Point((coordX - cpt_gauche) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL).get(1).equals(false)
				&& this.tabUsed.get(new Point((coordX + cpt_droite) * this.step, (coordY * this.step)))
						.get(Direction.HORIZONTAL).get(1).equals(false)) {

			if (cpt_gauche + cpt_droite == 4) {
				System.out.println(this.tabUsed.get(z));
				int tempLeft = 1;
				int tempRight = 1;
				List<Boolean> b = new ArrayList<>();
				b.add(true);
				b.add(true);
				List<Boolean> c = new ArrayList<>();
				c.add(true);
				c.add(false);
				debut = new Point((coordX - cpt_gauche) * this.step, (coordY * this.step));
				fin = new Point((coordX + cpt_droite) * this.step, (coordY * this.step));
				if (this.tabUsed.get(debut).get(Direction.HORIZONTAL).contains(true)) {
					this.tabUsed.get(debut).get(Direction.HORIZONTAL).clear();
					this.tabUsed.get(debut).get(Direction.HORIZONTAL).add(0,true);
					this.tabUsed.get(debut).get(Direction.HORIZONTAL).add(1,true);

				} else {
					this.tabUsed.get(debut).get(Direction.HORIZONTAL).clear();
					this.tabUsed.get(debut).get(Direction.HORIZONTAL).add(0,true);
					this.tabUsed.get(debut).get(Direction.HORIZONTAL).add(1,false);
					}
				if (this.tabUsed.get(fin).get(Direction.HORIZONTAL).contains(true)) {
					this.tabUsed.get(fin).get(Direction.HORIZONTAL).clear();
					this.tabUsed.get(fin).get(Direction.HORIZONTAL).add(0,true);
					this.tabUsed.get(fin).get(Direction.HORIZONTAL).add(1,true);
					} else {
					this.tabUsed.get(fin).get(Direction.HORIZONTAL).clear();

					this.tabUsed.get(fin).get(Direction.HORIZONTAL).add(0,true);
					this.tabUsed.get(fin).get(Direction.HORIZONTAL).add(1,false);				}

				for (int i = tempLeft; i <= cpt_gauche; i++) {
					Point t = new Point((coordX - i) * this.step, (coordY * this.step));
					if (!t.equals(debut) && !t.equals(fin)) {
						this.tabUsed.get(t).get(Direction.HORIZONTAL).clear();
						this.tabUsed.get(t).get(Direction.HORIZONTAL).addAll(b);
					}
				}
				for (int i = tempRight; i <= cpt_droite; i++) {
					Point t = new Point((coordX + i) * this.step, (coordY * this.step));
					if (!t.equals(debut) && !t.equals(fin)) {
						this.tabUsed.get(t).get(Direction.HORIZONTAL).clear();
						this.tabUsed.get(t).get(Direction.HORIZONTAL).addAll(b);
					}

				}
				if (!z.equals(debut) && !z.equals(fin)) {
					this.tabUsed.get(z).replace(Direction.HORIZONTAL, this.tabUsed.get(z).get(Direction.HORIZONTAL), b);
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

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

}