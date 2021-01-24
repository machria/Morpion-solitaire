package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Map.Entry;



import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This class is the model of the game board associated with the game of join five 5D.
 * 
 * @author floryan/majid
 *
 */
public class Grid5D extends Grid {

    /**
	 * HashMap of all possible movements for a given point.
	 */
    private HashMap<Point, HashMap<Direction, Boolean>> tabUsed;
    
    /**
   	 * Keep in memory the best score simulated.
   	 */
     private int simBestScore;
    
    /**
     * Grid 5D constructor.
     * 
     * @param h (height)
     * @param w (width)
     * @param step (gap)
     */
	public Grid5D(int h,int w, int step){
		super(h,w,step);
    	this.simBestScore=0;
    }
	
	public Grid5D(Grid5D g){
		super(g.getHeight(),g.getWidth(),g.getStep());
    	setGrid(g);

    }
	
/////////////////////////
//   				   //	
// Getters and Setters //
//   				   //
/////////////////////////
	
	
   public void setGrid(Grid5D g) {
    	this.setStep(g.getStep());
    	this.setHeight(g.getHeight());
    	this.setWidth(g.getWidth());
    	this.setScoreHistory(new ArrayList<Integer>(g.getScoreHistory()));
    	this.setNbLine((int)g.getWidth()/g.getStep());
    	this.setNbColumn((int)g.getHeight()/g.getStep());
    	this.setCenter((int) g.getNbLine()/2);//Same height and width
    	
    	this.setPotentialMove(new ArrayList<Point>());
    	for(int i=0;i<g.getPotentialMove().size();i++) {
    		this.getPotentialMove().add(g.getPotentialMove().get(i));
    	}
    	
    	this.setPotentialMoveNext(new ArrayList<Point>(g.getPotentialMoveNext()));
    	for(int i=0;i<g.getPotentialMoveNext().size();i++) {
    		this.getPotentialMoveNext().add(g.getPotentialMoveNext().get(i));
    	}
    	
    	this.setPossibleDirection(new HashMap<Direction,Boolean>(g.getPossibleDirection()));
    	
    	
    	this.setPoints(new boolean [getNbLine()][getNbColumn()]);
    	for(int i = 0;i<getNbLine();i++) {
			for(int j = 0;j<getNbColumn();j++) {
				this.getPoints()[i][j]=g.getPoints()[i][j];
			}
		}
    	this.setTabCoordonnee(new ArrayList<Point>(g.getTabCoordonnee()));
    	this.setTabCross(new ArrayList<Point>(g.getTabCross()));
    	g.catchCoordonnee();
    	
    	this.setTabLine(new ArrayList<Line>(g.getTabLine()));
    	for(int i=0;i<g.getTabLine().size();i++) {
    		getTabLine().add(g.getTabLine().get(i));
    	}
    	
    	this.tabUsed = new HashMap<Point, HashMap<Direction, Boolean>>(g.tabUsed);
    	Set<Entry<Point, HashMap<Direction, Boolean>>> entries = g.tabUsed.entrySet();

		for (Entry<Point, HashMap<Direction, Boolean>> p : entries) {
			Set<Entry<Direction, Boolean>> b = p.getValue().entrySet();
			HashMap<Direction, Boolean> a = new HashMap<>();
			for (Entry<Direction, Boolean> c : b) {
				a.put(c.getKey(), c.getValue());
			}
			this.tabUsed.put(p.getKey(), (HashMap<Direction, Boolean>) a.clone());

		}
    	this.setPointUser(new LinkedHashMap<Point, Direction>(g.getPointUser()));
    	this.setScore(new Score());
		
	}

	
	
	
////////////////////////
//	  				  //
// Game setup methods //
//	  				  //
////////////////////////
	
