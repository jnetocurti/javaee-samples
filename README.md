# javaee-samples

Samples of Java EE Applications

## Getting Started

- [Git](https://git-scm.com/)
- [JDK 8](http://www.oracle.com)
- [Apache Maven](https://maven.apache.org/)
- [Docker and Docker Compose](https://www.docker.com/)

### Starting the environment

1. `cd ./src/site/environment` to enter into the directory of the docker-compose.yml file.

2. Run `docker-compose up` to start Wildfly server.

#### Building, testing and installing the modules

`mvn clean install`

#### Deployment jax-rs-sample application

`mvn wildfly:deploy`


