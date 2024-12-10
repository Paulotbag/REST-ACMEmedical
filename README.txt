Member              | Contributions                                                            | Average Peer Grade (Grade Provided by Abraham, Azadeh, Harpinder, Paulo)
--------------------|-------------------------------------------------------------------------|------------------------------------------------
Paulo Gomes         | Managed git repository, added JPA annotations to 4 entity classes       | 100%
                    | entity classes, finished Custom Authentication Mechanism, stablished    |
                    | Relationship Between SecurityUser and Physician, Debuged RESTful        |
                    | Endpoint requests and project deployment, fixed ROLE access and JSON file for Postman. |
Azadeh Sadeghtehrani | Wrote 20 JUnit tests, did the whole Building a REST API task, added JPA | 100%
                    | annotations to 4 entity classes.                                        |
Abraham El Kachi    | Wrote 12 JUnit tests, Build the database (initially, we did not see the | 100%
                    | provided sql query), POPULATE TABLES USING SQL COMMANDS (initially,     |
                    | we did not see the provided sql query), added JPA annotations to 4      |
                    | entity classes, did some Securing REST Endpoints.                       |
Harpinder Brar      | added JPA annotations to 5 entity classes, Wrote 18 JUnit tests, did     | 100%
                    | some Securing REST Endpoints.                                           |

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
NOTE FROM TEAM: 
This is a collaborative work focused on learning RESTful API using Java. Besides, this work also involved learning how to use JPA and Jackson annotation.

The project involved creating a RESTful system for the fictional company "ACME Medical Corp." that integrates a database schema with Java objects, exposing them 
as secure RESTful APIs with CRUD functionalities, supported by JUnit tests and a Maven Surefire report.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Key Tasks:
1)JPA Annotations:
Ensure proper mapping of entity relationships.

2)Custom Authentication:
Implement database-backed authentication in the CustomIdentityStoreJPAHelper class.
Map relationship between entity classes.
Map a one-to-one relationship between SecurityUser and Physician for enhanced resource security.

3)REST API Construction:
Create APIs for each model entity with CRUD operations.
Define appropriate HTTP methods and endpoints.

4)Role-Based Security Rules:
Implement rules like restricting entity creation to ADMIN_ROLE, and ensuring USER_ROLE can only access linked data.

5)JUnit Tests:
Build tests to confirm API functionality.
Validate responses, statuses, and data consistency.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Tools and Resources:

#Development: Jakarta EE (JPA, JAX-RS), Eclipse and IntelliJ IDE, Maven.
#Testing: JUnit 5, Maven Surefire Plugin.
#API Testing: Postman.
#Security: JEE Security roles, annotations, and database-backed authentication.
