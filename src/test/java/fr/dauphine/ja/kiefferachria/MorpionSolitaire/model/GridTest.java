package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GridTest extends TestCase {
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GridTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GridTest.class );
    }

    /**
     * Test the initialization of the cross. Check that we have 36 point at true (visible)
     */
    public void testInitCross()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
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
    	final Grid5D grid = new Grid5D(880,880,40);
    	grid.updateGrid(new Point(240,400), "player");
		assertTrue(grid.getPointUser().size()==1);
		int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				
				
			}
		}
		assertTrue(count==37);
    }
    
    /**
     * Test the update of a point not playable
     */
    public void testUpdateGridBad()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
    	grid.updateGrid(new Point(0,0), "player");
		assertTrue(grid.getPointUser().size()==0);
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
     * Test of check horizontal. Test a point possible and another one impossible
     */
    public void testcheckHorizontal()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
		assertTrue(grid.checkPossibleMoveHorizontal(new Point(240,400)));
		assertFalse(grid.checkPossibleMoveHorizontal(new Point(0,0)));

    }
    
    /**
     * Test of check vertical. Test a point possible and another one impossible
     */
    public void testcheckVertical()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
		assertTrue(grid.checkPossibleMoveVertical(new Point(400,240)));
		assertFalse(grid.checkPossibleMoveVertical(new Point(0,0)));

    }
    
    /**
     * Test of check diagonal left. Test a point possible and another one impossible
     */
    public void testcheckLeft()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
		assertTrue(grid.checkPossibleMoveDiagonalLeft(new Point(560,360)));
		assertFalse(grid.checkPossibleMoveDiagonalLeft(new Point(0,0)));

    }
    
    /**
     * Test of check diagonal right. Test a point possible and another one impossible
     */
    public void testcheckRight()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
		assertTrue(grid.checkPossibleMoveDiagonalRight(new Point(360,360)));
		assertFalse(grid.checkPossibleMoveDiagonalRight(new Point(0,0)));

    }
    
    /**
     * Test that all the point used in check are availble as point in the list
     */
    public void testpointAvailable()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
    	grid.pointAvailable();
		assertTrue(grid.getPotentialMove().contains(new Point(360,360)));
		assertTrue(grid.getPotentialMove().contains(new Point(560,360)));
		assertTrue(grid.getPotentialMove().contains(new Point(400,240)));
		assertTrue(grid.getPotentialMove().contains(new Point(240,400)));

    }
    
    /**
     * Test our algorithm which generate solution ten times and print the best score
     */
    public void testNMCS()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
    	for(int i=0;i<1;i++) {
        	grid.pointAvailable();
    		while(!grid.getPotentialMove().isEmpty()) {

    			grid.NMCS();
    			grid.pointAvailable();
                
    		}
    		grid.reset();
    	}
    	int max=0;
    	for(int i=0;i<grid.getScoreHistory().size();i++) {
    		if(max<grid.getScoreHistory().get(i))
    			max=grid.getScoreHistory().get(i);
    	}
    	int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				
				
			}
		}
		assertTrue(count==37);

    }
    
    /**
     * Test our algorithm which generate solution ten times and print the best score
     */
    public void testNMCS2()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
    	for(int i=0;i<1;i++) {
        	grid.pointAvailable();
    		while(!grid.getPotentialMove().isEmpty()) {

    			grid.NMCS2();
    			grid.pointAvailable();
                
    		}
    		grid.reset();
    	}
    	int max=0;
    	for(int i=0;i<grid.getScoreHistory().size();i++) {
    		if(max<grid.getScoreHistory().get(i).intValue())
    			max=grid.getScoreHistory().get(i).intValue();
    	}
    	int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				
				
			}
		}
		assertTrue(count==37);

    }
    
    /**
     * Test our algorithm which generate solution ten times and print the best score
     */
    public void testNMCS3()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
    	for(int i=0;i<1;i++) {
        	grid.pointAvailable();
    		while(!grid.getPotentialMove().isEmpty()) {

    			grid.NMCS3();
    			grid.pointAvailable();
                
    		}
    		grid.reset();
    	}
    	int max=0;
    	for(int i=0;i<grid.getScoreHistory().size();i++) {
    		if(max<grid.getScoreHistory().get(i).intValue())
    			max=grid.getScoreHistory().get(i).intValue();
    	}
    	int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				
				
			}
		}
		assertTrue(count==37);

    }
    
    /**
     * Test algorithm who take a random point which generate solution ten times and print the best score
     */
    public void testIA()
    {
    	final Grid5D grid = new Grid5D(880,880,40);
    	for(int i=0;i<1;i++) {
        	grid.pointAvailable();
    		while(!grid.getPotentialMove().isEmpty()) {

    			grid.updateIA();
    			grid.pointAvailable();
                
    		}
    		grid.reset();
    	}
    	int max=0;
    	for(int i=0;i<grid.getScoreHistory().size();i++) {
    		if(max<grid.getScoreHistory().get(i).intValue())
    			max=grid.getScoreHistory().get(i).intValue();
    	}
    	System.out.println(max);
    	int count=0;
		for(int i = 0;i<grid.getNbLine();i++) {
			for(int j = 0;j<grid.getNbColumn();j++) {
				Point z = new Point(i*grid.getStep(),j*grid.getStep());
					if(grid.getPoints()[((int)z.getX()/grid.getStep())][(int)z.getY()/grid.getStep()] ) {
						count++;
					}
				
				
			}
		}
		assertTrue(count==37);
    }
    
}
