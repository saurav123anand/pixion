Login with Google
1. OAuth2 -- Add OAuth 2 client starter dependency
2. Google->
     Client Id
     Client secrets
3. add the oauth login configuration 
4. login page /login and success handler
5. In your success handler you are getting data... we can save data based on provider information 

Many-to-Many: Users ↔ Posts (via Likes)
Explanation:

A User can like many Posts.
A Post can be liked by many Users.
This is represented using a join table (likes) that has:
user_id referencing users.id.
post_id referencing posts.id.
Database Representation:

likes is the join table with user_id and post_id as foreign keys.