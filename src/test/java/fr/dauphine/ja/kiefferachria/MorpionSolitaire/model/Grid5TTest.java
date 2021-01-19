package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;

import fr.dauphine.ja.kiefferachria.MorpionSolitaire.AppTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Grid5TTest extends TestCase {
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Grid5TTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( Grid5TTest.class );
    }

    /**
     * Test the initialization of the cross. Check that we have 36 point at true (visible)
     */
    public void testInitCross()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
    	int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				
				
			}
		}
		assertTrue(count==36);
    }
    
    /**
     * Test the update of a point that is playable
     */
    public void testUpdateGridGood()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
    	grid.updateGrid(new Point(240,400), "player");
		assertTrue(grid.getPointUser().size()==1);
		int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
				//try {
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				//}catch(ArrayIndexOutOfBoundsException e) {
				//	System.out.println();
				//}
				
			}
		}
		assertTrue(count==37);
    }
    
    /**
     * Test the update of a point not playable
     */
    public void testUpdateGridBad()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
    	grid.updateGrid(new Point(0,0), "player");
		assertTrue(grid.getPointUser().size()==0);
		int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
				//try {
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				//}catch(ArrayIndexOutOfBoundsException e) {
				//	System.out.println();
				//}
				
			}
		}
		assertTrue(count==36);
    }
    
    /**
     * Test of check horizontal. Test a point possible and another one impossible
     */
    public void testcheckHorizontal()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
		assertTrue(grid.checkPossibleMoveHorizontale(new Point(240,400)));
		assertFalse(grid.checkPossibleMoveHorizontale(new Point(0,0)));

    }
    /**
     * Test of check vertical. Test a point possible and another one impossible
     */
    public void testcheckVertical()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
		assertTrue(grid.checkPossibleMoveVerticale(new Point(400,240)));
		assertFalse(grid.checkPossibleMoveVerticale(new Point(0,0)));

    }
    
    /**
     * Test of check diagonal left. Test a point possible and another one impossible
     */
    public void testcheckLeft()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
		assertTrue(grid.checkPossibleMoveDiagonaleLeft(new Point(560,360)));
		assertFalse(grid.checkPossibleMoveDiagonaleLeft(new Point(0,0)));

    }
    
    /**
     * Test of check diagonal right. Test a point possible and another one impossible
     */
    public void testcheckRight()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
		assertTrue(grid.checkPossibleMoveDiagonaleRight(new Point(360,360)));
		assertFalse(grid.checkPossibleMoveDiagonaleRight(new Point(0,0)));

    }
    /**
     * Test that all the point used in check are availble as point in the list
     */
    public void testpointAvailable()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
    	grid.pointAvailable();
		assertTrue(grid.getPotentialMove().contains(new Point(360,360)));
		assertTrue(grid.getPotentialMove().contains(new Point(560,360)));
		assertTrue(grid.getPotentialMove().contains(new Point(400,240)));
		assertTrue(grid.getPotentialMove().contains(new Point(240,400)));

    }
}
