# @@@ API TESTING SCENARIO @@@

## 1. Employee management API
---

#### 1.1. Login into store

###### 1.1.1. Request

* Path: api/v1/login
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization:

* Body:
```
{
    "username": "admin",
    "password": "admin@gmail.com"
}
```

###### 1.1.2. Response

* On success:
```
{
    "success": true,
    "id": 1,
    "name": "Admin dep trai",
    "username": "admin",
    "salary": null,
    "email": "admin@gmail.com",
    "address": "Hanoi",
    "mobileNo": "0917915518",
    "roles": [
        "ROLE_ADMIN"
    ],
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQzNTEyNTYzLCJleHAiOjE1NDQxMTczNjN9.S4zVTJY0ejIjTWbNni1Wo6bQijeRb9fkhs_mi8JAIBx_QbCCPe4Vo_ITbooRWtoDwlihUvfzq9wgFXQxwUJd2w"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```


* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 1.2. Get your own information

###### 1.2.1. Request

* Path: api/v1/me
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 1.2.2. Response

* On success:
```
{
    "success": true,
    "id": 1,
    "name": "Admin dep trai",
    "username": "admin",
    "salary": null,
    "email": "admin@gmail.com",
    "address": "Hanoi",
    "mobileNo": "0917915518",
    "roles": [
        "ROLE_ADMIN"
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

#### 1.3. Update your own information

###### 1.3.1. Request

* Path: api/v1/me
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (you can choose any fields you want to change, all fields are not compulsory)
```
{
    "name": "Admin dep trai",
    "username": "admin",
    "email": "admin@gmail.com",
    "address": "Hanoi",
    "mobileNo": "0917915518"
}
```

###### 1.3.2. Response

* On Success:
```
{
    "success": true,
    "code": "update_user_information_successful",
    "message": "update your information successful"
}
```

* On fail (unauthorized)
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 1.4. Change your own password

###### 1.4.1. Request

* Path: /me/change_password
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:
```
{
	"oldPassword": "admin@gmail.com",
	"newPassword": "admin@password"
}
```

###### 1.4.2. Response

* On success:
```
{
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQzNTEyNjQxLCJleHAiOjE1NDQxMTc0NDF9.52Mi3nwFVmNtJM-04dknYrCgm8eYe2utlj2k82SEj5lCFvzvypFKuLXxvdAS-OLEYn1DCvMHOQ-L_TlZ5idDww"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (old password is not correct):
```
{
    "success": false,
    "code": "old_password_not_correct",
    "message": "old password is not correct"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 1.5. Delete your own account

###### 1.5.1. Request

* Path: /api/v1/me
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 1.5.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_user_successful",
    "message": "delete user successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

#### 1.6. Create new account

###### 1.6.1. Request

* Path: /api/v1/users
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (username, email and password are compulsory, other fields can be skipped)
```
{
    "name": "ceo la",
    "username": "ceo",
    "password": "ahsdguyih29387ey3892767565jihi7897897",
    "salary": 1000,
    "email": "ceo@gmail.com",
    "address": "hoa binh",
    "mobileNo": "0916167558",
    "roles": [
        "ROLE_ADMIN", "ROLE_MANAGER"
    ]
}
```

###### 1.6.2. Response

* On success:
```
{
    "success": true,
    "id": 9
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (username is taken):
```
{
    "success": false,
    "code": "username_taken",
    "message": "Username is already taken!"
}
```

* On fail (email is taken):
```
{
    "success": false,
    "code": "email_taken",
    "message": "email is already taken!"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (role is not in list roleName):
```
{
    "success": false,
    "code": "wrong_role",
    "message": "role " + {wrong_role} + " does not exist"
}
```

* On fail (you don't have role admin):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 1.7. Get other user's information

###### 1.7.1. Request

* Path: /api/v1/users/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 1.7.2. Response

* On success:
```
{
    "success": true,
    "id": 9,
    "name": "ceo la",
    "username": "ceo",
    "salary": 1000,
    "email": "ceo@gmail.com",
    "address": "hoa binh",
    "mobileNo": "0916167558",
    "roles": [
        "ROLE_MANAGER",
        "ROLE_ADMIN"
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_user_id",
    "message": "user id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 1.8. Update other user's information

###### 1.8.1. Request

* Path: /api/v1/users/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (you can choose any fields you want to change, all fields are not compulsory)
```
{
    "name": "ceo la la",
    "username": "ceola",
    "password": "md5@password"
    "salary": 100,
    "email": "ceola@gmail.com",
    "address": "hai phong",
    "mobileNo": "0916157558",
    "roles": [
        "ROLE_CASHIER",
        "ROLE_ADMIN"
    ]
}
```

###### 1.8.2. Response

* On success:
```
{
    "success": true,
    "code": "update_user_information_successful",
    "message": "update user information successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (username is taken):
```
{
    "success": false,
    "code": "username_taken",
    "message": "Username is already taken!"
}
```

* On fail (email is taken):
```
{
    "success": false,
    "code": "email_taken",
    "message": "email is already taken!"
}
```

* On fail (role is not in list roleName):
```
{
    "success": false,
    "code": "wrong_role",
    "message": "role " + {wrong_role} + " does not exist"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_user_id",
    "message": "user id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 1.9. Get all users' information

###### 1.9.1. Request

* Path: /api/v1/users?page=0&size=3
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 1.9.2. Response

* On success:
```
{
    "success": true,
    "users": [
        {
            "id": 1,
            "name": "Admin dep trai",
            "username": "admin",
            "salary": null,
            "email": "admin@gmail.com",
            "address": "Hanoi",
            "mobileNo": "0917915518",
            "roles": [
                "ROLE_ADMIN"
            ]
        },
        {
            "id": 4,
            "name": "cashier",
            "username": "cashier",
            "salary": 1000,
            "email": "cashier@gmail.com",
            "address": "hoa binh",
            "mobileNo": "0916167558",
            "roles": [
                "ROLE_CASHIER"
            ]
        },
        {
            "id": 3,
            "name": "ceo la",
            "username": "ceo",
            "salary": 1000,
            "email": "ceo@gmail.com",
            "address": "hoa binh",
            "mobileNo": "0916167558",
            "roles": [
                "ROLE_MANAGER",
                "ROLE_ADMIN"
            ]
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (page number is less than zero):
```
{
    "success": false,
    "code": "page_num_unacceptable",
    "message": "Page number cannot be less than zero."
}
```

* On fail (page size is greater than MAX_PAGE_SIZE):
```
{
    "success": false,
    "code": "page_size_unacceptable",
    "message": "Page size must not be greater than 50"
}
```

#### 1.10. Delete other users's account

###### 1.10.1. Request

* Path: /api/v1/users/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 1.10.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_user_successful",
    "message": "delete user successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_user_id",
    "message": "user id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 2. Customer management API
---

#### 2.1. Create new customer

###### 2.1.1. Request

* Path: /api/v1/customers
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:(name field is compulsory, other fields can be skipped)
```
{
    "name": "customer_1",
    "email": "customer_1@gmail.com",
    "address": "ha noi",
    "mobileNo": "0916167887"
}
```

###### 2.1.2. Response

* On success:
```
{
    "success": true,
    "id": 1
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 2.2. Get all customer summary information

###### 2.2.1. Request

* Path: /api/v1/customers?page=0&size=3
* Method: GET
* Header:
    
    * Content-type: 
    * Authorization: Bearer JWT

* Body:

###### 2.2.2. Response

* On success:
```
{
    "success": true,
    "customers": [
        {
            "success": null,
            "id": 3,
            "name": "customer_3",
            "email": "customer_3@gmail.com",
            "address": "ha noi",
            "mobileNo": "0916167887"
        },
        {
            "success": null,
            "id": 2,
            "name": "customer_2",
            "email": "customer_2@gmail.com",
            "address": "ha noi",
            "mobileNo": "0916167887"
        },
        {
            "success": null,
            "id": 1,
            "name": "customer_1",
            "email": "customer_1@gmail.com",
            "address": "ha noi",
            "mobileNo": "0916167887"
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 2.3. Get a customer's information 

###### 2.3.1. Request

* Path: /api/v1/customer/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 2.3.2. Response

* On success:
```
{
    "success": true,
    "id": 1,
    "name": "customer_1",
    "email": "customer_1@gmail.com",
    "address": "ha noi",
    "mobileNo": "0916167887"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_customer_id",
    "message": "customer id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 2.4. Update a customer's information 

###### 2.4.1. Request

* Path: /api/v1/customers/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (you can choose any fields you want to change, all fields are not compulsory)
```
{
    "name": "customer_5",
    "email": "customer_5@gmail.com",
    "address": "ha noi",
    "mobileNo": "0916167997"
}
```

###### 2.4.2. Response

* On success:
```
{
    "success": true,
    "code": "update_customer_information_successful",
    "message": "update customer information successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_customer_id",
    "message": "customer id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 2.5. Delete a customer

###### 2.1.1. Request

* Path: /api/v1/customers/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


###### 2.1.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_customer_successful",
    "message": "delete customer successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_customer_id",
    "message": "customer id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 2.6. Search a customer
###### 2.6.1. Request

* Path: /api/v1/search/customers
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:
```
{
    "draw": 1,
    "start": 0,
    "length": 2,
    "search": {
        "value": "",
        "name": "",
        "email": "",
        "address": "",
        "mobileNo": ""
    }
}
```

###### 2.6.2. Response

* On success:
```
{
    "success": true,
    "draw": 10,
    "recordsTotal": 2,
    "recordsFiltered": 3,
    "data": [
        {
            "id": 1,
            "name": "customer_1",
            "email": "customer_1@gmail.com",
            "address": "hoa binh",
            "mobileNo": "0916167997"
        },
        {
            "id": 2,
            "name": "customer_2",
            "email": "customer_2@gmail.com",
            "address": "ha noi",
            "mobileNo": "0916167887"
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

## 3. Supplier management API
---

#### 3.1. Create new supplier

###### 3.1.1. Request

* Path: /api/v1/suppliers
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:(name field is compulsory, other fields can be skipped)
```
{
    "name": "supplier",
    "email": "supplier@gmail.com",
    "address": "ha noi",
    "mobileNo": "0324129032"
}
```

###### 3.1.2. Response

* On success:
```
{
    "success": true,
    "id": 1
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```


#### 3.2. Search a supplier
###### 3.2.1. Request

* Path: /api/v1/search/suppliers
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:
```
{
    "draw": 1,
    "start": 0,
    "length": 2,
    "search": {
        "value": "",
        "name": "",
        "email": "",
        "address": "",
        "mobileNo": ""
    }
}
```

###### 3.2.2. Response

* On success:
```
{
    "success": true,
    "draw": 10,
    "recordsTotal": 2,
    "recordsFiltered": 3,
    "data": [
        {
            "id": 1,
            "name": "customer1",
            "email": "customer1@gmail.com",
            "address": "hoa binh",
            "mobileNo": "0916167997"
        },
        {
            "id": 2,
            "name": "customer2",
            "email": "customer2@gmail.com",
            "address": "ha noi",
            "mobileNo": "0916167887"
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 3.3. Get a supplier's information 

###### 3.3.1. Request

* Path: /api/v1/supplier/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 3.3.2. Response

* On success:
```
{
    "success": true,
    "id": 1,
    "name": "supplier1",
    "email": "supplier1@gmail.com",
    "address": "ha noi",
    "mobileNo": "0916167887"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_supplier_id",
    "message": "supplier id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 3.4. Update a supplier's information 

###### 3.4.1. Request

* Path: /api/v1/suppliers/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (you can choose any fields you want to change, all fields are not compulsory)
```
{
    "name": "supplierX",
    "email": "supplierX@gmail.com",
    "address": "ha noi",
    "mobileNo": "0916167997"
}
```

###### 3.4.2. Response

* On success:
```
{
    "success": true,
    "code": "update_supplier_information_successful",
    "message": "update supplier information successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_supplier_id",
    "message": "supplier id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 3.5. Delete a supplier

###### 3.1.1. Request

* Path: /api/v1/suppliers/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


###### 3.1.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_supplier_successful",
    "message": "delete supplier successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_supplier_id",
    "message": "supplier id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 4. Product management API
---

#### 4.1. Create new product

###### 4.1.1. Request

* Path: /api/v1/products
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:(name field is compulsory, other fields can be skipped)
```
{
    "name": "vina milk",
    "price": 80000,
    "unit": "cartoon",
    "barcode": "dccf2351",
    "quantities": 500
}
```

###### 4.1.2. Response

* On success:
```
{
    "success": true,
    "id": 1
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```


#### 4.2. Search products
###### 4.2.1. Request

* Path: /api/v1/search/products
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:
```
{
    "draw": 1,
    "start": 0,
    "length": 10,
    "search": {
        "value": "",
        "name": "",
        "unit": "",
        "barcode": ""
    }
}
```

###### 4.2.2. Response

* On success:
```
{
    "success": true,
    "draw": 10,
    "recordsTotal": 2,
    "recordsFiltered": 2,
    "data": [
        {
            "id": 2,
            "name": "milk tea",
            "price": 85000,
            "unit": "bar",
            "barcode": "df32xv34",
            "quantities": 90
        },
        {
            "id": 1,
            "name": "chocolate white vn",
            "price": 80000,
            "unit": "lit",
            "barcode": "df32xv34",
            "quantities": 1000
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 4.3. Get a product's information 

###### 4.3.1. Request

* Path: /api/v1/product/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 4.3.2. Response

* On success:
```
{
    "success": true,
    "id": 2,
    "name": "milk",
    "price": 85000,
    "unit": "bar",
    "barcode": "df32xv34",
    "quantities": 100
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 4.4. Update a product's information 

###### 4.4.1. Request

* Path: /api/v1/product/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (you can choose any fields you want to change, all fields are not compulsory)
```
{
    "name": "milk",
    "price": 85000,
    "unit": "bar",
    "barcode": "df32xv34",
    "quantities": 100
}
```

###### 4.4.2. Response

* On success:
```
{
    "success": true,
    "code": "update_product_information_successful",
    "message": "update product type information successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 4.5. Delete a product

###### 4.1.1. Request

* Path: /api/v1/product/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


###### 4.1.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_product_successful",
    "message": "delete product successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 5. Sell management API
---

#### 5.1. Create new buy

###### 5.1.1. Request

* Path: /api/v1/buys
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:
```
{
	"buy_items": [
		{
			"product_id": 1,
			"supplier_id": 1,
			"price": 1000,
			"quantities": 2
		},
		{
			"product_id": 2,
			"supplier_id": 3,
			"price": 50,
			"quantities": 5
		}
	]
}
```

###### 5.1.2. Response

* On success:
```
{
    "success": true,
    "id": 10
}
```

* On fail (product id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
}
```

```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id must not be null"
}
```

* On fail (supplier id does not exist):
```
{
    "success": false,
    "code": "wrong_supplier_id",
    "message": "supplier id " + {id} + " does not exist"
}
```

```
{
    "success": false,
    "code": "wrong_supplier_id",
    "message": "supplier id must not be null"
}
```

* On fail (product is smaller than 0):
{
    "success": false,
    "code": "product_quantities_unacceptable",
    "message": "quantities of product must be greater than 0"
}

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```


#### 5.2. Search buys
###### 5.2.1. Request

* Path: /api/v1/search/buys
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:

    * Search by user id and date:

    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
            "user_id": 1,
            "start": "2018-01-30 06:52:05",
            "end": "2018-12-04 06:52:08"
        }
    }
    ```

    * Search by date:
    
    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
            "start": "2018-01-30 06:52:05",
            "end": "2018-12-04 06:52:08"
        }
    }
    ```

    * Search by user id:

    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
            "user_id": 1,
        }
    }
    ```

    * Get all:
    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
        }
    }
    ```



###### 5.2.2. Response

* On success:
```
{
    "success": true,
    "draw": 10,
    "recordsTotal": 3,
    "recordsFiltered": 2,
    "data": [
        {
            "id": 1,
            "user_id": 3,
            "user_name": "cashier",
            "active": false,
            "createdAt": "2018-12-04 13:13:16",
            "buy_items": [
                {
                    "product_id": 4,
                    "product_name": null,
                    "supplier_id": 1,
                    "supplier_name": "supplier",
                    "price": 970,
                    "quantities": 100,
                    "unit": null
                },
                {
                    "product_id": 1,
                    "product_name": "milk",
                    "supplier_id": 2,
                    "supplier_name": "vina",
                    "price": 100,
                    "quantities": 18,
                    "unit": "lit"
                }
            ]
        },
        {
            "id": 2,
            "user_id": 1,
            "user_name": "Admin dep trai",
            "active": true,
            "createdAt": "2018-12-05 20:32:09",
            "buy_items": [
                {
                    "product_id": 2,
                    "product_name": "chocolate",
                    "supplier_id": 1,
                    "supplier_name": "supplier",
                    "price": 1,
                    "quantities": 1,
                    "unit": "bar"
                }
            ]
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 5.3. Get a buy's information 

###### 5.3.1. Request

* Path: /api/v1/buys/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 5.3.2. Response

* On success:
```
{
    "success": true,
    "id": 1,
    "user_id": 3,
    "user_name": null,
    "active": false,
    "createdAt": "2018-12-04 13:13:16",
    "buy_items": [
        {
            "product_id": 1,
            "product_name": "milk",
            "supplier_id": 2,
            "supplier_name": "vina",
            "price": 100,
            "quantities": 18,
            "unit": "lit"
        },
        {
            "product_id": 3,
            "product_name": null,
            "supplier_id": 10,
            "supplier_name": null,
            "price": 10,
            "quantities": 150,
            "unit": null
        },
        {
            "product_id": 4,
            "product_name": null,
            "supplier_id": 1,
            "supplier_name": "supplier",
            "price": 970,
            "quantities": 100,
            "unit": null
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 5.4. Update a buy's information 

###### 5.4.1. Request

* Path: /api/v1/buys/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (this information will overwrite the original one)

```
{
    "user_id": 3,
    "active": false,
    "buy_items": [
        {
            "product_id": 1,
            "supplier_id": 2,
            "price": 100,
            "quantities": 18
        },
        {
            "product_id": 3,
            "supplier_id": 10,
            "price": 10,
            "quantities": 150
        },
        {
            "product_id": 4,
            "supplier_id": 1,
            "price": 970,
            "quantities": 100
        }
    ]
}
```

###### 5.4.2. Response

* On success:
```
{
    "success": true,
    "code": "update_buy_information_successful",
    "message": "update buy type information successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_buy_id",
    "message": "buy id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 5.5. Delete a buy

###### 5.1.1. Request

* Path: /api/v1/buys/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


###### 5.1.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_buy_successful",
    "message": "delete buy successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_buy_id",
    "message": "buy id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 6. Sell management API
---

#### 6.1. Create new sell

###### 6.1.1. Request

* Path: /api/v1/sells
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:
```
{
    "customer_id": 1,
    "tax": 10.2,
    "sell_items": [
        {
            "product_id": 1,
            "price": 1000,
            "quantities": 2
        },
        {
            "product_id": 2,
            "price": 50,
            "quantities": 5
        }
    ]
}
```

###### 6.1.2. Response

* On success:
```
{
    "success": true,
    "id": 2,
    "active": true
}
```

* On fail (total wrong):
```
{
    "success": false,
    "code": "wrong_total_bill",
    "message": "wrong total bill"
}
```

* On fail (user id does not exist):
```
{
    "success": false,
    "code": "wrong_user_id",
    "message": "user id " + {id} + " does not exist"
}
```

* On fail (customer id does not exist):
```
{
    "success": false,
    "code": "wrong_customer_id",
    "message": "customer id " + {id} + " does not exist"
}
```

* On fail (product id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
}
```

```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id must not be null"
}
```

* On fail (product is smaller than 0):
{
    "success": false,
    "code": "product_quantities_unacceptable",
    "message": "quantities of product must be greater than 0"
}

* On failt(not enough product):
{
    "success": false,
    "code": "not_enough_product",
    "message": "there are only +488.0 items of product id 1 in warehouse"
}

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```


#### 6.2. Search sells
###### 6.2.1. Request

* Path: /api/v1/search/sells
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:

    * Search by user id and customer id and date:

    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
            "user_id": 1,
            "customer_id": 1,
            "start": "2018-01-30 06:52:05",
            "end": "2018-12-05 06:52:08"
        }
    }
    ```

    * Search by date:
    
    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
            "start": "2018-01-30 06:52:05",
            "end": "2018-12-05 06:52:08"
        }
    }
    ```

    * Search by user id:

    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
            "user_id": 1,
        }
    }
    ```

    * Search by customer id:

    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
            "customer_id": 1,
        }
    }
    ```

    * Get all:
    ```
    {
        "draw": 1,
        "start": 0,
        "length": 10,
        "search": {
        }
    }
    ```



###### 6.2.2. Response

* On success:
```
{
    "success": true,
    "draw": 10,
    "recordsTotal": 8,
    "recordsFiltered": 1,
    "data": [
        {
            "id": 1,
            "user_id": 3,
            "user_name": null,
            "customer_id": 3,
            "customer_name": null,
            "tax": 20.07,
            "active": true,
            "createdAt": "2018-12-04 13:22:38",
            "sell_items": [
                {
                    "product_id": 3,
                    "product_name": null,
                    "price": 10,
                    "quantities": 150,
                    "unit": null
                },
                {
                    "product_id": 1,
                    "product_name": "milk",
                    "price": 100,
                    "quantities": 18,
                    "unit": "bar"
                },
                {
                    "product_id": 4,
                    "product_name": null,
                    "price": 970,
                    "quantities": 100,
                    "unit": null
                }
            ]
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 6.3. Get a sell's information 

###### 6.3.1. Request

* Path: /api/v1/sells/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 6.3.2. Response

* On success:
```
{
    "success": true,
    "id": 1,
    "user_id": 3,
    "user_name": null,
    "customer_id": 3,
    "customer_name": null,
    "tax": 20.07,
    "active": true,
    "createdAt": "2018-12-04 13:22:38",
    "sell_items": [
        {
            "product_id": 4,
            "product_name": null,
            "price": 970,
            "quantities": 100,
            "unit": null
        },
        {
            "product_id": 3,
            "product_name": null,
            "price": 10,
            "quantities": 150,
            "unit": null
        },
        {
            "product_id": 1,
            "product_name": "milk",
            "price": 100,
            "quantities": 18,
            "unit": "bar"
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 6.4. Update a sell's information 

###### 6.4.1. Request

* Path: /api/v1/sells/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (this information will overwrite the original one)

```
{
    "user_id": 2,
    "customer_id": 1,
    "tax": 0,
    "active": true,
    "total": 3300,
    "sell_items": [
        {
            "product_id": 1,
            "supplier_id": 2,
            "price": 100,
            "quantities": 18
        },
        {
            "product_id": 3,
            "supplier_id": 10,
            "price": 10,
            "quantities": 150
        }
    ]
}
```

###### 6.4.2. Response

* On success:
```
{
    "success": true,
    "code": "update_sell_information_successful",
    "message": "update sell type information successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_sell_id",
    "message": "sell id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 6.6. Delete a sell

###### 6.6.1. Request

* Path: /api/v1/sells/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


###### 6.6.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_sell_successful",
    "message": "delete sell successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_sell_id",
    "message": "sell id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 7. Invoice management API
---

#### 7.1. Create new invoice

###### 7.1.1. Request

* Path: /api/v1/invoices
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:(name field is compulsory, other fields can be skipped)
```
{
    "amount": 1000,
    "purpose": "1",
    "description": "one"
}
```

###### 7.1.2. Response

* On success:
```
{
    "success": true,
    "id": 1
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```


#### 7.2. Search a invoice
###### 7.2.1. Request

* Path: /api/v1/search/invoices
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:

    * Search with date:
    ```
    {
        "draw": 1,
        "start": 0,
        "length": 5,
        "search": {
            "purpose": "",
            "description": "",
            "start": "2018-01-30 06:52:05",
            "end": "2018-12-30 06:52:05"
        }
    }
    ```
    * Search without date:
    ```
    {
        "draw": 1,
        "start": 0,
        "length": 5,
        "search": {
            "purpose": "",
            "description": "",
            "start": "2018-01-30 06:52:05",
            "end": "2018-12-30 06:52:05"
        }
    }
    ```

###### 7.2.2. Response

* On success:
```
{
    "success": true,
    "draw": 10,
    "recordsTotal": 1,
    "recordsFiltered": 1,
    "data": [
        {
            "id": 1,
            "amount": 10000.5,
            "purpose": "1",
            "description": "one",
            "createdAt": "2018-12-08 04:02:30"
        }
    ]
} ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 7.7. Get a invoice's information 

###### 7.7.1. Request

* Path: /api/v1/invoice/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

###### 7.7.2. Response

* On success:
```
{
    "success": true,
    "id": 1,
    "amount": 10000.5,
    "purpose": "1",
    "description": "one",
    "createdAt": "2018-12-08 04:02:30"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_invoice_id",
    "message": "invoice id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

#### 7.4. Update a invoice's information 

###### 7.4.1. Request

* Path: /api/v1/invoices/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (you can choose any fields you want to change, all fields are not compulsory)
```
{
    "amount": 10000.5,
    "purpose": "1",
    "description": "one"
}
```

###### 7.4.2. Response

* On success:
```
{
    "success": true,
    "code": "update_invoice_information_successful",
    "message": "update invoice information successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_invoice_id",
    "message": "invoice id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

#### 7.5. Delete a invoice

###### 7.1.1. Request

* Path: /api/v1/invoices/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


###### 7.1.2. Response

* On success:
```
{
    "success": true,
    "code": "delete_invoice_successful",
    "message": "delete invoice successful"
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (id does not exist):
```
{
    "success": false,
    "code": "wrong_invoice_id",
    "message": "invoice id " + {id} + " does not exist"
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 8. Report management API
---

#### 7.1. Report 1

###### 7.1.1. Request

* Path: /api/v1/reports/1
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:(name field is compulsory, other fields can be skipped)
```
{	
	"length": 2,
	"start": "2018-01-30 06:52:05",
    "end": "2018-12-30 06:52:05"
}
```

###### 7.1.2. Response

* On success:
```
{
    "success": true,
    "cost": 10215.5,
    "revenue": 3505,
    "products": [
        {
            "success": true,
            "id": 1,
            "name": "chocolate",
            "price": 80000,
            "unit": "cartoon",
            "barcode": "dccf2351",
            "quantities": 291,
            "sold_quantities": 223
        },
        {
            "success": true,
            "id": 3,
            "name": null,
            "price": null,
            "unit": null,
            "barcode": null,
            "quantities": null,
            "sold_quantities": 150
        }
    ]
}
```

* On fail (unauthorized):
```
{
    "success": false,
    "code": "unauthorized",
    "message": "You're not authorized to access this resource"
}
```

* On fail (one field is not in right format):
```
{
    "success": false,
    "code": "argument_not_valid",
    "message": {object_name} + {default_message} + "and" + ...
}
```

* On fail (you don't have role admin or manager):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```