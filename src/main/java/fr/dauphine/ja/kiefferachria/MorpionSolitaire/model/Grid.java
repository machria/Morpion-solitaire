package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


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
    
    public Grid(int h,int w, int step){
    	this.nbLine=(int)w/step;
    	this.nbColumn=(int)h/step;
    	this.step=step;
    	this.height=h;
    	this.width=w;
    	this.points = new boolean [nbLine][nbColumn];
    	this.initiatePoint();
    	this.tabCoordonnee=new ArrayList<Point>();
    	this.center=(int) nbLine/2;//Same height and width
    	this.tabCross=new ArrayList<Point>();
    	this.tabLine=new ArrayList<Line>();
    	
    	this.generateCross();
    }
    
    public ArrayList<Line> getTabLine() {
		return tabLine;
	}

	public void setTabLine(ArrayList<Line> tabLine) {
		this.tabLine = tabLine;
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
    
    private void initiatePoint() {
		// TODO Auto-generated method stub
		for(int i = 0;i<nbLine;i++) {
			for(int j = 0;j<nbColumn;j++) {
				this.points[i][j]=false;
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

	public void updateGrid(Point z) {
		// TODO Auto-generated method stub
		if(!this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]) {
			this.getPoints()[((int)z.getX()/this.getStep())][(int)z.getY()/this.getStep()]=true;
			
		}else {
			System.out.println("existe deja");
		}
	}
	
	public void checkPossibleMoveHorizontale(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY]) {
				for(int a = 1;a<=4-cpt_gauche;a++) {
					//System.out.println(a);
					if(!this.getPoints()[coordX+a][coordY]) {
						System.out.println("Pas de line possible sur l'horizontale");
						return;
					}
					if(cpt_gauche+cpt_droite==4) {
						a=6;
						i=6;
					}
					
					else{
						cpt_droite++;
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
		if(cpt_gauche+cpt_droite==4) {
			debut = new Point((coordX-cpt_gauche)*this.step,(coordY*this.step));
			fin = new Point((coordX+cpt_droite)*this.step,(coordY*this.step));
			line.setP1(debut);
			line.setP5(fin);
			this.tabLine.add(line);
			System.out.println("Ajout");
		}
		System.out.println(cpt_gauche);
		System.out.println(cpt_droite);
		
	}
	
	public void checkPossibleMoveVerticale(Point z) {
		int cpt_haut = 0;
		int cpt_bas = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX][coordY-i]) {
				for(int a = 1;a<=4-cpt_haut;a++) {
					//System.out.println(a);
					if(!this.getPoints()[coordX][coordY+a]) {
						System.out.println("Pas de line possible sur l'horizontale");
						return;
					}
					if(cpt_haut+cpt_bas==4) {
						a=6;
						i=6;
					}
					
					else{
						cpt_bas++;
					}
				}
			}
			else {
				cpt_haut++;
				if(cpt_haut==4) {
					i = 6;
				}
				
			}
				
			}
		if(cpt_haut+cpt_bas==4) {
			debut = new Point((coordX*this.step),(coordY-cpt_haut)*this.step);
			fin = new Point((coordX*this.step),(coordY+cpt_bas)*this.step);
			line.setP1(debut);
			line.setP5(fin);
			this.tabLine.add(line);
			System.out.println("Ajout");
		}
		System.out.println(cpt_haut);
		System.out.println(cpt_bas);
		
	}
	
	public void checkPossibleMoveDiagonaleLeft(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX-i][coordY-i]) {
				for(int a = 1;a<=4-cpt_gauche;a++) {
					//System.out.println(a);
					if(!this.getPoints()[coordX+a][coordY+a]) {
						System.out.println("Pas de line possible sur l'horizontale");
						return;
					}
					if(cpt_gauche+cpt_droite==4) {
						a=6;
						i=6;
					}
					
					else{
						cpt_droite++;
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
		if(cpt_gauche+cpt_droite==4) {
			debut = new Point((coordX-cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step));
			fin = new Point((coordX+cpt_droite)*this.step,((coordY+cpt_droite)*this.step));
			line.setP1(debut);
			line.setP5(fin);
			this.tabLine.add(line);
			System.out.println("Ajout");
		}
		System.out.println(cpt_gauche);
		System.out.println(cpt_droite);
		
	}
	
	public void checkPossibleMoveDiagonaleRight(Point z) {
		int cpt_gauche = 0;
		int cpt_droite = 0;
		Line line = new Line(null,null,null,null,null);
		Point fin = new Point();
		Point debut = new Point();
		int coordX=((int)z.getX()/this.getStep());
		int coordY=((int)z.getY()/this.getStep());
		for(int i =1; i<=4;i++) {
			if(!this.getPoints()[coordX+i][coordY-i]) {
				for(int a = 1;a<=4-cpt_gauche;a++) {
					//System.out.println(a);
					if(!this.getPoints()[coordX-a][coordY+a]) {
						System.out.println("Pas de line possible sur l'horizontale");
						return;
					}
					if(cpt_gauche+cpt_droite==4) {
						a=6;
						i=6;
					}
					
					else{
						cpt_droite++;
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
		if(cpt_gauche+cpt_droite==4) {
			debut = new Point((coordX+cpt_gauche)*this.step,((coordY-cpt_gauche)*this.step));
			fin = new Point((coordX-cpt_droite)*this.step,((coordY+cpt_droite)*this.step));
			line.setP1(debut);
			line.setP5(fin);
			this.tabLine.add(line);
			System.out.println("Ajout");
		}
		System.out.println(cpt_gauche);
		System.out.println(cpt_droite);
		
	}
    
    
    		
    		
    
    
}
