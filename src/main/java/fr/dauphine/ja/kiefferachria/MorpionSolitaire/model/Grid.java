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
    
    
    		
    		
    
    
}
