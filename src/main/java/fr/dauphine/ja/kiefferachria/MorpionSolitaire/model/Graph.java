package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;
import java.util.ArrayList;

public class Graph {
	private ArrayList<Point> point;
	private int m;

	public Graph(ArrayList<Integer> toplot) {
		point=new ArrayList<Point>();
		int max=-1;
		for(int i = 0;i<toplot.size();i++) {
			if(max<toplot.get(i))
				max=toplot.get(i);
		}
		m=max;
		max = max*2;
		for(int i = 0;i<toplot.size();i++) {
			point.add(new Point((i*20)+5,max-toplot.get(i)));
		}
	}

	public ArrayList<Point> getPoint() {
		return point;
	}

	public void setPoint(ArrayList<Point> point) {
		this.point = point;
	}
	
	public int max() {
		
		return m;
	}
	
	
}
