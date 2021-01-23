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
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class is the model of the game board associated with the game of join five 5D.
 * 
 * @author floryan/majid
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
    	this.possibleDirection.put(Direction.VERTICAL_BOTTOM, false);
		this.possibleDirection.put(Direction.VERTICAL_TOP, false);

		this.possibleDirection.put(Direction.HORIZONTAL_LEFT, false);
		this.possibleDirection.put(Direction.HORIZONTAL_RIGHT, false);

		this.possibleDirection.put(Direction.DIAGLEFT_TOPLEFT, false);
		this.possibleDirection.put(Direction.DIAGLEFT_BOTTOMRIGHT, false);

		this.possibleDirection.put(Direction.DIAGRIGHT_BOTTOMLEFT, false);
		this.possibleDirection.put(Direction.DIAGRIGHT_TOPRIGHT, false);

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
    	this.pointUser= new LinkedHashMap<Point, Direction>();
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
		this.possibleDirectionOnClick(z);
		ArrayList<Direction> tmp = new ArrayList<Direction>(this.possibleDirection.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), true)).map(Map.Entry::getKey).collect(Collectors.toList())); 
		if(tmp.size()>1 && s != "IA") {
			JFrame choix = new JFrame("Choix direction");
			choix.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			choix.setSize(new Dimension(200, 200));
			choix.setLocationRelativeTo(null);
			for(int i = 0 ; i<tmp.size();i++) {
				final int a = i;
				c.gridx=0;
				c.gridy=i;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridwidth=1;
				c.weightx = 1/tmp.size();
				c.weighty = 1/tmp.size();
				JButton b = new JButton(tmp.get(i).toString());
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateGrid(z, tmp.get(a), s);
						
						choix.dispose();
					}
					
				});
				choix.add(b ,c);
			}
			
			choix.setVisible(true);
			
		}
		else if(tmp.size()>1 && s == "IA") {
			Collections.shuffle(tmp);
			updateGrid(z,tmp.get(0),s);
		}
		else if(tmp.size()==1) {
			updateGrid(z,tmp.get(0),s);
			
			
		}
		else {

		}


		
	}
	
	/**
	 * Update the grid according to a point and a direction.
	 * 
	 * @param z (Point)
	 * @param d (Direction)
	 * @param s (String) which corresponds to the user
	 */
	public void updateGrid(Point z,Direction d,String s) {
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
	 * Computer play and makes random choices.
	 */
	public void updateIANaive() {
		Collections.shuffle(this.potentialMove);
		if(!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(0);
			this.updateGrid(x,"IA");
		}else {

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

		}
		else {
			this.score.setScore_joueur(this.score.getScore_joueur()+1);

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
				if(!this.getPoints()[coordX][coordY-i] || this.tabUsed.get(new Point(coordX*this.step,(coordY-i)*this.step)).get(Direction.VERTICAL).equals(true)) {
					for(int a = 1;a<=4-cpt_haut;a++) {
						if(coordX>=0&&coordY+a<this.nbColumn) {
							if(!this.getPoints()[coordX][coordY+a]) {
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

						if(coordX-a>=0&&coordY+a<this.nbColumn) {
							if(!this.getPoints()[coordX-a][coordY+a]) {

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

			if(!this.getPoints()[coordX][coordY-i] || this.tabUsed.get(new Point(coordX*this.step,(coordY-i)*this.step)).get(Direction.VERTICAL).equals(true)) {
				for(int a = 1;a<=4-cpt_haut;a++) {

					if(!this.getPoints()[coordX][coordY+a]) {

						return;
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
			else {
				if(this.tabUsed.get(new Point(coordX*this.step,(coordY-i)*this.step)).get(Direction.VERTICAL).equals(false)) {
					cpt_haut++;

					if(cpt_haut==4) {
						i = 6;



					}
				}
				
				
			}
				
		}
		if(this.tabUsed.get(z).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY-cpt_haut)*this.step)).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY+cpt_bas)*this.step)).get(Direction.VERTICAL).equals(false)) {
			


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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY-i] || this.tabUsed.get(new Point((coordX-i)*this.step,(coordY-i)*this.step)).get(Direction.DIAGLEFT).equals(true)) {
				for(int a = 1;a<=4-cpt_gauche;a++) {
					if(!this.getPoints()[coordX+a][coordY+a]) {

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
				

			}
		}
			
		


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

			}
			
		}


				
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX+i][coordY] || this.tabUsed.get(new Point((coordX+i)*this.step,coordY*this.step)).get(Direction.HORIZONTAL).equals(true)) {
				for(int a = 1;a<=4-cpt_droite;a++) {
					if(!this.getPoints()[coordX-a][coordY]) {
						return;
					}
					else{
						cpt_gauche++;
						if(cpt_gauche+cpt_droite==4) {
							a=6;
							i=6;
						}
					}
				}
			}
			else {
				cpt_droite++;
				if(cpt_droite==4) {
					i = 6;
				}
				
			}
		}
		if(this.tabUsed.get(z).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*this.step,(coordY*this.step))).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*this.step,(coordY*this.step))).get(Direction.HORIZONTAL).equals(false)) {

			if(cpt_gauche+cpt_droite==4) {
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
					debut = new Point((coordX+cpt_droite)*this.step,(coordY*this.step));
					fin = new Point((coordX-cpt_gauche)*this.step,(coordY*this.step));
					line.setP1(debut);
					line.setP5(fin);
					this.tabLine.add(line);
			}
			
			
		}
	}
	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a vertical line) 
	 * if the point chosen by the player is valid. The search begin by bottom.
	 * 
	 * @param z
	 */
	public void drawMoveVertical2(Point z) {
		int cpt_haut = 0;
		int cpt_bas = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX][coordY+i] || this.tabUsed.get(new Point(coordX*this.step,(coordY+i)*this.step)).get(Direction.VERTICAL).equals(true)) {
				for(int a = 1;a<=4-cpt_bas;a++) {
					if(!this.getPoints()[coordX][coordY-a]) {
						return;
					}
					else{
						cpt_haut++;
						if(cpt_haut+cpt_bas==4) {
							a=10;
							i=10;
							
						}
						
					}
				}
			}
			else {
				if(this.tabUsed.get(new Point(coordX*this.step,(coordY+i)*this.step)).get(Direction.VERTICAL).equals(false)) {
					cpt_bas++;
					if(cpt_bas==4) {
						i = 6;
						
					}
				}
				
				
			}
				
		}
		if(this.tabUsed.get(z).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY+cpt_bas)*this.step)).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*this.step),(coordY-cpt_haut)*this.step)).get(Direction.VERTICAL).equals(false)) {
			

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
				debut = new Point((coordX*this.step),(coordY+cpt_bas)*this.step);
				fin = new Point((coordX*this.step),(coordY-cpt_haut)*this.step);
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
			}
		}
		
	}
	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a left diagonal line) 
	 * if the point chosen by the player is valid. It begin search at right.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveDiagonalLeft2(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX+i][coordY+i] || this.tabUsed.get(new Point((coordX+i)*this.step,(coordY+i)*this.step)).get(Direction.DIAGLEFT).equals(true)) {
				for(int a = 1;a<=4-cpt_droite;a++) {
					if(!this.getPoints()[coordX-a][coordY-a]) {
						return;
					}
					else{
						cpt_gauche++;
						if(cpt_gauche+cpt_droite==4) {
							a=6;
							i=6;
						}
					}
				}
			}
			else {
				cpt_droite++;
				if(cpt_droite==4) {
					i = 6;
				}
				
			}
				
			}
		if(this.tabUsed.get(z).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*this.step,((coordY+cpt_droite)*this.step))).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step))).get(Direction.DIAGLEFT).equals(false)) {
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
				debut = new Point((coordX+cpt_droite)*this.step,((coordY+cpt_droite)*this.step));
				fin = new Point((coordX-cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step));
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
				
			}
		}
			
		
		
	}
	
	/**
	 * This method allows to fill tabLine with a start point and an end point (which represent a right diagonal line) 
	 * if the point chosen by the player is valid. The search begin at right.
	 * 
	 * @param z (Point)
	 */
	public void drawMoveDiagonalRight2(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY+i] || this.tabUsed.get(new Point((coordX-i)*this.step,(coordY+i)*this.step)).get(Direction.DIAGRIGHT).equals(true)) {
				for(int a = 1;a<=4-cpt_droite;a++) {
					//System.out.println(a);
					if(!this.getPoints()[coordX+a][coordY-a]) {
						return;
					}
					else{
						cpt_gauche++;
						if(cpt_gauche+cpt_droite==4) {
							a=6;
							i=6;
						}
					}
				}
			}
			else {
				cpt_droite++;
				if(cpt_droite==4) {
					i = 6;
				}
				
			}
				
			}
		if(this.tabUsed.get(z).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_droite)*this.step,((coordY+cpt_droite)*this.step))).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step))).get(Direction.DIAGRIGHT).equals(false)) {
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
				debut = new Point((coordX-cpt_droite)*this.step,((coordY+cpt_droite)*this.step));
				fin = new Point((coordX+cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step));
				line.setP1(debut);
				line.setP5(fin);
				this.tabLine.add(line);
			}
			
		}
		
				
	}
	
	/**
	 * This method makes it possible to fill possibleDirection in order to determine, for a given point, which are the 
	 * different possible directions.
	 * 
	 * @param z
	 *  (Point)
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

		

		Set<Entry<Point, HashMap<Direction, Boolean>>> entries = this.tabUsed.entrySet();
		HashMap<Point, HashMap<Direction, Boolean>> shallowCopy1 = new HashMap<>();

		HashMap<Point, HashMap<Direction, Boolean>> shallowCopy2 = new HashMap<>();
		for (Entry<Point, HashMap<Direction, Boolean>> p : entries) {
			Set<Entry<Direction, Boolean>> b = p.getValue().entrySet();
			HashMap<Direction, Boolean> a = new HashMap<>();
			for (Entry<Direction, Boolean> c : b) {
				a.put(c.getKey(), c.getValue());
			}
			shallowCopy1.put(p.getKey(), (HashMap<Direction, Boolean>) a.clone());
			shallowCopy2.put(p.getKey(), (HashMap<Direction, Boolean>) a.clone());

		}

		ArrayList<Line> t1 = new ArrayList<Line>();
		ArrayList<Line> t2 = new ArrayList<Line>();

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
			

			if (this.tabLine.size() > 0) {
				p1 = this.tabLine.get(this.tabLine.size() - 1).getP1();
				p2 = this.tabLine.get(this.tabLine.size() - 1).getP5();
				this.tabLine.remove(this.tabLine.size() - 1);

			}
			this.setTabLine(t1);
			this.tabUsed = new HashMap<>(shallowCopy1);
			this.drawMoveHorizontal2(z);
			

			if (this.tabLine.size() > 0) {
				p12 = this.tabLine.get(this.tabLine.size() - 1).getP1();
				p22 = this.tabLine.get(this.tabLine.size() - 1).getP5();
				this.tabLine.remove(this.tabLine.size() - 1);
			}
			

			if (!((p1.equals(p12) || p1.equals(p22)) && (p2.equals(p12) || p2.equals(p22)))) {
				this.possibleDirection.replace(Direction.HORIZONTAL_LEFT, true);
				this.possibleDirection.replace(Direction.HORIZONTAL_RIGHT, true);
			}

			else {
				this.possibleDirection.replace(Direction.HORIZONTAL_LEFT, true);
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
