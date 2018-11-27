# BUILD AND RUN INSTRUCTION:

## Build Spring Boot Apps with VS Code

Visual Studio Code is an ideal lightweight development environment for Spring Boot developers and there are several useful VS Code extensions.

1. Prerequisites:

    * Java Development Kit (JDK), version 1.8
    * Apache Maven, version 3.0 or later
    * [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-spring-boot) on Visual Studio Code (VSC)

2. Run project on VSC by starting debug (or press F5). This project use outside mysql-server, so you need internet connection.

3. [Scratch Design for client-server APIs](https://hackmd.io/hTQZJTXPSNaHzta9tsb5Hg?fbclid=IwAR1W9m5mHEY5KzgvCOI3qRO4TDf2EDcEdXyb0alMzJe4PUm76tYwLPdvMOQ)

4. You can use these accounts for logging:

| username | password |
| --- | --- |
| admin | admin@123 |
| manager | manager@123 |
| cashier | cashier@123 |

## Build and run via commandline - Ubuntu

### 1. Install maven

~~~
sudo apt install maven
~~~

### 2. Open Terminal from project folder then:

~~~
mvn install
~~~

### 3. Run it:

~~~
mvn spring-boot:run
~~~
