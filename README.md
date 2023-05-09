CRUD-Spring-Boot
CRUD Example of Spring-Boot-REST (Courses)

1. You can clone it from github by running following command
   https://github.com/gandhapodi-k/courses-crud-operations.git
2. Import project into IntelliJ IDE
   File -> Open -> Browse Project from cloned location
3. Right click on CourseApplication.java file and run as Java Application 

4. Once Sprint Boot Application will be started successfully then we can call following Endpoints by using POSTMAN
   Environments/API-Collections/Courses.postman_collection.json

5. To get list of courses call following endpoint with GET Request
      http://localhost:8080/v01/api/courses
6. To get particular courses call following endpoint with GET Request
      http://localhost:8080/v01/api/courses/<id>
7. To create courses call following endpoint with POST Request
      http://localhost:8080/v01/api/courses 
        JSON body:
               [
               {
                    "courseName": "oops",
                    "instructor": "don",
                    "courseDuration": 2
               }
               ]
8. To update the particular courses call following endpoint with PUT Request
      http://localhost:8080/v01/api/courses/<id>
       JSON body:
            [
            {
                "courseName": "oops",
                "instructor": "don",
                "courseDuration": 2
           }
           ]
9. To delete particular courses call following endpoint with DELETE Request
      http://localhost:8080/v01/api/courses/<id>
 
Note - Replace with actual id
