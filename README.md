## Setup

1. Clone the repository to local directory

```
cd <local_directory> (for example: cd "D:/project_repo")
git clone <repository_url> (for example: git clone abc.git)
```
2. To run and test application use the maven version 3.5.0 or above. (To check Maven version -> open cmd.exe and enter below command)

```
mvn -v
```
3. Make sure Java 1.8 or above is installed on your system. (To check Java version -> open cmd.exe and enter below command)

```
java -version
```

## Running check style
Code has been formatted with Google code formatter and refactored with checkstyle

```
cd <project_directory> (for example: cd d:/customerprofilemanagement/)
mvn clean compile jxr:jxr checkstyle:checkstyle
```
Snapshot of Checkstyle result:

![picture](checkstyle_results.PNG "CheckStyle result")

## Running tests
```
cd <project_directory> (for example: cd d:/customerprofilemanagement/)
Unit tests -> mvn clean test
Integration tests -> mvn -Dtest=*IT.java clean test
All tests -> mvn clean verify
```
For running tests this project uses maven defaults
 - Unit test ends with `*Test.java`
 - Integration tests ends with `*IT.java`

## Running Clover
To check the code coverage of Tests, execute the below command.

either we can use Clover or Build-in Coverage tool

```
cd <project_directory> (for example: cd d:/customerprofilemanagement/)
mvn clover:check -Dmaven.clover.targetPercentage=65%
```
Snapshot of Coverage Dashboard: 65.9% covered
![picture](code_coverage.PNG "Coverage Result")

## Running Sonar
To check static code analysis,

```
cd <project_directory> (for example: cd d:/customerprofilemanagement/)
mvn clean verify sonar:sonar
```
![picture](Sonar_Qube.PNG "Sonar Result")

## SWAGGER API Contract Definition

Approach followed: Top-down

Swagger definitions can be found in /src/main/resources/swagger/customer_profile_management.yml 
and /src/main/resources/swagger/swagger.json

To visualize and interact with the API’s resources, 

Click [Swagger UI](http://localhost:8080/swagger-ui.html) Link. 
	
Note: Make sure application is running

To test application through Swagger UI, the following lines has to be commented out since Swagger doesn't support Authorization

```
com.qant.customerprofilemanagement.configuration.SwaggerConfig.java:

/*.securitySchemes(Collections.singletonList(
						new ApiKey("Bearer", "Authorization", "header")))*/
```
save & re-run the application after making the above mentioned changes.


## To run application
```
cd <project_directory> (for example: cd d:/customerprofilemanagement/)
mvn clean spring-boot:run
```

## Package and run application
To create package and run application by skipping all tests, use the below command.

```
cd <project_directory> (for example: cd d:/customerprofilemanagement/)
mvn clean package -Dmaven.test.skip
java -jar target\customerprofilemanagement*.war
```
## Security
We can achieve security mechanism in many ways.  Here in this project, Oauth has been implemented to safeguard from un-authorized users.
There is an alternate way to ensure security.  We can use an existing api which is available api-store. We have to subscribe and use it. 

## Get Oauth Token
If you are using postman, follow the below procedure.

```
MethodType = POST
URL: http://localhost:8080/oauth/token
```
under body tab, select x-www-form-urlencoded radio button and provide the below form fields

```
grant_type: password
username: testuser1 or testuser2
password: password1 or password2 
```
(Note: passwords are encrypted before it is stored in h2 database.)

Hit Send button, on successful authentication, access_token will be returned as response from Oauth server.

## To Create Customer
Copy the "access_token" and go to another tab and try hitting the below for customer creation procedure

Access token should be passed in the Headers as below.

```
methodType: POST
URL: http://localhost:8080/customer
Authorization: Bearer <access_token> 
(for example: 
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyX2FwcGxpY2F0aW9uIl0sInVzZXJfbmFtZSI6InRlc3R1c2VyMiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1NDc3MDAwNzEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIwMTBlZmVlYy1lZDE1LTQ4YzktYWJlOS04OTllNGZiMWU5MGQiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWFwcCJ9.wlnm0oGLlI8P0HmyMLWCSyW_-3J0bxFn4lngd-Xuu7M)
Content-Type: application/json
```
keep the input customer json object in the body (raw) 

Sample JSON:

```
{
  "firstName": "test",
  "lastName": "test",
  "dateOfBirth": "1988-12-01",
  "homeAddress": {
    "unitNumber": "1234",
    "street": "test",
    "city": "test",
    "state": "test",
    "postalCode": 123,
    "country": "test"
  },
  "email": "test@gmail.com"
}
```

To explore more, kindly use swagger-ui. To open [click here](http://localhost:8080/swagger-ui.html).

## Execute through curl command
Create Customer:

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyX2FwcGxpY2F0aW9uIl0sInVzZXJfbmFtZSI6InRlc3R1c2VyMiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1NDc3MTgxMzYsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI0MWJlOGM4My02NGRmLTRkNjUtYThkOS1mYmQ1YWExNDNlM2QiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWFwcCJ9.yaw3WSrVef-CiehVeRbQlS0tZcOgAm30oYHSYTah-VE' -d '{ \ 
   "customerId": 0, \ 
   "dateOfBirth": "1990-01-01", \ 
   "email": "test%40mail.com", \ 
   "firstName": "John", \ 
   "homeAddress": { \ 
     "city": "sydney", \ 
     "country": "Australia", \ 
     "postalCode": 2060, \ 
     "state": "NSW", \ 
     "street": "zulu street", \ 
     "unitNumber": "123" \ 
   }, \ 
   "lastName": "Mathew", \ 
   "officeAddress": { \ 
     "city": "sydney", \ 
     "country": "Australia", \ 
     "postalCode": 2060, \ 
     "state": "NSW", \ 
     "street": "zulu street", \ 
     "unitNumber": "123" \ 
   } \ 
 }' 'http://localhost:8080/customer'
 ```

## Technical Flow Diagram

 - Request received from mobile app and web channel are identified by "User-Agent" param in the header.

 - Oauth token expiry time can be configured according to the the chennel type.  Mobile App can have longer active time token than web channel.
  
![picture](Technical_Design.PNG "Clover Result")


