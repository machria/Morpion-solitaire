package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;


public class Grid {

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
    private HashMap<Point, HashMap<Direction, Boolean>> tabUsed;
    private ArrayList<Point> potentialMoveNext;
    private ArrayList<Point> potentialMove;
    private ArrayList<Point> pointUser;
    private Score score;
    
    
	public Grid(int h,int w, int step){
    	
    	this.step=step;
    	this.height=h;
    	this.width=w;
    	this.reset();

    }
	
//GETTERS ET SETTERS
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
	public ArrayList<Point> getPointUser() {
		return pointUser;
	}
	public void setPointUser(ArrayList<Point> pointUser) {
		this.pointUser = pointUser;
	}

//Mise en place du jeu et gestion du jeu
    private void initiatePoint() {
		// TODO Auto-generated method stub
    	HashMap<Direction,Boolean> dir = new HashMap<Direction, Boolean>();
    	dir.put(Direction.VERTICAL,false);
    	dir.put(Direction.HORIZONTAL,false);
    	dir.put(Direction.DIAGLEFT,false);
    	dir.put(Direction.DIAGRIGHT,false);
		for(int i = 0;i<nbLine;i++) {
			for(int j = 0;j<nbColumn;j++) {
				this.points[i][j]=false;
				this.tabUsed.put(new Point(i*this.step,j*this.step ),(HashMap<Direction, Boolean>) dir.clone());
			}
		}
	}

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
    
	public Point firstPoint() {
    	return new Point((this.center-1)*this.step,(this.center-1)*this.step);
    }

	public void reset() {
		// TODO Auto-generated method stub
    	this.nbLine=(int)width/step;
    	this.nbColumn=(int)height/step;
    	this.potentialMove = new ArrayList<Point>();
    	this.potentialMoveNext = new ArrayList<Point>();

    	this.points = new boolean [nbLine][nbColumn];
    	this.tabCoordonnee=new ArrayList<Point>();
    	this.center=(int) nbLine/2;//Same height and width
    	this.tabCross=new ArrayList<Point>();
    	this.tabLine=new ArrayList<Line>();
    	this.tabUsed = new HashMap<Point, HashMap<Direction, Boolean>>();
    	this.initiatePoint();
    	this.generateCross();
    	this.catchCoordonnee();
    	this.pointUser= new ArrayList<Point>();
    	this.score = new Score();
	}

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
	
	
//Fonctions UPDATE
	public void updateGrid(Point z,String s) {
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
			System.out.println(this.tabUsed.get(z));
			if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveDiagonaleRight(z)) {
				this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;	
				this.drawMoveDiagonaleRight(z);
				this.pointUser.add(z);
				this.incrementeScore(s);
			}
			if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveDiagonaleLeft(z)) {
				this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;
				this.drawMoveDiagonaleLeft(z);
				this.pointUser.add(z);
				this.incrementeScore(s);
			}
			if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveHorizontale(z)) {
				this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;
				this.drawMoveHorizontale(z);
				this.pointUser.add(z);
				this.incrementeScore(s);
			}
			if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && checkPossibleMoveVerticale(z)) {
				this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;
				this.drawMoveVerticale(z);
				this.pointUser.add(z);
				this.incrementeScore(s);
			}
			else {
				System.out.println("existe deja");
			}
		
		
	}
	
	public void updateIANaive() {
		Collections.shuffle(this.potentialMove);
		if(!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(0);
			this.updateGrid(x,"IA");
		}else {
			System.out.println("No solution");
		}
		
	}
	
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

// Fonctions qui permettent de capter les points possibles et de capter aussi les points possibles par rapport à un point
	public void pointAvailable() {
		this.potentialMove.clear();
		for(int i = 0;i<nbLine;i++) {
			for(int j = 0;j<nbColumn;j++) {
				Point z = new Point(i*this.step,j*this.step);
				//try {
					if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && (checkPossibleMoveDiagonaleRight(z)|| checkPossibleMoveDiagonaleLeft(z)|| checkPossibleMoveHorizontale(z)|| checkPossibleMoveVerticale(z))) {
						this.potentialMove.add(z);
					}
				//}catch(ArrayIndexOutOfBoundsException e) {
				//	System.out.println();
				//}
				
			}
		}
	}
	
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
				//try {
					if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()] && (checkPossibleMoveDiagonaleRight(z)|| checkPossibleMoveDiagonaleLeft(z)|| checkPossibleMoveHorizontale(z)|| checkPossibleMoveVerticale(z))) {
						this.potentialMoveNext.add(z);
					}
				//}catch(ArrayIndexOutOfBoundsException e) {
				//	System.out.println();
				//}
				
			}
			
		}
		if (test) {
			this.getPoints()[((int)b.getX()/this.getStep())][(int)b.getY()/this.getStep()]=false;
		}
		return this.potentialMoveNext.size();

	}
	
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
		
		if(!this.potentialMove.isEmpty()) {
			Point x = this.potentialMove.get(indice);
			this.updateGrid(x,"IA");
		}else {
			System.out.println("No solution");
		}
		
	}
	
	
//Fonctions de vérification
	public boolean checkPossibleMoveHorizontale(Point z) {
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
	
	public boolean checkPossibleMoveVerticale(Point z) {
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
	
	public boolean checkPossibleMoveDiagonaleLeft(Point z) {
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
	
	public boolean checkPossibleMoveDiagonaleRight(Point z) {
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
    
//Fonctions qui permettent de dessiner les lignes lorsqu'elle sont correctes	
	public void drawMoveHorizontale(Point z) {
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
	
	public void drawMoveVerticale(Point z) {
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
	
	public void drawMoveDiagonaleLeft(Point z) {
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
	
	public void drawMoveDiagonaleRight(Point z) {
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
					//System.out.println(a);
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
	   
}
