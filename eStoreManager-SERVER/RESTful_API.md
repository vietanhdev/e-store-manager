
# 1. Employee management API
---

## 1.1. Login into store

### 1.1.1. Request

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

### 1.1.2. Response

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

## 1.2. Get your own information

### 1.2.1. Request

* Path: api/v1/me
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 1.2.2. Response

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

## 1.3. Update your own information

### 1.3.1. Request

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

### 1.3.2. Response

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

## 1.4. Change your own password

### 1.4.1. Request

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

### 1.4.2. Response

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

## 1.5. Delete your own account

### 1.5.1. Request

* Path: /api/v1/me
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 1.5.2. Response

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

## 1.6. Create new account

### 1.6.1. Request

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
    "password": "md5@password",
    "salary": 1000,
    "email": "ceo@gmail.com",
    "address": "hoa binh",
    "mobileNo": "0916167558",
    "roles": [
        "ROLE_ADMIN", "ROLE_MANAGER"
    ]
}
```

### 1.6.2. Response

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

## 1.7. Get other user's information

### 1.7.1. Request

* Path: /api/v1/users/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 1.7.2. Response

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

## 1.8. Update other user's information

### 1.8.1. Request

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

### 1.8.2. Response

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

## 1.9. Get all users' information

### 1.9.1. Request

* Path: /api/v1/users?page=0&size=3
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 1.9.2. Response

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

## 1.10. Delete other users's account

### 1.10.1. Request

* Path: /api/v1/users/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 1.10.2. Response

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

# 2. Customer management API
---

## 2.1. Create new customer

### 2.1.1. Request

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

### 2.1.2. Response

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

## 2.2. Get all customer summary information

### 2.2.1. Request

* Path: /api/v1/customers?page=0&size=3
* Method: GET
* Header:
    
    * Content-type: 
    * Authorization: Bearer JWT

* Body:

### 2.2.2. Response

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

## 2.3. Get a customer's information 

### 2.3.1. Request

* Path: /api/v1/customer/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 2.3.2. Response

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

## 2.4. Update a customer's information 

### 2.4.1. Request

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

### 2.4.2. Response

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

## 2.5. Delete a customer

### 2.1.1. Request

* Path: /api/v1/customers/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


### 2.1.2. Response

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

## 2.6. Search a customer
### 2.6.1. Request

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

### 2.6.2. Response

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

# 3. Supplier management API
---

## 3.1. Create new supplier

### 3.1.1. Request

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

### 3.1.2. Response

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


## 3.2. Search a supplier
### 3.2.1. Request

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

### 3.2.2. Response

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

## 3.3. Get a supplier's information 

### 3.3.1. Request

* Path: /api/v1/supplier/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 3.3.2. Response

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

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 3.4. Update a supplier's information 

### 3.4.1. Request

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

### 3.4.2. Response

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

## 3.5. Delete a supplier

### 3.1.1. Request

* Path: /api/v1/suppliers/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


### 3.1.2. Response

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

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

# 4. Product management API
---

## 4.1. Create new product

### 4.1.1. Request

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

### 4.1.2. Response

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


## 4.2. Search products
### 4.2.1. Request

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

### 4.2.2. Response

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

## 4.3. Get a product's information 

### 4.3.1. Request

* Path: /api/v1/product/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 4.3.2. Response

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

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 4.4. Update a product's information 

### 4.4.1. Request

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

### 4.4.2. Response

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

## 4.5. Delete a product

### 4.1.1. Request

* Path: /api/v1/product/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


### 4.1.2. Response

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

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

# 5. Buy management API
---

## 5.1. Create new buy

### 5.1.1. Request

* Path: /api/v1/buys
* Method: POST
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body:(name field is compulsory, other fields can be skipped)
```
{
	"user_id": 1,
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

### 5.1.2. Response

* On success:
```
{
    "success": true,
    "id": 10
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

* On fail (product id does not exist):
```
{
    "success": false,
    "code": "wrong_product_id",
    "message": "product id " + {id} + " does not exist"
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


## 5.2. Search buys
### 5.2.1. Request

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



### 5.2.2. Response

* On success:
```
{
    "success": true,
    "draw": 10,
    "recordsTotal": 4,
    "recordsFiltered": 4,
    "data": [
        {
            "id": 10,
            "user_id": 1
        },
        {
            "id": 8,
            "user_id": 1
        },
        {
            "id": 9,
            "user_id": 1
        },
        {
            "id": 7,
            "user_id": 1
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

## 5.3. Get a buy's information 

### 5.3.1. Request

* Path: /api/v1/buys/{id}
* Method: GET
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:

### 5.3.2. Response

* On success:
```
{
    "success": true,
    "id": 9,
    "user_id": 1,
    "buy_items": [
        {
            "product_id": 1,
            "supplier_id": 1,
            "price": 590,
            "quantities": 1
        },
        {
            "product_id": 3,
            "supplier_id": 2,
            "price": 100,
            "quantities": 10
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

## 5.4. Update a buy's information 

### 5.4.1. Request

* Path: /api/v1/buys/{id}
* Method: PUT
* Header:
    
    * Content-type: application/json
    * Authorization: Bearer JWT

* Body: (this information will overwrite the original one)

```
{
    "user_id": 2,
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

### 5.4.2. Response

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

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```

## 5.5. Delete a buy

### 5.1.1. Request

* Path: /api/v1/buys/{id}
* Method: DELETE
* Header:
    
    * Content-type:
    * Authorization: Bearer JWT

* Body:


### 5.1.2. Response

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

* On fail (you don't have role admin or cashier):
```
{
    "success": false,
    "code": "access_denied",
    "message": "You don't have permission to access this resource"
}
```
