# BUILD AND RUN INSTRUCTION:

## Build Spring Boot Apps with VS Code
Visual Studio Code is an ideal lightweight development environment for Spring Boot developers and there are several useful VS Code extensions including:
* [Spring Boot Tools](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-spring-boot)
*  [Spring Initializr](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-initializr)

1. Prerequisites:
    * Java Development Kit (JDK), version 1.8
    * Apache Maven, version 3.0 or later
    * MySQL, version 14.14 or later
2. Create database "eStoreManager" with user "springuser" on mysql server (you can change all this default information in `application.properties` file):
    * Open terminal and enter command line

        ```
        $ sudo mysql --password
        ```

    * Create database `eStoreManager`:
  
        ```
        mysql> CREATE DATABASE eStoreManager;
        ```

    * Create user `springuser` with password `ThePassword@123`:
  
        ```
        mysql> CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'ThePassword@123';
        ```

    * Provide the user with access to the information:
  
        ```
        mysql> GRANT ALL PRIVILEGES ON eStoreManager.* TO 'springuser'@'localhost' IDENTIFIED BY 'ThePassword@123';
        ```
    
3. Choose Debug on Activity Bar (or Ctrl+Shift+D), Click Start Debugging Button to start project "eStoreManager-SERVER".
4. At this time, after the project started successful, there will be 3 tables in `eStoreManager` database is automatically created (`roles`, `user_roles`, `users`). We’ll have a fixed set of predefined roles in our application (you can add more in `model/RoleName.java`). Whenever a user logs in, we’ll assign ROLE_USER to it by default. For assigning the roles, they have to be present in the database. So let’s create the two default r   oles in the database by executing the following insert statements:

    ```
    mysql> INSERT INTO roles(name) VALUES('ROLE_USER');
    mysql> INSERT INTO roles(name) VALUES('ROLE_ADMIN');
    mysql> INSERT INTO roles(name) VALUES('ROLE_MANAGER');
    ```


## Use API of project `eStoreManager`

**Signup**
* Request URL: http://localhost:8080/api/auth/signin
* Request Method: POST
* Request Payload: {name: "An Nguyen", email: "syan2244@gmail.com", username: "syan", password: "Pass@12345"}

**Signin**
* Request URL: http://localhost:8080/api/auth/signin
* Request Method: POST
* Request Payload: {usernameOrEmail: "an123", password: "12345678"}

**Check username available**
* Request URL: http://localhost:8080/api/user/checkUsernameAvailability?username=syan
* Request Method: GET

**Check email available**

* Request URL: http://localhost:8080/api/user/checkUsernameAvailability?email=syan224@gmail.com
* Request Method: GET

**Get current user information**
* Request URL: http://localhost:8080/api/user/me
* Request Method: GET
* Request Headers:
    * authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNzc2IiwiaWF0IjoxNTM5OTU2NTc4LCJleHAiOjE1NDA1NjEzNzh9.CvHqlL-plZytqApMOyTQTNewQeSB2FEKr3LZdR7NkCFnirObpwT5OOLCdiUrjlXrPhndM0FFapcqrOZjtGfU5Q

**Get user profile**
* Request URL: http://localhost:8080/api/users/an123
* Request Method: GET
