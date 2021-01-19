package fr.dauphine.ja.kiefferachria.MorpionSolitaire.model;

import java.awt.Point;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ScoreTest extends TestCase {
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ScoreTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ScoreTest.class );
    }

    /**
     * Test the incrementation of the score for player on 5D
     */
    public void testplayerIncrementTest()
    {
    	final Grid grid = new Grid(880,880,40);
    	assertTrue(grid.getScore().getScore_joueur()==0);
    	grid.updateGrid(new Point(240,400), "player");
    	assertTrue(grid.getScore().getScore_joueur()==1);
    }
    /**
     * Test the not incrementation of the score for player who try a point impossible according to rule on 5D
     */
    public void testplayernoIncrement()
    {
    	final Grid grid = new Grid(880,880,40);
    	assertTrue(grid.getScore().getScore_joueur()==0);
    	grid.updateGrid(new Point(0,0), "player");
    	assertTrue(grid.getScore().getScore_joueur()==0);
    }
    /**
     * Test the incrementation of the score for player on 5D
     */
    public void testcomputerIncrement()
    {
    	final Grid grid = new Grid(880,880,40);
    	assertTrue(grid.getScore().getScore_computeur()==0);
    	grid.updateGrid(new Point(240,400), "player");
    	assertTrue(grid.getScore().getScore_computeur()==0);
    	grid.pointAvailable();
    	grid.updateIANaive();
    	assertTrue(grid.getScore().getScore_computeur()==1);
    	grid.pointAvailable();
    	grid.NMCS();
    	assertTrue(grid.getScore().getScore_computeur()==2);

    }
    /**
     * Test the incrementation of the score for player on 5D
     */
    public void testplayerIncrement5T()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
    	assertTrue(grid.getScore().getScore_joueur()==0);
    	grid.updateGrid(new Point(240,400), "player");
    	assertTrue(grid.getScore().getScore_joueur()==1);
    }
    /**
     * Test the not incrementation of the score for player who try a point impossible according to rule on 5D
     */
    public void testplayernoIncrement5T()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
    	assertTrue(grid.getScore().getScore_joueur()==0);
    	grid.updateGrid(new Point(0,0), "player");
    	assertTrue(grid.getScore().getScore_joueur()==0);
    }
    /**
     * Test the incrementation of the score for player on 5D
     */
    public void testcomputerIncrement5T()
    {
    	final Grid5T grid = new Grid5T(880,880,40);
    	assertTrue(grid.getScore().getScore_computeur()==0);
    	grid.updateGrid(new Point(240,400), "player");
    	assertTrue(grid.getScore().getScore_computeur()==0);
    	grid.pointAvailable();
    	grid.updateIA();
    	assertTrue(grid.getScore().getScore_computeur()==1);
    	grid.pointAvailable();
    	grid.NMCS();
    	assertTrue(grid.getScore().getScore_computeur()==2);
    }
}
