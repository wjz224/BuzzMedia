package edu.lehigh.cse216.yap224.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatabaseTest extends TestCase{
    public DatabaseTest(String testName)
    {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DatabaseTest.class);
    }
    
    public void testDatabaseConstructor() {
        Database db = Database.getDatabase("localhost:3306/mydb"); 
        
        // testing the 
            
    }
}