package edu.lehigh.cse216.yap224.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Unit test for simple App. Hello World
 */


import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
public class AppTest extends TestCase{
    
    /**
     *  Create the test case
     * @param testName name of the test case
     */

    public AppTest( String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite( AppTest.class );
    }

   
    public void testApp()
    {
        assertTrue( true );
    }
  /** 
    public void testGet(){
        RestAssured.baseURI = "https://thebuzzomega.herokuapp.com/";
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        Response response = request.get("/posts");
        assertEquals(response.getStatusCode(), 200);
    }
    
    public void testPost(){
        RestAssured.baseURI = "https://thebuzzomega.herokuapp.com/";
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        Response response = request.body("{\"mTitle\": \"unitTestPost\", \"mText\": \"unitTestPostMessage\"}").post("/posts");
        assertEquals(response.getStatusCode(), 404);
    }
    */
    /** 
    public void testGet(){
        RestAssured.baseURI = "https://thebuzzomega.herokuapp.com/";
        RequestSpecification request = RestAssured.given();

        request.header("COntent-Type", "application/json");
        Response response = request.get("/posts");
        assertEquals(response.getStatusCode(), 200);
    }



    public void testPost(){
        RestAssured.baseURI = "https://thebuzzomega.herokuapp.com/";
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        Response response = request.body("{\"mTitle\": \"unitTestPost\", \"mMessage\": \"unitTestPostMessage\"}").post("/posts");
        assertEquals(response.getStatusCode(), 200);
    }

    public void testLike(){
        RestAssured.baseURI = "https://thebuzzomega.herokuapp.com/";
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        Response response = request.put("/messages/3/likes");
        assertEquals(response.getStatusCode(), 200);
    }

    public void testDisike(){
        RestAssured.baseURI = "https://thebuzzomega.herokuapp.com/";
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        Response response = request.put("/messages/3/dislikes");
        assertEquals(response.getStatusCode(), 200);
    }
    */


}
