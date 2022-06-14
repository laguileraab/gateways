<div align="center" id="top"> 
  <a href="" rel="noopener">

 <img width=200px height=200px src="https://i.imgur.com/6wj0hh6.jpg" alt="Api"></a>
  &#xa0;
</div>

<h1 align="center">Gateway Project</h1>

<p align="center">

  <img alt="Github top language" src="https://img.shields.io/github/languages/top/laguileraab/gateways?color=56BEB8">

  <img alt="Github language count" src="https://img.shields.io/github/languages/count/laguileraab/gateways?color=56BEB8">

  <img alt="Repository size" src="https://img.shields.io/github/repo-size/laguileraab/gateways?color=56BEB8">

</p>
<p align="center">
  <a href="#dart-about">About</a> &#xa0; | &#xa0;
    <a href="#white_check_mark-requirements-for-compilation">Requirements</a> &#xa0; | &#xa0;
    <a href="#rocket-technologies">Technologies</a> &#xa0; | &#xa0;
  <a href="#checkered_flag-starting">Starting</a> &#xa0; | &#xa0;
  <a href="#sparkles-usage-of-the-app">Usage</a> &#xa0; | &#xa0;
    <a href="#test_tube-perform-tests">Tests</a> &#xa0; | &#xa0;
  <a href="#heavy_check_mark-run-with-docker">Docker</a> &#xa0; | &#xa0;
  <a href="https://msoftgateways.herokuapp.com/swagger-ui.html">Heroku</a> &#xa0; | &#xa0;
  <a href="https://github.com/laguileraab" target="_blank">Author</a>
</p>

<br>

## :dart: About ##

In this project i made a REST API that allow you to store information about gateways and peripherals.

## :white_check_mark: Requirements for compilation <a name = "requirement"></a>

You need to have Java >= 8 and Maven installed in order to run this project. Check if you have install it already with:

Java
```bash
java --version
```

Maven
```bash
mvn --version
```

## :rocket: Technologies <a name = "tech"></a>

The following tools were used in this project:

- [Java](https://www.java.com/)
- [Spring](https://spring.io/)
- [H2](https://www.h2database.com/)
- [MySQL](https://www.mysql.com/)


## :checkered_flag: Starting ## <a name = "starting"></a>
We need to install maven dependencies.

```bash
# Clone this project
$ git clone https://github.com/laguileraab/gateways

# Access
$ cd gateways

# Install dependencies and skip tests
$ mvn clean install

# Run the project
$ java -jar .\target\gateways-0.0.1-SNAPSHOT.jar

# The app server will initialize in the <http://127.0.0.1>

```


## :test_tube: Perform tests
For launching tests use:

```bash
$ mvn clean integration-test verify
```

After that you can go to Jacoco site for review the Code Coverage of the tests inside the project.

```bash
./site/index.html
```

## :sparkles: Usage of the app <a name = "usage"></a>

In the project is been added Swagger 3 for documentation of the API, this allows also to perform test in the web application and an understanding of the required objects for every endpoint.

```bash
#Swagger 3
http://127.0.0.1/swagger-ui.html
```

Also been added Actuator for checking the status of the web service, useful for containers.

```bash
http://127.0.0.1/actuator #endpoints
http://127.0.0.1/actuator/health/ #component's status, ej: database
http://127.0.0.1/actuator/loggers/ #loggers
http://127.0.0.1/actuator/caches/ #elements in cache
```

## :heavy_check_mark: Run with Docker <a name = "docker"></a>

In the project is a Dockerfile to dockerize the Spring Boot application and a Docker Composer file v3 for Docker deployment. You can be setup in one line:

```bash
docker-compose up
```

And you're good to go. See here:

```bash
http://localhost/actuator/health
```

and everything must be UP.

:triangular_flag_on_post: Important!

Also this project is currently deployed on [Heroku](https://msoftgateways.herokuapp.com/swagger-ui.html).


## ✍️ Author <a name = "author"></a>

- [@laguileraab](https://github.com/laguileraab)

&#xa0;

<a href="#top">Back to top</a>