package edu.lehigh.cse216.yap224.backend;

//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;

import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;
/**
 * Unit test for simple App. Hello World
 */
public class AppTest{
    @Test
    void getTest(){
        given().get("https://thebuzzomega.herokuapp.com/messages").then().statusCode(200);
    }


}
