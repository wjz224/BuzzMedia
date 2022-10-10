package edu.lehigh.cse216.yap224.admin;


import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;


public class DatabaseTest extends TestCase{

    String db_url = "postgres://izfmydakonpxcn:eebbe11f587fed810de5c8427c555808043c8e0d1a408960b32b6c13d55e3942@ec2-3-209-39-2.compute-1.amazonaws.com:5432/ddsnjmei05mg8l";
    Database db = Database.getDatabase(db_url);

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest( String testName )
    {
        super(testName );
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
        Database db = Database.getDatabase(db_url);
        assertTrue( db != null );
    }

    public void testDatabaseInsertDelete()
    {
        int testInt = db.insertRow("hello", "world", 1);
        int testInt2 = db.deleteRow(testInt);
        assertTrue(testInt != -1  && testInt2 != -1);
    }

    public void testDatabaseSelectAll()
    {   
        ArrayList<Database.RowData> testArray = db.selectAll();
        assertTrue( testArray != null );
    }

    
    
}
