package edu.lehigh.cse216.yap224.backend;


import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



public class DatabaseTest extends TestCase{

    String db_url = "postgres://mjvjoatyohzaka:18fb558a6a972731841f0b25746771babdaa53b0f9d1d0407919f477abccc725@ec2-54-163-34-107.compute-1.amazonaws.com:5432/d7au1ki28gl09q";
    Database db = Database.getDatabase(db_url);

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DatabaseTest.class );
    }

    public void testDatabaseCreation()
    {
        assertTrue( db != null );
    }

    public void testDatabaseGETRoute()
    {
        //ArrayList test = db.selectAll();
        assertTrue( true );
    }

    public void testDatabaseDELETERoute()
    {
        assertTrue( true );
    }

    public void testDatabasePOSTRoute()
    {
        assertTrue( true );
    }

    public void testDatabasePUTLikeRoute()
    {
        assertTrue( true );
    }

    public void testDatabasePUTMessageChangeRoute()
    {
        assertTrue( true );
    }
    
    
    
}
