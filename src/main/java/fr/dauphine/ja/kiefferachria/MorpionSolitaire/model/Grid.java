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
    
    public Grid(int h,int w, int step){
    	this.nbLine=(int)w/step;
    	this.nbColumn=(int)h/step;
    	this.step=step;
    	this.height=h;
    	this.width=w;
    	this.points = new boolean [nbLine][nbColumn];
    	this.tabCoordonnee=new ArrayList<Point>();
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
    
    
    		
    		
    
    
}
