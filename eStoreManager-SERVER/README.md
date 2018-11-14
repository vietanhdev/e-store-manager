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
    mysql> INSERT INTO roles(name) VALUES('ROLE_ADMIN');

    mysql> INSERT INTO roles(name) VALUES('ROLE_MANAGER');
     
    mysql> INSERT INTO roles(name) VALUES('ROLE_CASHIER');
    ```


## Use API of project `eStoreManager`

<!-- ONLY FOR ADMIN ROLE -->
1. Create user account
   
    * Description: Admin can create other users (admin, manager, cashier)
    * Path: /api/createUser
    * PreAuthorize: ROLE_ADMIN
    * Request method: POST
    * Header:
        * Authorization: Bearer JWT
        * Content-type: application/json
    * Body: {"name": "user", "email": "user@gmail.com", "username": "user", "password": "user@gmail.com", "salary": 0, "roles": ["ROLE_MANAGER", "ROLE_CASHIER"]}

2. Reset other user password

    * Description: Admin use to reset new password for user
    * Path: /api/users/{username}/resetPassword
    * PreAuthorize: ROLE_ADMIN
    * Request method: PUT
    * Header:
        *  Authorization: Bearer JWT
        *  Content-type: application/json
    * Body: {"new_password": "user#123"}

  
3. Get other user information

    * Description: Admin use to get information of other user (name, username, email, address, mobileNo, salary, roles)
    * Path: /api/users/{username}
    * PreAuthorize: ROLE_ADMIN
    * Request method: GET
    * Header:
        *  Authorization: Bearer JWT

4. Update other user information

    * Description: Admin use to change information of other user (name, username, email, address, mobileNo, salary, roles)
    * Path: /api/users/{username}
    * PreAuthorize: ROLE_ADMIN
    * Request method: PUT
    * Header:
        *  Authorization: Bearer JWT
        *  Content-type: application/json
    * Body: {"name": "user", "username": "user#123", "email": "user@gmail.com", "address": "his address", "mobilNo": "0914141452", "roles": ["ROLE_MANAGER"]}

5. Delete other user account
   
    * Description: Admin user to delete other user account
    * Path: /api/users/{username}
    * PreAuthorize: ROLE_ADMIN
    * Request method: DELETE
    * Header:
        *  Authorization: Bearer JWT

<!-- FOR ALL THE ROLE -->
6. Sign in

    * Description: User use to get JWT
    * Path: /api/public/login
    * Request method: GET
    * Header:
        *  Content-type: application/json
    * Body: {"username": "user", "password": "user#123"}

7. Change password

    * Description: User use to change their own password
    * Path: /api/changePassword
    * Request method: PUT
    * Header:
        *  Authorization: Bearer JWT
        *  Content-type: application/json
    * Body: {"old_password": "user@gmail.com", "new_password": "user#123"}

8.  Get personal information
   
    * Description: User use to get his own information (name, username, email, address, mobileNo, salary, roles)
    * Path: /api/me
    * Request method: GET
    * Header:
        *  Authorization: Bearer JWT
        *  Content-type: application/json
    * Body: NULL
  
9. Update personal information

    * Description: User use to change his own information (name, username, email, address, mobileNo)
    * Path: /api/me
    * Request method: PUT
    * Header:
        *  Authorization: Bearer JWT
        *  Content-type: application/json
    * Body: {"name": "user", "username": "user", "email": "user@gmail.com", "address": "your address", "mobilNo": "0914141452"}

10. Delete personal account

    * Description: User use to delete his own account
    * Path: /api/me
    * Request method: DELETE
    * Header:
        *  Authorization: Bearer JWT

11. Check username availability
   
    * Description: User can check if new username is availability
    * Path: /api/users/checkUsernameAvailability
    * PreAthorize: ROLE_ADMIN, ROLE_MANAGER, ROLE_CASHIER
    * Request method: GET
    * Header:
        *  Authorization: Bearer JWT

12. Check email availability
    
    * Description: User can check if new email is availability
    * Path: /api/users/checkEmailAvailability
    * PreAthorize: ROLE_ADMIN, ROLE_MANAGER, ROLE_CASHIER
    * Request method: GET
    * Header:
        *  Authorization: Bearer JWT
