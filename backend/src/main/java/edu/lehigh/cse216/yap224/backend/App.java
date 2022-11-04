package edu.lehigh.cse216.yap224.backend;

/**
 * Importing Spark package
 */
import spark.Spark;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.get;
import static spark.Spark.post;
/**
 * Importing google package
 */
import com.google.gson.*;

import edu.lehigh.cse216.yap224.backend.Database.CommentRowData;

/***
 * Import java map
 */
import java.io.Console;
import java.util.Map;


/**
 * Import java error handling, scanner, and security
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.*;
/***
 * Importing google oauth2 package
 */
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

/**
 * Importing firebase package
 */
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
/**
 * For now, our app creates an HTTP server that can only get and add data.
 */


public class App {
    /***
     * Inner session class for the session object.
     */
   
    static Map<Integer, Integer> users = new HashMap<>();
   

    public static void main(String[] args) {
        
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String db_url = env.get("DATABASE_URL");
        users.put("yap224@lehigh.edu".hashCode(),1);
        users.put("wjz224@lehigh.edu".hashCode(),2);    
        /*String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        System.out.println("intesiaad");*/
       
        
        Database db = Database.getDatabase(db_url);
        
        if (db == null)
            return;
        
        db.createTable();    
        
        db_url.concat("?sslmode=require");

        // Get a fully-configured connection to the database, or exit
        // immediately
        
        
                
        /**
        * Replace this with the client ID you got from the Google APIs console.
        */
        final String CLIENT_ID = "33869758256-rm63jguhi2icfvpv07ccs6m0dacnnuhj.apps.googleusercontent.com";
        /**
         * Replace this with the client secret you got from the Google APIs console.
         */
        final String CLIENT_SECRET = "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";
        /**
         * Optionally replace this with your application's name.
         */
        final String APPLICATION_NAME = "TheBuzz";

        /**
         * Default HTTP transport to use to make HTTP requests.
         */
         final HttpTransport transport = new NetHttpTransport();
        /**
         * Default JSON factory to use to deserialize JSON.
         */
        final JacksonFactory jsonFactory = new JacksonFactory();
        // gson provides us with a way to turn JSON into objects, and objects
        // into JSON.
        //
        // NB: it must be final, so that it can be accessed from our lambdas
        //
        // NB: Gson is thread-safe. See
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
        final Gson gson = new Gson();

        // db holds all of the data that has been provided via HTTP
        // requests
        //

        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", 4567));

        // NB: every time we shut down the server, we will lose all data, and
        // every time we start the server, we'll have an empty db,
        // with IDs starting over from 0.
        // final db db = new db();
        // Set up the location for serving static files
        // Spark.staticFileLocation("/web");
        // Set up the location for serving static files. If the STATIC_LOCATION
        // environment variable is set, we will serve from it. Otherwise, serve
        // from "/web"
        
        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        String cors_enabled = env.get("ENABLE_CORS");

        if ("TRUE".equalsIgnoreCase(cors_enabled)) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }

        
        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });
        
        Spark.post("/verifymobile/:id_token", (request, response) -> {
            String idToken = (String) request.params("id_token");
            // fix decoded token
            String gender = "N/A";
            String sexualOrientation = "N/A";
            String note = "N/A";

            response.status(200);
            if (true) { // should be decodedToken!+ null
                // Get profile information from payload
                //String email = decodedToken.getEmail(); 
                //String userName = decodedToken.getName();
                String email = "yap224@lehigh.edu";
                String userName = "Yash";
                String profile = "none";
        
                // create sessionKey by hashing the user's email since each user email is unique.   
                int sessionKey = (int) email.hashCode();
                
                // if sessionKey doesn't already exist than add sessionKey and userId into local hashmap and user info into database
                if(!users.containsKey(sessionKey)){
                    // get user_id after inserting user into database
                    int user_id = db.insertUser(userName, email, sexualOrientation, gender, note, profile);
                    // put session key and user_id into hashtable
                    users.put(sessionKey, user_id);
                }
                
                return gson.toJson(new StructuredResponse("ok"," valid token", sessionKey));
            } else {
                return gson.toJson(new StructuredResponse("error"," invalid token", null));
            }
        });
        
        // POST route that verifys the access token and returns a sessionid
        Spark.post("/verify/:id_token", (request,response) -> {
            response.type("application/json");
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

            // (Receive idTokenString by HTTPS POST)
            String idTokenString = (String) request.params("id_token");
            System.out.println("idtoken: " + idTokenString);
            String gender = "N/A";
            String sexualOrientation = "N/A";
            String note = "N/A";
            response.status(200);
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                System.out.println("id token verified");
                // Get profile information from payload
                Payload payload = idToken.getPayload();
                String userName = (String) payload.get("name");
                String email = payload.getEmail();
                String profile = (String) payload.get("picture");
        
                // create sessionKey by hashing the user's email since each user email is unique.   
                int sessionKey = (int) email.hashCode();
                
                // if sessionKey doesn't already exist than add sessionKey and userId into local hashmap and user info into database
                if(!users.containsKey(sessionKey)){
                    // get user_id after inserting user into database
                    if(db.getUserId(email) <= 0){
                        System.out.println("user id:" + db.getUserId(email));
                        int user_id = db.insertUser(userName, email, sexualOrientation, gender, note, profile);
                        // put session key and user_id into hashtable
                         users.put(sessionKey, user_id);
                    }
                    else{
                        // put session key and user_id into hashtable
                         users.put(sessionKey, db.getUserId(email));
                    }
                }
                
                return gson.toJson(new StructuredResponse("ok"," valid token", sessionKey));
            } else {
                return gson.toJson(new StructuredResponse("error"," invalid token", null));
            }
            
        });

        
        // GET route that returns all posts 
        Spark.get(":sessionKey/posts", (request, response) -> {
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            // ensure status 200 OK, with a MIME type of JSON
            // check session key
            response.status(200);
            response.type("application/json");

            // implement session key check, if it exists continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
           }
            return gson.toJson(new StructuredResponse("ok", null, db.selectAllPostLikes()));
        });
        
        // GET route that returns everything for a single row in the db.
        // The ":id" suffix in the first parameter to get() becomes
        // request.params("id"), so that we can get the requested id. If
        // ":id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error. Otherwise, we have an integer, and the only possible
        // error is that it doesn't correspond to a row with data.
        Spark.get(":sessionKey/posts/:post_id", (request, response) -> {
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            int post_id = Integer.parseInt(request.params("post_id"));
            // implement session key check, if it exists continue, if not return error.
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // implement session key check, if it exists continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
           }
            // get post data
            Database.PostRowData data = db.selectmOnePost(post_id);

            if (data == null) {
                return gson.toJson(new StructuredResponse("error", post_id + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        }); 
       
        // finished get route, have to fix everything else
        // GET route that returns everything for a single row in the db from the user datatable.
        // The ":user_id" suffix in the first parameter to get() becomes
        // request.params("user_id"), so that we can get the requested user_ID. If
        // ":user_id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error. Otherwise, we have an integer, and the only possible
        // error is that it doesn't correspond to a row with data.
        Spark.get(":sessionKey/users/:email", (request, response) -> {
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            String email = (String) request.params("email");
            System.out.println("sessionkey: " + sessionKey);
           
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // implement session key check, if it exists continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
           }
           // get user_id of the profile we want to visit based on the email 
           int user_id = -1;
           // if input has .edu than it is an email
            if(email.contains(".edu")){
                user_id = db.getUserId(email);
            }
            // if input has no edu than it is a user_id
            else{
                user_id = Integer.parseInt(email);
            }
            System.out.println("email: " + email);
            System.out.println("userid: " + user_id);
            // get the user profile data based on the user_id returned from the email
            Database.UserRowData data = db.selectmOneUser(user_id);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", user_id + " not found", null));
            } else {
                return gson.toJson(data);
            }
        }); 
        Spark.get(":sessionKey/comments/:post_id", (request, response) ->{
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            int post_id = Integer.parseInt(request.params("post_id"));
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");  
             // implement session key check, if it exists continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            return gson.toJson(db.selectAllCommentPost(post_id));
        });
        Spark.get(":sessionKey/:post_id/likes", (request, response) ->{
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            int post_id = Integer.parseInt(request.params("post_id"));
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");  
             // implement session key check, if it exists continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            return gson.toJson(new StructuredResponse("ok", null, db.findLike(post_id)));
        });
        
        // POST route for adding a new post to the post table in the db. This will read 
        // sessionKey from the body of the request and turn it into an int
        // JSON from the body of the request, turn it into a SimpleRequest
        // object, extract the title and message, insert them, and return the
        // ID of the newly created row.
        Spark.post(":sessionKey/posts", (request, response) -> {
           // get session key for the user making the post
           int sessionKey = Integer.parseInt(request.params("sessionKey"));
            // ensure status 200 OK, with a MIME type of JSON
           // NB: even on error, we return 200, but with a JSON object that
           // describes the error.
           response.status(200);
           response.type("application/json");

           // implement session key check, if it exists in the hashtable then continue, if not return error.
           if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
           }
           // NB: if gson.Json fails, Spark will reply with status 500 Internal
           // Server Error
           SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
           int user_id = users.get(sessionKey);
           // NB: createEntry checks for null title and message
           System.out.println("user_id: " + user_id);
           System.out.println("req.mTitle : " + req.mTitle);
           System.out.println("req.mMessage : " + req.mMessage);
           int post_id = db.insertPost(user_id, req.mTitle, req.mMessage);
           if (post_id <= 0) {
               return gson.toJson(new StructuredResponse("error", "error adding post", null));
           } else {
               return gson.toJson(new StructuredResponse("ok", null, post_id));
           }
       });
        Spark.post(":sessionKey/comments/:post_id", (request, response) -> {
            // get session key for the user making the post
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            int post_id = Integer.parseInt(request.params("post_id"));
                // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");

            // implement session key check, if it exists in the hashtable then continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // get user_id from hash table
            int user_id = users.get(sessionKey);
            // get comment_id from the sql execution.
            int comment_id = db.insertComment(user_id,post_id, req.mMessage);
            // NB: createEntry checks for null title and message
            if (post_id <= 0) {
                return gson.toJson(new StructuredResponse("error", "error adding comment", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null , comment_id));
            }
        });
        Spark.put(":sessionKey/:post_id/likes", (request, response) -> {
            // get session key for the user making the post
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
             // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");
 
            // implement session key check, if it exists in the hashtable then continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                 return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);

            int user_id = users.get(sessionKey);
            int post_id = Integer.parseInt(request.params("post_id"));
            db.removeDislike(user_id,post_id);
            int checkLike = db.checkLike(user_id, post_id);
            int result = 1;

            if(checkLike > 0){
                db.removeLike(user_id,post_id);
            }
            else{
                result = db.insertLike(user_id,post_id);
            }
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "error adding post", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
        Spark.put(":sessionKey/:post_id/dislikes", (request, response) -> {
            // get session key for the user making the post
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
             // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");
 
            // implement session key check, if it exists in the hashtable then continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                 return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            int user_id = users.get(sessionKey);
            int post_id = Integer.parseInt(request.params("post_id"));
            db.removeLike(user_id,post_id);
            int checkDislike = db.checkDislike(user_id,post_id);
            int result = 1;
            
            if(checkDislike > 0){
                db.removeDislike(user_id,post_id);
            }
            else{
                result = db.insertDislike(user_id,post_id);
            }

            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "error adding post", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, post_id));
            }
        });
        
        // DELETE route for removing a post from the db
        Spark.delete(":sessionKey/posts/:email/:post_id", (request, response) -> {
            // get session key for the user making the post
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");

            // implement session key check, if it exists in the hashtable then continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            // If we can't get an ID, Spark will send a status 500
            int post_id = Integer.parseInt(request.params("post_id"));
            String email = (String) (request.params("email"));
            int user_id = db.getUserId(email);
            // check if user_id from the sessionKey matches with the user who made the post. If they match they have permissions to delete the post
            if(user_id != users.get(sessionKey)){
                return gson.toJson(new StructuredResponse("error", "invalid permissions, mismatching user id", null));
            }
            // NB: we won't concern ourselves too much with the quality of the
            // message sent on a successful delete
            int result = db.deletePost(post_id);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + post_id, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });
            // DELETE route for removing a user from the db
        Spark.delete(":sessionKey/users/:email", (request, response) -> {
            // get session key for the user making the post
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");
            // implement session key check, if it exists in the hashtable then continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                    return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            // If we can't get an ID, Spark will send a status 500
            String email = (String) (request.params("email"));
            int user_id = db.getUserId(email);
            // check if user_id from the sessionKey matches with the user who made the post. If they match they have permissions to delete the post
            if(user_id != users.get(sessionKey)){
                return gson.toJson(new StructuredResponse("error", "invalid permissions, mismatching user id", null));
            }
            // NB: we won't concern ourselves too much with the quality of the
            // message sent on a successful delete
            int result = db.deleteUser(user_id);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + user_id, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
       });
        Spark.put(":sessionKey/comments/:email/:comment_id", (request, response) -> {
            System.out.println("in weird put route");
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");

            // implement session key check, if it exists in the hashtable then continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }

            String email = (String) request.params("email");
            int comment_id = Integer.parseInt(request.params("comment_id"));
            int user_id =  db.getUserId(email);
            ComRequest req = gson.fromJson(request.body(), ComRequest.class);
            // check if userid from sessionKey matches with the userid from the email
            if(user_id != users.get(sessionKey)){
                return gson.toJson(new StructuredResponse("error", "invalid permissions, mismatching user id", null));
            }
            int result = db.updateComment(comment_id, req.mComment);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + comment_id, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
        
        Spark.put(":sessionKey/users/:email", (request,response) -> { 
            int sessionKey = Integer.parseInt(request.params("sessionKey"));
            response.status(200);
            response.type("application/json");
            // implement session key check, if it exists in the hashtable then continue, if not return error.
            if (users.containsKey(sessionKey) == false) {
                return gson.toJson(new StructuredResponse("error", "Invalid Session Key", null));
            }
            String email = (String) request.params("email");
            int user_id =  db.getUserId(email);
            UserRequest req = gson.fromJson(request.body(), UserRequest.class);
            // check if userid from sessionKey matches with the userid from the email
            if(user_id != users.get(sessionKey)){
                return gson.toJson(new StructuredResponse("error", "invalid permissions, mismatching user id", null));
            }
            int result = db.editUserSexOrient(user_id, req.mSex);
            result = db.editUserGender(user_id, req.mGender);
            result = db.editUserNote(user_id, req.mNote);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to update user " + user_id, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }

        });




       /** *
        // PUT route for updating a row in the db. This is almost
        // exactly the same as POST 
        Spark.put("/messages/:id", (request, response) -> {
             // implement session key check, if it exists continue, if not return error.
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = db.updateOne(idx, req.mMessage);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
        **/
        /** 
        Spark.put("/messages/:id/likes", (request, response) -> {
             // implement session key check, if it exists continue, if not return error.
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            //SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            DataRow current = db.selectOne(idx);
            int numOfLikes = current.mLikes;
            int result = db.likes(idx, numOfLikes);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
        **/

        /** 
        Spark.put("/messages/:id/dislikes", (request, response) -> {
             // implement session key check, if it exists continue, if not return error.
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            //SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            DataRow current = db.selectOne(idx);
            int numOfLikes = current.mLikes;
            int result = db.dislikes(idx,numOfLikes);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
         
        **/
        
        
        /** 
        // DELETE route for removing a row from the db
        Spark.delete("/messages/:id", (request, response) -> {
             // implement session key check, if it exists continue, if not return error.
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the
            // message sent on a successful delete
            int result = db.deleteRow(idx);
            if (result <= 0) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });
    }
        **/
    }
    /**
     * Get an integer environment varible if it exists, and otherwise return the
     * default value.
     * 
     * @envar The name of the environment variable to get.
     * @defaultVal The integer value to use as the default if envar isn't found
     * 
     * @returns The best answer we could come up with for a value for envar
     */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

    /**
 * Set up CORS headers for the OPTIONS verb, and for every response that the
 * server sends.  This only needs to be called once.
 * 
 * @param origin The server that is allowed to send requests to this server
 * @param methods The allowed HTTP verbs from the above origin
 * @param headers The headers that can be sent with a request from the above
 *                origin
 */
private static void enableCORS(String origin, String methods, String headers) {
    // Create an OPTIONS route that reports the allowed CORS headers and methods
    Spark.options("/*", (request, response) -> {
        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
        }
        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
        }
        return "OK";
    });

    // 'before' is a decorator, which will run before any 
    // get/post/put/delete.  In our case, it will put three extra CORS
    // headers into the response
    Spark.before((request, response) -> {
        response.header("Access-Control-Allow-Origin", origin);
        response.header("Access-Control-Request-Method", methods);
        response.header("Access-Control-Allow-Headers", headers);
    });
}
}