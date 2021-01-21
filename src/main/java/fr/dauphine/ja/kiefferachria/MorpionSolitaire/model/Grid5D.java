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
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This class is the model of the game board associated with the game of join five 5D.
 * 
 * @author floryan
 *
 */
public class Grid5D {

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
	 * HashMap of all possible movements for a given point.
	 */
    private HashMap<Point, HashMap<Direction, Boolean>> tabUsed;
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
    private HashMap<Point,Direction> pointUser;
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
    private HashMap<Direction,Boolean> possibleDirection;
    
    /**
     * Grid 5D constructor.
     * 
     * @param h (height)
     * @param w (width)
     * @param step (gap)
     */
	public Grid5D(int h,int w, int step){
    	
    	this.step=step;
    	this.height=h;
    	this.width=w;
    	this.scoreHistory = new ArrayList<Integer>();
    	this.reset();

    }
	
/////////////////////////
//   				   //	
// Getters and Setters //
//   				   //
/////////////////////////
	
	
    public ArrayList<Line> getTabLine() {
		return tabLine;
	}
	public void setTabLine(ArrayList<Line> tabLine) {
		this.tabLine = tabLine;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	public ArrayList<Point> getPotentialMove() {
		return this.potentialMove;
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
	public Point getNeigh(int x,int y) {
		
    	Point t = new Point(x,y);
    	Point res= t;
    	double dist=Point.distanceSq(t.getX(), t.getY()-this.step/2, this.tabCoordonnee.get(0).getX(), this.tabCoordonnee.get(0).getY());;
    	double min= dist;
    	for (int i = 1; i<this.tabCoordonnee.size();i++) {
    		dist=Point.distanceSq(t.getX(), t.getY()-this.step/2, this.tabCoordonnee.get(i).getX(), this.tabCoordonnee.get(i).getY());
    		if(dist<min) {
    			min=dist;
    			res = this.tabCoordonnee.get(i);
    		}
    	}
    	System.out.println(t);
    	System.out.println(res);
    	return res;
    }
	public HashMap<Point, Direction> getPointUser() {
		return pointUser;
	}
	
////////////////////////
//	  				  //
// Game setup methods //
//	  				  //
////////////////////////
	
	/**
	 * Initiate instance of class
	 */
    private void initiatePoint() {
		// TODO Auto-generated method stub
    	HashMap<Direction,Boolean> dir = new HashMap<Direction, Boolean>();
    	dir.put(Direction.VERTICAL,false);
    	dir.put(Direction.HORIZONTAL,false);
    	dir.put(Direction.DIAGLEFT,false);
    	dir.put(Direction.DIAGRIGHT,false);
    	this.possibleDirection.put(Direction.VERTICAL,false);
    	this.possibleDirection.put(Direction.HORIZONTAL,false);
    	this.possibleDirection.put(Direction.DIAGLEFT,false);
    	this.possibleDirection.put(Direction.DIAGRIGHT,false);
		for(int i = 0;i<nbLine;i++) {
			for(int j = 0;j<nbColumn;j++) {
				this.points[i][j]=false;
				this.tabUsed.put(new Point(i*this.step,j*this.step ),(HashMap<Direction, Boolean>) dir.clone());
			}
		}
	}

    /**
	 * Add all starting point in tabCross and put at true this points in points.
	 */
    public void generateCross() {
		Point c = firstPoint();
		Point temp=c;
		for (int i=0;i<4;i++) {
			c= new Point((int)c.getX(), (int) (c.getY()-(this.step*i)));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX()+(this.step*i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX(), (int) (c.getY()+(this.step*i)));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX()+(this.step*i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX(), (int) (c.getY()+(this.step*i)));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX()-(this.step*i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX(), (int) (c.getY()+(this.step*i)));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX()-(this.step*i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX(), (int) (c.getY()-(this.step*i)));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX()-(this.step*i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<4;i++) {
			c= new Point((int)c.getX(), (int) (c.getY()-(this.step*i)));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
		c=this.tabCross.get(this.tabCross.size()-1);
		temp=c;
		for (int i=1;i<3;i++) {
			c= new Point((int)c.getX()+(this.step*i), (int) (c.getY()));
			this.tabCross.add(c);
			this.points[((int)c.getY()/this.step)][((int)c.getX()/this.step)]=true;
			c=temp;
		}
	}
    
    /**
	 * This method is used to determine the point of the center
	 * 
	 * @return center point
	 */
	public Point firstPoint() {
    	return new Point((this.center-1)*this.step,(this.center-1)*this.step);
    }

	/**
	 * Reset all instances of Grid5T Object.
	 */
	public void reset() {
    	this.nbLine=(int)width/step;
    	this.nbColumn=(int)height/step;
    	if(!(this.pointUser==null))
    		this.scoreHistory.add(this.pointUser.size());
    	this.potentialMove = new ArrayList<Point>();
    	this.potentialMoveNext = new ArrayList<Point>();
    	this.possibleDirection = new HashMap<Direction,Boolean>(4);

    	this.points = new boolean [nbLine][nbColumn];
    	this.tabCoordonnee=new ArrayList<Point>();
    	this.center=(int) nbLine/2;//Same height and width
    	this.tabCross=new ArrayList<Point>();
    	this.tabLine=new ArrayList<Line>();
    	this.tabUsed = new HashMap<Point, HashMap<Direction, Boolean>>();
    	this.initiatePoint();
    	this.generateCross();
    	this.catchCoordonnee();
    	this.pointUser= new LinkedHashMap();
    	this.score = new Score();
	}

	/**
	 * Catch all coordinates of the grid.
	 */
	public void catchCoordonnee() {
		int x=0;
		int y=0;
		for(int i = 0 ; i < this.nbLine-1;i++) {
			y=y+step;
			for(int j = 0 ; j < this.nbColumn-1; j++) {
				x=x+step;
				this.tabCoordonnee.add(new Point(x,y));
			}
			x=0;
			
		}
	}
	
////////////////////
//	  			  //
// Update methods //
//	  			  //
////////////////////
	
	/**
	 * Update the grid according to the choice of the players.
	 * 
	 * @param z (Point)
	 * @param s (player or IA)
	 */
	public void updateGrid(Point z,String s) {
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		this.possibleDirectionOnClick(z);
		ArrayList<Direction> tmp = new ArrayList<Direction>(this.possibleDirection.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), true)).map(Map.Entry::getKey).collect(Collectors.toList())); 
		if(tmp.size()>1 && s != "IA") {
			JFrame choix = new JFrame("Choix direction");
			choix.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			choix.setSize(new Dimension(200, 200));
			choix.setLocationRelativeTo(null);
			for(int i = 0 ; i<tmp.size();i++) {
				final int a = new Integer(i);
				c.gridx=0;
				c.gridy=i;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridwidth=1;
				c.weightx = 1/tmp.size();
				c.weighty = 1/tmp.size();
				JButton b = new JButton(tmp.get(i).toString());
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(tmp.get(a)==Direction.VERTICAL) {
							drawMoveVertical(z);
							getPointUser().put(z,Direction.VERTICAL);
						}
						if(tmp.get(a)==Direction.HORIZONTAL) {
							drawMoveHorizontal(z);
							getPointUser().put(z,Direction.HORIZONTAL);

						}
						if(tmp.get(a)==Direction.DIAGLEFT) {
							drawMoveDiagonalLeft(z);
							getPointUser().put(z,Direction.DIAGLEFT);

						}
						if(tmp.get(a)==Direction.DIAGRIGHT) {
							drawMoveDiagonalRight(z);
							getPointUser().put(z,Direction.DIAGRIGHT);

						}
						choix.dispose();
					}
					
				});
				choix.add(b ,c);
			}
			
