# Back-End Server


## Running the Backend
1. Open up git bash
2. Navigate to the backend folder
3. Run mvn clean compile
4. Run mvn package
5. Run mvn heroku:deploy

## Testing
1. Does the database exist
2. Does the GET route work?
3. Does the POST route work?
4. Does the PUT message route work?
5. Does the PUT like route work?
6. Does DELETE work?

## Routes
- __Get__: returns one entity from the table
  \GET /messages 
  JSON Route: {
    "mId" = int
    "mTitle" = String
    "mContent" = String
    "mLikes" = int
    "mCreated" = timecreated
  }

- __Post__: adds a new message, title, and like counter to the current database table
  \POST /messages
  JSON Route: {
    "mTitle" = String
    "mContent" = String
  }

- __Delete__: removes an entity specified by an ID number from the table
  \DELETE /messages/#
  JSON Route: {
    "mId" = int
  }

- __Put__: changes an existing entity in the table by specifying an ID number
  \PUT /messages/#
JSON Route:{
  "mId" = int
  "mContent" = String
}

- __Put__: Adds like or dislikes if already liked
  \PUT /messages/#/3
JSON Route:{
  "mID" = int
}