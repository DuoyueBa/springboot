# Simple Example of REST API in SpringBoot
This is a simple example to provide REST API by SpringBoot. There are two projects in example. spring-boot-rest-1 provides REST API to manage "User". spring-boot-rest-2 provides REST API to send mail.

# Requirements
1. JDK8
2. Maven
3. docker-compose

# Step
1. Download source code by "git clone"
2. Compile sourcecode and package executable jar file. 
   cd spring-boot-rest-2
   mvn clean install
Because spring-boot-rest-1 dendpends on one class of spring-boot-rest-2, and SpringBoot repackages jar file, we need replace springboot jar files with normal jar file.
   Enter directory Maven local repository directory and replace it.
   cd $MAVEN_REPO/com/cct/spring-boot-rest-2/0.0.1-SNAPSHOT/
   rm spring-boot-rest-2-0.0.1-SNAPSHOT.jar
   mv spring-boot-rest-2-0.0.1-SNAPSHOT-source.jar spring-boot-rest-2-0.0.1-SNAPSHOT.jar
Continue to compile spring-boot-rest-1
   cd spring-boot-rest-1
   mvn clean install
3. Now we can run these two services
   java -jar spring-boot-rest-2/target/spring-boot-rest-2-0.0.1-SNAPSHOT.jar
   java -jar spring-boot-rest-1/target/spring-boot-rest-1-0.0.1-SNAPSHOT.jar
4. Access to service of spring-boot-rest-1 by SOAP-UI tool. I use Soap-UI. After receiving requests, spring-boot-rest-1 service will add/update/delete User and call spring-boot-rest-2 service to send mail.
   Note: spring-boot-rest-2 uses a fake mail server, so it can not send mail successfully.
5. We can run services by docker-compose.
   cd springboot
   docker-compose build
   docker-compose up
   
# REST API
spring-boot-rest-1 service provides the following REST API.
1. Register single user
URL:        /register/user
Method:     POST
Parameter:  {
	              "name": "Zhouyu",
	               "age": 26,
	               "email": "12345@345"
	          }
Success Response: 
            code : 201
            Content: {"name":"Zhouyu","age":26,"email":"12345@345","registrationStatus":"Successful"}
Error Response:
            code : 409
            Content: User alread exists
            or
            code : 400
            Content: User email must not be empty.
            
2. Register multiple users
URL:        /register/users
Method:     POST
Parameter:  [{
	              "name": "Zhouyu",
	               "age": 26,
	               "email": "12345@345"
	          },
            {
	              "name": "Zhugeliang",
	               "age": 23,
	               "email": "1235@345"
	          }]
Response: 
            code : 201
            Content: []
            If all users are registered, then return empty. Otherwise names of failed users are returned in response;
            code : 201
            Content: ["Zhouyu","Zhugeliang"]
3. Update user
URL:        /update/user
Method:     PUT
Parameter:  {
	              "name": "Zhouyu",
	               "age": 26,
	               "email": "12345@345"
	          }
Success Response: 
            code : 200
            Content: Update successful
            or 
            code : 200
            Content: Update un-successful
Error Response:
            code : 400
            Content: User email format is wrong.
            or
            code : 400
            Content: User email must not be empty.
            
4. Update users
URL:        /update/users
Method:     PUT
Parameter:  [{
	              "name": "Zhouyu",
	               "age": 26,
	               "email": "12345@345"
	          },
            {
	              "name": "Zhugeliang",
	               "age": 23,
	               "email": "1235@345"
	          }]
Success Response: 
            code : 200
            Content: []
            or 
            If all users are updated successfully, then return empty. Otherwise names of failed users are returned in response;
            code : 200
            Content: ["Zhouyu"]

5. Delete user
URL:        /delete/user/{email}
Method:     DELETE
Parameter:  {
	              "name": "Zhouyu",
	               "age": 26,
	               "email": "12345@345"
	          }
Success Response: 
            code : 200
            Content: Delete successful
Error Response:
            code : 404
            Content: {"timestamp":"2019-08-08T07:56:14.318+0000","status":404,"error":"Not Found","message":"No message available","path":"/delete/user"}
            
6. Delete users
URL:        /delete/users
Method:     PUT
Parameter:  ["12345@345",
            "1235@345"
	          ]
Success Response: 
            code : 200
            Content: []
            or 
            If all users are deleted successfully, then return empty. Otherwise names of failed users are returned in response;
            code : 200
            Content: ["Zhouyu"]