	/**
	 * Initiate instance of class
	 */
    public void initiatePoint() {
		// TODO Auto-generated method stub
    	HashMap<Direction,Boolean> dir = new HashMap<Direction, Boolean>();
    	dir.put(Direction.VERTICAL,false);
    	dir.put(Direction.HORIZONTAL,false);
    	dir.put(Direction.DIAGLEFT,false);
    	dir.put(Direction.DIAGRIGHT,false);
    	this.getPossibleDirection().put(Direction.VERTICAL_BOTTOM, false);
		this.getPossibleDirection().put(Direction.VERTICAL_TOP, false);

		this.getPossibleDirection().put(Direction.HORIZONTAL_LEFT, false);
		this.getPossibleDirection().put(Direction.HORIZONTAL_RIGHT, false);

		this.getPossibleDirection().put(Direction.DIAGLEFT_TOPLEFT, false);
		this.getPossibleDirection().put(Direction.DIAGLEFT_BOTTOMRIGHT, false);

		this.getPossibleDirection().put(Direction.DIAGRIGHT_BOTTOMLEFT, false);
		this.getPossibleDirection().put(Direction.DIAGRIGHT_TOPRIGHT, false);

		for(int i = 0;i<getNbLine();i++) {
			for(int j = 0;j<getNbColumn();j++) {
				this.getPoints()[i][j]=false;
				this.tabUsed.put(new Point(i*this.getStep(),j*this.getStep() ),(HashMap<Direction, Boolean>) dir.clone());
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
    	this.tabUsed = new HashMap<Point, HashMap<Direction, Boolean>>();
    	this.initiatePoint();
    	this.generateCross();
    	this.catchCoordonnee();
    	this.setPointUser(new LinkedHashMap<Point, Direction>());
    	this.setScore(new Score());
	}

	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//																													  //
// Methods which allow to capture the possible points and also to capture the possible points with respect to a point //
//																													  //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Another try of implementation of IA algorithm
	 */
	public void NMCS3() {
		this.pointAvailable();
		double max=-1;
		int indice=-1;
		Collections.shuffle(this.getPotentialMove());
		Grid5D g = new Grid5D(this);
		for (int i=0;i<this.getPotentialMove().size();i++) {
			double x=this.pointAvailableNext(this.getPotentialMove().get(i))+.8*this.simulation(this.getPotentialMove().get(i), this.getPointUser().size(), g);
			if(x>max) {
				max=x;
				indice = i;
			}
			g = new Grid5D(this);

		}
		
		if(!this.getPotentialMove().isEmpty()) {
			Point x = this.getPotentialMove().get(indice);
			this.updateGrid(x,"IA");
		}else {

		}
		
	}
	
	/**
	 * Another try of implementation of IA algorithm
	 */
	public void NMCS4() {
		
		long endTimeMillis = System.currentTimeMillis() + 10000;
		this.pointAvailable();
		int max = -1;
		int indice = -1;
		Collections.shuffle(this.getPotentialMove());
		Grid5D g = new Grid5D(this);
		int simtemp = 1;

		int maxlocal = -1;

		do {
			maxlocal = 0;
			for (int i = 0; i < this.getPotentialMove().size(); i++) {
				simtemp = this.simulation(this.getPotentialMove().get(i), this.getPointUser().size(), g);

				if (simtemp > max) {
					max = simtemp;
					if (maxlocal < max) {
						maxlocal = max;
						indice = i;
					}
				}
				g = new Grid5D(this);
			}
		} while (max < this.simBestScore && System.currentTimeMillis() > endTimeMillis);
		this.simBestScore = maxlocal;

		
		
		
		if (!this.getPotentialMove().isEmpty()) {
			Point x = this.getPotentialMove().get(indice);
			this.updateGrid(x, "IA");
		} else {

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
				if(!this.getPoints()[coordX-i][coordY] || this.tabUsed.get(new Point((coordX-i)*this.getStep(),coordY*this.getStep())).get(Direction.HORIZONTAL).equals(true)) {
					for(int a = 1;a<=4-cpt_gauche;a++) {
						if(coordX+a<this.getNbColumn()&&coordY>=0) {
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
		if(this.tabUsed.get(z).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*this.getStep(),(coordY*this.getStep()))).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*this.getStep(),(coordY*this.getStep()))).get(Direction.HORIZONTAL).equals(false)) {

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
				if(!this.getPoints()[coordX][coordY-i] || this.tabUsed.get(new Point(coordX*this.getStep(),(coordY-i)*this.getStep())).get(Direction.VERTICAL).equals(true)) {
					for(int a = 1;a<=4-cpt_haut;a++) {
						if(coordX>=0&&coordY+a<this.getNbColumn()) {
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
					if(this.tabUsed.get(new Point(coordX*getStep(),(coordY-i)*getStep())).get(Direction.VERTICAL).equals(false)) {
						cpt_haut++;

						if(cpt_haut==4) {
							i = 6;
						}
					}
					
					
				}
			}

			
				
		}
		if(this.tabUsed.get(z).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*getStep()),(coordY-cpt_haut)*getStep())).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*getStep()),(coordY+cpt_bas)*getStep())).get(Direction.VERTICAL).equals(false)) {
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
				if(!this.getPoints()[coordX-i][coordY-i] || this.tabUsed.get(new Point((coordX-i)*getStep(),(coordY-i)*getStep())).get(Direction.DIAGLEFT).equals(true)) {
					for(int a = 1;a<=4-cpt_gauche;a++) {
						if(coordX+a<this.getNbColumn()&&coordY+a<this.getNbColumn()) {
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
		if(this.tabUsed.get(z).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()))).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()))).get(Direction.DIAGLEFT).equals(false)) {
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
			if(coordX+i<this.getNbColumn()&&coordY-i>=0) {
				if(!this.getPoints()[coordX+i][coordY-i] || this.tabUsed.get(new Point((coordX+i)*getStep(),(coordY-i)*getStep())).get(Direction.DIAGRIGHT).equals(true)) {
					for(int a = 1;a<=4-cpt_gauche;a++) {

						if(coordX-a>=0&&coordY+a<this.getNbColumn()) {
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
		if(this.tabUsed.get(z).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()))).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()))).get(Direction.DIAGRIGHT).equals(false)) {
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
			if(!this.getPoints()[coordX-i][coordY] || this.tabUsed.get(new Point((coordX-i)*getStep(),coordY*getStep())).get(Direction.HORIZONTAL).equals(true)) {
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
		if(this.tabUsed.get(z).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*getStep(),(coordY*getStep()))).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*getStep(),(coordY*getStep()))).get(Direction.HORIZONTAL).equals(false)) {

			if(cpt_gauche+cpt_droite==4) {

					int tempLeft=1;
					int tempRight=1;
					for(int i =tempLeft;i<=cpt_gauche;i++) {
						Point t= new Point((coordX-i)*getStep(),(coordY*getStep()));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, false, true);
					}
					for(int i =tempRight;i<=cpt_droite;i++) {
						Point t= new Point((coordX+i)*getStep(),(coordY*getStep()));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, false, true);
					}
					this.tabUsed.get(z).replace(Direction.HORIZONTAL, false, true);
					debut = new Point((coordX-cpt_gauche)*getStep(),(coordY*getStep()));
					fin = new Point((coordX+cpt_droite)*getStep(),(coordY*getStep()));
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {

			if(!this.getPoints()[coordX][coordY-i] || this.tabUsed.get(new Point(coordX*getStep(),(coordY-i)*getStep())).get(Direction.VERTICAL).equals(true)) {
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
				if(this.tabUsed.get(new Point(coordX*getStep(),(coordY-i)*getStep())).get(Direction.VERTICAL).equals(false)) {
					cpt_haut++;

					if(cpt_haut==4) {
						i = 6;



					}
				}
				
				
			}
				
		}
		if(this.tabUsed.get(z).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*getStep()),(coordY-cpt_haut)*getStep())).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*getStep()),(coordY+cpt_bas)*getStep())).get(Direction.VERTICAL).equals(false)) {
			


			if(cpt_haut+cpt_bas==4) {

				int tempHaut=1;
				int tempBas=1;
				for(int i =tempHaut;i<=cpt_haut;i++) {
					Point t= new Point((coordX)*getStep(),((coordY-i)*getStep()));
					this.tabUsed.get(t).replace(Direction.VERTICAL, false, true);
				}
				for(int i =tempBas;i<=cpt_bas;i++) {
					Point t= new Point((coordX)*getStep(),((coordY+i)*getStep()));
					this.tabUsed.get(t).replace(Direction.VERTICAL, false, true);
				}
				this.tabUsed.get(z).replace(Direction.VERTICAL, false, true);
				debut = new Point((coordX*getStep()),(coordY-cpt_haut)*getStep());
				fin = new Point((coordX*getStep()),(coordY+cpt_bas)*getStep());
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY-i] || this.tabUsed.get(new Point((coordX-i)*getStep(),(coordY-i)*getStep())).get(Direction.DIAGLEFT).equals(true)) {
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
		if(this.tabUsed.get(z).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()))).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()))).get(Direction.DIAGLEFT).equals(false)) {

			if(cpt_gauche+cpt_droite==4) {
				int tempLeft=1;
				int tempRight=1;
				for(int i =tempLeft;i<=cpt_gauche;i++) {
					Point t= new Point((coordX-i)*getStep(),((coordY-i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGLEFT, false, true);
				}
				for(int i =tempRight;i<=cpt_droite;i++) {
					Point t= new Point((coordX+i)*getStep(),((coordY+i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGLEFT, false, true);
				}
				this.tabUsed.get(z).replace(Direction.DIAGLEFT, false, true);
				debut = new Point((coordX-cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()));
				fin = new Point((coordX+cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()));
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
				

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
			if(!this.getPoints()[coordX+i][coordY-i] || this.tabUsed.get(new Point((coordX+i)*getStep(),(coordY-i)*getStep())).get(Direction.DIAGRIGHT).equals(true)) {
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
		if(this.tabUsed.get(z).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()))).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()))).get(Direction.DIAGRIGHT).equals(false)) {

			if(cpt_gauche+cpt_droite==4) {
				int tempLeft=1;
				int tempRight=1;
				for(int i =tempLeft;i<=cpt_gauche;i++) {
					Point t= new Point((coordX+i)*getStep(),((coordY-i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGRIGHT, false, true);
				}
				for(int i =tempRight;i<=cpt_droite;i++) {
					Point t= new Point((coordX-i)*getStep(),((coordY+i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGRIGHT, false, true);
				}
				this.tabUsed.get(z).replace(Direction.DIAGRIGHT, false, true);
				debut = new Point((coordX+cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()));
				fin = new Point((coordX-cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()));
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX+i][coordY] || this.tabUsed.get(new Point((coordX+i)*getStep(),coordY*getStep())).get(Direction.HORIZONTAL).equals(true)) {
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
		if(this.tabUsed.get(z).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*getStep(),(coordY*getStep()))).get(Direction.HORIZONTAL).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*getStep(),(coordY*getStep()))).get(Direction.HORIZONTAL).equals(false)) {

			if(cpt_gauche+cpt_droite==4) {
					int tempLeft=1;
					int tempRight=1;
					for(int i =tempLeft;i<=cpt_gauche;i++) {
						Point t= new Point((coordX-i)*getStep(),(coordY*getStep()));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, false, true);
					}
					for(int i =tempRight;i<=cpt_droite;i++) {
						Point t= new Point((coordX+i)*getStep(),(coordY*getStep()));
						this.tabUsed.get(t).replace(Direction.HORIZONTAL, false, true);
					}
					this.tabUsed.get(z).replace(Direction.HORIZONTAL, false, true);
					debut = new Point((coordX+cpt_droite)*getStep(),(coordY*getStep()));
					fin = new Point((coordX-cpt_gauche)*getStep(),(coordY*getStep()));
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX][coordY+i] || this.tabUsed.get(new Point(coordX*getStep(),(coordY+i)*getStep())).get(Direction.VERTICAL).equals(true)) {
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
				if(this.tabUsed.get(new Point(coordX*getStep(),(coordY+i)*getStep())).get(Direction.VERTICAL).equals(false)) {
					cpt_bas++;
					if(cpt_bas==4) {
						i = 6;
						
					}
				}
				
				
			}
				
		}
		if(this.tabUsed.get(z).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*getStep()),(coordY+cpt_bas)*getStep())).get(Direction.VERTICAL).equals(false) && this.tabUsed.get(new Point((coordX*getStep()),(coordY-cpt_haut)*getStep())).get(Direction.VERTICAL).equals(false)) {
			

			if(cpt_haut+cpt_bas==4) {

				int tempHaut=1;
				int tempBas=1;
				for(int i =tempHaut;i<=cpt_haut;i++) {
					Point t= new Point((coordX)*getStep(),((coordY-i)*getStep()));
					this.tabUsed.get(t).replace(Direction.VERTICAL, false, true);
				}
				for(int i =tempBas;i<=cpt_bas;i++) {
					Point t= new Point((coordX)*getStep(),((coordY+i)*getStep()));
					this.tabUsed.get(t).replace(Direction.VERTICAL, false, true);
				}
				this.tabUsed.get(z).replace(Direction.VERTICAL, false, true);
				debut = new Point((coordX*getStep()),(coordY+cpt_bas)*getStep());
				fin = new Point((coordX*getStep()),(coordY-cpt_haut)*getStep());
				line.setP1(debut);
				line.setP5(fin);
				getTabLine().add(line);
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
			if(!this.getPoints()[coordX+i][coordY+i] || this.tabUsed.get(new Point((coordX+i)*getStep(),(coordY+i)*getStep())).get(Direction.DIAGLEFT).equals(true)) {
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
		if(this.tabUsed.get(z).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()))).get(Direction.DIAGLEFT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()))).get(Direction.DIAGLEFT).equals(false)) {
			if(cpt_gauche+cpt_droite==4) {
				int tempLeft=1;
				int tempRight=1;
				for(int i =tempLeft;i<=cpt_gauche;i++) {
					Point t= new Point((coordX-i)*getStep(),((coordY-i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGLEFT, false, true);
				}
				for(int i =tempRight;i<=cpt_droite;i++) {
					Point t= new Point((coordX+i)*getStep(),((coordY+i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGLEFT, false, true);
				}
				this.tabUsed.get(z).replace(Direction.DIAGLEFT, false, true);
				debut = new Point((coordX+cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()));
				fin = new Point((coordX-cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()));
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
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY+i] || this.tabUsed.get(new Point((coordX-i)*getStep(),(coordY+i)*getStep())).get(Direction.DIAGRIGHT).equals(true)) {
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
		if(this.tabUsed.get(z).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX-cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()))).get(Direction.DIAGRIGHT).equals(false) && this.tabUsed.get(new Point((coordX+cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()))).get(Direction.DIAGRIGHT).equals(false)) {
			if(cpt_gauche+cpt_droite==4) {
				int tempLeft=1;
				int tempRight=1;
				for(int i =tempLeft;i<=cpt_gauche;i++) {
					Point t= new Point((coordX+i)*getStep(),((coordY-i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGRIGHT, false, true);
				}
				for(int i =tempRight;i<=cpt_droite;i++) {
					Point t= new Point((coordX-i)*getStep(),((coordY+i)*getStep()));
					this.tabUsed.get(t).replace(Direction.DIAGRIGHT, false, true);
				}
				this.tabUsed.get(z).replace(Direction.DIAGRIGHT, false, true);
				debut = new Point((coordX-cpt_droite)*getStep(),((coordY+cpt_droite)*getStep()));
				fin = new Point((coordX+cpt_gauche)*getStep(),((coordY-cpt_gauche)*getStep()));
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
	 *  (Point)
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
