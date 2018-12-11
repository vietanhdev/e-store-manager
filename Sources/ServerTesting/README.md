# eStoreManager - API Testing

This is the Testing Project for server APIs.

- The sever to test is put in server_for_testing/store-0.0.1-SNAPSHOT-testdb.jar
- The database for testing is put in server_for_testing/eStoreManagerTesting.sql

## PreSetup

- Install JRE.
- Deploy the database to mysql or mariadb with the following information:
    + Db name: eStoreManagerTesting
    + Username: springuser
    + Password: ThePassword@123

## Run Test:
~~~
npm i
npm test
~~~