			choix.setVisible(true);
			this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;	
			this.incrementeScore(s);
		}
		else if(tmp.size()>1 && s == "IA") {
			Collections.shuffle(tmp);
			if(tmp.get(0)==Direction.VERTICAL) {
				this.drawMoveVertical(z);
				getPointUser().put(z,Direction.VERTICAL);

			}
			if(tmp.get(0)==Direction.HORIZONTAL) {
				this.drawMoveHorizontal(z);
				getPointUser().put(z,Direction.HORIZONTAL);

			}
			if(tmp.get(0)==Direction.DIAGLEFT) {
				this.drawMoveDiagonalLeft(z);
				getPointUser().put(z,Direction.DIAGLEFT);

			}
			if(tmp.get(0)==Direction.DIAGRIGHT) {
				this.drawMoveDiagonalRight(z);
				getPointUser().put(z,Direction.DIAGRIGHT);

			}
			this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;	
			this.incrementeScore(s);
		}
		else if(tmp.size()==1) {
			if(tmp.get(0)==Direction.VERTICAL) {
				this.drawMoveVertical(z);
				getPointUser().put(z,Direction.VERTICAL);

			}
			if(tmp.get(0)==Direction.HORIZONTAL) {
				this.drawMoveHorizontal(z);
				getPointUser().put(z,Direction.HORIZONTAL);

			}
			if(tmp.get(0)==Direction.DIAGLEFT) {
				this.drawMoveDiagonalLeft(z);
				getPointUser().put(z,Direction.DIAGLEFT);

			}
			if(tmp.get(0)==Direction.DIAGRIGHT) {
				this.drawMoveDiagonalRight(z);
				getPointUser().put(z,Direction.DIAGRIGHT);

			}
			this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;	

			this.incrementeScore(s);
			
		}
		else {
			System.out.println("Pas de mouvement possible");
		}
		
		
	}
	
	/**
	 * Update the grid according to a point and a direction.
	 * 
	 * @param z (Point)
	 * @param d (Direction)
	 */
	public void updateGrid(Point z,Direction d) {
		if(d==Direction.VERTICAL) {
			this.drawMoveVertical(z);
			getPointUser().put(z,Direction.VERTICAL);

		}
		if(d==Direction.HORIZONTAL) {
			this.drawMoveHorizontal(z);
			getPointUser().put(z,Direction.HORIZONTAL);

		}
		if(d==Direction.DIAGLEFT) {
			this.drawMoveDiagonalLeft(z);
			getPointUser().put(z,Direction.DIAGLEFT);

		}
		if(d==Direction.DIAGRIGHT) {
			this.drawMoveDiagonalRight(z);
			getPointUser().put(z,Direction.DIAGRIGHT);

		}
		this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;	

		this.incrementeScore("player");
	}
	
	/**
	 * Computer play and makes random choices.
	 */
	public void updateIANaive() {
		Collections.shuffle(this.potentialMove);
		if(!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(0);
			this.updateGrid(x,"IA");
		}else {
			System.out.println("No solution");
		}
		
	}
	
	/**
	 * This method able to increment score. 
	 * 
	 * @param s (player or IA)
	 */
	public void incrementeScore(String s) {
		if(s.equals("IA")) {
			this.score.setScore_computeur(this.score.getScore_computeur()+1);
			System.out.println("score ordi: "+this.score.getScore_computeur());
		}
		else {
			this.score.setScore_joueur(this.score.getScore_joueur()+1);
			System.out.println("score joueur: "+this.score.getScore_joueur());
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	  																												  //
// Methods which allow to capture the possible points and also to capture the possible points with respect to a point //
//	  																												  //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * This method able to fills the table of possible available points at time t.
	 */
	public void pointAvailable() {
		this.potentialMove.clear();
		for(int i = 0;i<nbLine;i++) {
			for(int j = 0;j<nbColumn;j++) {
				Point z = new Point(i*this.step,j*this.step);
					if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && (checkPossibleMoveDiagonalRight(z)|| checkPossibleMoveDiagonalLeft(z)|| checkPossibleMoveHorizontal(z)|| checkPossibleMoveVertical(z))) {
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
		boolean test=false;
		if (this.getPoints()[((int)b.getX()/this.getStep())][(int)b.getY()/this.getStep()]==false) {
			this.getPoints()[((int)b.getX()/this.getStep())][(int)b.getY()/this.getStep()]=true;	
			test = true;
		}
		for(int i = 0;i<nbLine;i++) {
			for(int j = 0;j<nbColumn;j++) {
				Point z = new Point(i*this.step,j*this.step);
					if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && (checkPossibleMoveDiagonalRight(z)|| checkPossibleMoveDiagonalLeft(z)|| checkPossibleMoveHorizontal(z)|| checkPossibleMoveVertical(z))) {
						this.potentialMoveNext.add(z);
					}
			}
			
		}
		if (test) {
			this.getPoints()[((int)b.getX()/this.getStep())][(int)b.getY()/this.getStep()]=false;
		}
		return this.potentialMoveNext.size();

	}
	
	/**
	 * This method able to found a solution (but not the best)
	 */
	public void NMCS() {
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
			System.out.println("No solution");
		}
		
	}
	
	/**
	 * This method able to found a solution (but not the best and better than NMCS)
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
			System.out.println("No solution");
		}
		
	}
	
//////////////////////////
//						//
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
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(coordX-i>=0&&coordY>=0) {
				if(!this.getPoints()[coordX-i][coordY] || this.tabUsed.get(new Point((coordX-i)*this.step,coordY*this.step)).get(Direction.HORIZONTAL).equals(true)) {
					for(int a = 1;a<=4-cpt_gauche;a++) {
						if(coordX+a<this.nbColumn&&coordY>=0) {
							if(!this.getPoints()[coordX+a][coordY]) {
								System.out.println("Pas de line possible sur l'horizontale");
								return false;
							}
							else{
								cpt_droite++;
								if(cpt_gauche+cpt_droite==4) {
									a=6;
									i=6;
								}
							}
						}

						
					}
				}
				else {
					cpt_gauche++;
					if(cpt_gauche==4) {
						i = 6;
					}
					
				}
			}
			
		}
		if(this.tabUsed.get(z).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*this.step,(coordY*this.step))).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*this.step,(coordY*this.step))).get(Direction.HORIZONTAL).equals(false)) {

			if(cpt_gauche+cpt_droite==4) {
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
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(coordX>=0&&coordY-i>=0) {
				System.out.println(this.getPoints()[coordX][coordY-i]);
				if(!this.getPoints()[coordX][coordY-i] || this.tabUsed.get(new Point(coordX*this.step,(coordY-i)*this.step)).get(Direction.VERTICAL).equals(true)) {
					for(int a = 1;a<=4-cpt_haut;a++) {
						//System.out.println(a);
						if(coordX>=0&&coordY+a<this.nbColumn) {
							if(!this.getPoints()[coordX][coordY+a]) {
								System.out.println("Pas de line possible sur la vertical");
								return false;
							}
							else{
								cpt_bas++;
								if(cpt_haut+cpt_bas==4) {
									a=10;
									i=10;
								}
							}
						}

						
					}
				}
				else {
					if(this.tabUsed.get(new Point(coordX*this.step,(coordY-i)*this.step)).get(Direction.VERTICAL).equals(false)) {
						cpt_haut++;
						System.out.println("cpt_haut++");
						if(cpt_haut==4) {
							i = 6;
						}
					}
					
					
				}
			}

			
				
		}
		if(this.tabUsed.get(z).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY-cpt_haut)*this.step)).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY+cpt_bas)*this.step)).get(Direction.VERTICAL).equals(false)) {
			if(cpt_haut+cpt_bas==4) {
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
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(coordX-i>=0&&coordY-i>=0) {
				if(!this.getPoints()[coordX-i][coordY-i] || this.tabUsed.get(new Point((coordX-i)*this.step,(coordY-i)*this.step)).get(Direction.DIAGLEFT).equals(true)) {
					for(int a = 1;a<=4-cpt_gauche;a++) {
						if(coordX+a<this.nbColumn&&coordY+a<this.nbColumn) {
							if(!this.getPoints()[coordX+a][coordY+a]) {
								System.out.println("Pas de line possible sur la diagonale gauche");
								return false;
							}
							else{
								cpt_droite++;
								if(cpt_gauche+cpt_droite==4) {
									a=6;
									i=6;
								}
							}
						}

						
					}
				}
				else {
					cpt_gauche++;
					if(cpt_gauche==4) {
						i = 6;
					}
					
				}
			}
			
				
			}
		if(this.tabUsed.get(z).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step))).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*this.step,((coordY+cpt_droite)*this.step))).get(Direction.DIAGLEFT).equals(false)) {
			if(cpt_gauche+cpt_droite==4) {
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
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(coordX+i<this.nbColumn&&coordY-i>=0) {
				if(!this.getPoints()[coordX+i][coordY-i] || this.tabUsed.get(new Point((coordX+i)*this.step,(coordY-i)*this.step)).get(Direction.DIAGRIGHT).equals(true)) {
					for(int a = 1;a<=4-cpt_gauche;a++) {
						//System.out.println(a);
						if(coordX-a>=0&&coordY+a<this.nbColumn) {
							if(!this.getPoints()[coordX-a][coordY+a]) {
								System.out.println("Pas de line possible sur la diagonale droite");
								return false;
							}
							else{
								cpt_droite++;
								if(cpt_gauche+cpt_droite==4) {
									a=6;
									i=6;
								}
							}
						}

						
					}
				}
				else {
					cpt_gauche++;
					if(cpt_gauche==4) {
						i = 6;
					}
					
				}
			}

			
				
			}
		if(this.tabUsed.get(z).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step))).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_droite)*this.step,((coordY+cpt_droite)*this.step))).get(Direction.DIAGRIGHT).equals(false)) {
			if(cpt_gauche+cpt_droite==4) {
				return true;
			}
			
		}
		return false;		
	}
 
////////////////////////////////////////////////////////////////////
//  															  //
// Methods that allow you to draw the lines when they are correct //
//  															  //
////////////////////////////////////////////////////////////////////
	
	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a horizontal line) 
	 * if the point chosen by the player is valid.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveHorizontal(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY] || this.tabUsed.get(new Point((coordX-i)*this.step,coordY*this.step)).get(Direction.HORIZONTAL).equals(true)) {
				for(int a = 1;a<=4-cpt_gauche;a++) {
					if(!this.getPoints()[coordX+a][coordY]) {
						System.out.println("Pas de line possible sur l'horizontale");
						return;
					}
					else{
						cpt_droite++;
						if(cpt_gauche+cpt_droite==4) {
							a=6;
							i=6;
						}
					}
				}
			}
			else {
				cpt_gauche++;
				if(cpt_gauche==4) {
					i = 6;
				}
				
			}
		}
		if(this.tabUsed.get(z).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*this.step,(coordY*this.step))).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*this.step,(coordY*this.step))).get(Direction.HORIZONTAL).equals(false)) {

			if(cpt_gauche+cpt_droite==4) {
				System.out.println(this.tabUsed.get(z));
					int tempLeft=1;
					int tempRight=1;
					for(int i =tempLeft;i<=cpt_gauche;i++) {
						Point t= new Point((coordX-i)*this.step,(coordY*this.step));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, false, true);
					}
					for(int i =tempRight;i<=cpt_droite;i++) {
						Point t= new Point((coordX+i)*this.step,(coordY*this.step));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, false, true);
					}
					this.tabUsed.get(z).replace(Direction.HORIZONTAL, false, true);
					debut = new Point((coordX-cpt_gauche)*this.step,(coordY*this.step));
					fin = new Point((coordX+cpt_droite)*this.step,(coordY*this.step));
					line.setP1(debut);
					line.setP5(fin);
					this.tabLine.add(line);
					System.out.println("Ajout");
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			System.out.println(this.getPoints()[coordX][coordY-i]);
			if(!this.getPoints()[coordX][coordY-i] || this.tabUsed.get(new Point(coordX*this.step,(coordY-i)*this.step)).get(Direction.VERTICAL).equals(true)) {
				for(int a = 1;a<=4-cpt_haut;a++) {
					//System.out.println(a);
					if(!this.getPoints()[coordX][coordY+a]) {
						System.out.println("Pas de line possible sur la vertical");
						return;
					}
					else{
						cpt_bas++;
						if(cpt_haut+cpt_bas==4) {
							a=10;
							i=10;
							System.out.println("Je suis dans if");
							System.out.println("Haut"+cpt_haut);
							System.out.println("Bas"+cpt_bas);
						}
						System.out.println("cpt_bas++");
						System.out.println(cpt_haut+cpt_bas);
					}
				}
			}
			else {
				if(this.tabUsed.get(new Point(coordX*this.step,(coordY-i)*this.step)).get(Direction.VERTICAL).equals(false)) {
					cpt_haut++;
					System.out.println("cpt_haut++");
					if(cpt_haut==4) {
						i = 6;
						System.out.println("Je suis dans else");
						System.out.println("Haut"+cpt_haut);
						System.out.println("Bas"+cpt_bas);
					}
				}
				
				
			}
				
		}
		if(this.tabUsed.get(z).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY-cpt_haut)*this.step)).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY+cpt_bas)*this.step)).get(Direction.VERTICAL).equals(false)) {
			
			System.out.println(this.tabUsed.get(z));

			if(cpt_haut+cpt_bas==4) {

				int tempHaut=1;
				int tempBas=1;
				for(int i =tempHaut;i<=cpt_haut;i++) {
					Point t= new Point((coordX)*this.step,((coordY-i)*this.step));
					this.tabUsed.get(t).replace(Direction.VERTICAL, false, true);
				}
				for(int i =tempBas;i<=cpt_bas;i++) {
					Point t= new Point((coordX)*this.step,((coordY+i)*this.step));
					this.tabUsed.get(t).replace(Direction.VERTICAL, false, true);
				}
				this.tabUsed.get(z).replace(Direction.VERTICAL, false, true);
				debut = new Point((coordX*this.step),(coordY-cpt_haut)*this.step);
				fin = new Point((coordX*this.step),(coordY+cpt_bas)*this.step);
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println("Ajout");
			}
		}
		System.out.println("Haut"+cpt_haut);
		System.out.println("Bas"+cpt_bas);
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY-i] || this.tabUsed.get(new Point((coordX-i)*this.step,(coordY-i)*this.step)).get(Direction.DIAGLEFT).equals(true)) {
				for(int a = 1;a<=4-cpt_gauche;a++) {
					if(!this.getPoints()[coordX+a][coordY+a]) {
						System.out.println("Pas de line possible sur la diagonale gauche");
						return;
					}
					else{
						cpt_droite++;
						if(cpt_gauche+cpt_droite==4) {
							a=6;
							i=6;
						}
					}
				}
			}
			else {
				cpt_gauche++;
				if(cpt_gauche==4) {
					i = 6;
				}
				
			}
				
			}
		if(this.tabUsed.get(z).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step))).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*this.step,((coordY+cpt_droite)*this.step))).get(Direction.DIAGLEFT).equals(false)) {
			System.out.println(this.tabUsed.get(z));
			if(cpt_gauche+cpt_droite==4) {
				int tempLeft=1;
				int tempRight=1;
				for(int i =tempLeft;i<=cpt_gauche;i++) {
					Point t= new Point((coordX-i)*this.step,((coordY-i)*this.step));
					this.tabUsed.get(t).replace(Direction.DIAGLEFT, false, true);
				}
				for(int i =tempRight;i<=cpt_droite;i++) {
					Point t= new Point((coordX+i)*this.step,((coordY+i)*this.step));
					this.tabUsed.get(t).replace(Direction.DIAGLEFT, false, true);
				}
				this.tabUsed.get(z).replace(Direction.DIAGLEFT, false, true);
				debut = new Point((coordX-cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step));
				fin = new Point((coordX+cpt_droite)*this.step,((coordY+cpt_droite)*this.step));
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				
				System.out.println("Ajout");
			}
		}
			
		
		System.out.println("Gauche "+cpt_gauche);
		System.out.println(" Droite "+cpt_droite);
	}
	
	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a right diagonal line) 
	 * if the point chosen by the player is valid.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveDiagonalRight(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX+i][coordY-i] || this.tabUsed.get(new Point((coordX+i)*this.step,(coordY-i)*this.step)).get(Direction.DIAGRIGHT).equals(true)) {
				for(int a = 1;a<=4-cpt_gauche;a++) {
					if(!this.getPoints()[coordX-a][coordY+a]) {
						System.out.println("Pas de line possible sur la diagonale droite");
						return;
					}
					else{
						cpt_droite++;
						if(cpt_gauche+cpt_droite==4) {
							a=6;
							i=6;
						}
					}
				}
			}
			else {
				cpt_gauche++;
				if(cpt_gauche==4) {
					i = 6;
				}
				
			}
				
			}
		if(this.tabUsed.get(z).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step))).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_droite)*this.step,((coordY+cpt_droite)*this.step))).get(Direction.DIAGRIGHT).equals(false)) {
			System.out.println(this.tabUsed.get(z));
			if(cpt_gauche+cpt_droite==4) {
				int tempLeft=1;
				int tempRight=1;
				for(int i =tempLeft;i<=cpt_gauche;i++) {
					Point t= new Point((coordX+i)*this.step,((coordY-i)*this.step));
					this.tabUsed.get(t).replace(Direction.DIAGRIGHT, false, true);
				}
				for(int i =tempRight;i<=cpt_droite;i++) {
					Point t= new Point((coordX-i)*this.step,((coordY+i)*this.step));
					this.tabUsed.get(t).replace(Direction.DIAGRIGHT, false, true);
				}
				this.tabUsed.get(z).replace(Direction.DIAGRIGHT, false, true);
				debut = new Point((coordX+cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step));
				fin = new Point((coordX-cpt_droite)*this.step,((coordY+cpt_droite)*this.step));
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				System.out.println("Ajout");
			}
			
		}
		System.out.println(cpt_gauche);
		System.out.println(cpt_droite);
				
	}
	
	/**
	 * This method makes it possible to fill possibleDirection in order to determine, for a given point, which are the 
	 * different possible directions.
	 * 
	 * @param z
	 */
	public void possibleDirectionOnClick(Point z) {
		this.possibleDirection.replace(Direction.VERTICAL,false);
    	this.possibleDirection.replace(Direction.HORIZONTAL,false);
    	this.possibleDirection.replace(Direction.DIAGLEFT,false);
    	this.possibleDirection.replace(Direction.DIAGRIGHT,false);
    	
		if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveDiagonalLeft(z)==true) {
			this.possibleDirection.replace(Direction.DIAGLEFT, true);
		}
		if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveDiagonalRight(z)==true) {
			this.possibleDirection.replace(Direction.DIAGRIGHT, true);
		}
		if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveHorizontal(z)==true) {
			this.possibleDirection.replace(Direction.HORIZONTAL, true);
		}
		if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveVertical(z)==true) {
			this.possibleDirection.replace(Direction.VERTICAL, true);
		}
	}
	   
}
