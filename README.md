# javaee-samples

Samples of Java EE Applications

## Prerequisites

- [Git](https://git-scm.com/)
- [JDK 8](http://www.oracle.com)
- [Apache Maven](https://maven.apache.org/)
- [Docker and Docker Compose](https://www.docker.com/)

### Starting the environment

1. `cd ./src/site/environment` to enter into the docker-compose.yml file directory.

2. Run `docker-compose up` to start Wildfly server and PostgreSQL database.

3. Connect in Wildfly server container with `docker exec -it javaee-samples-server /bin/bash` to do the required configurations.

4. `./bin/jboss-cli.sh --file=./standalone/tmp/commands.cli` to configure JDBC Drivers, Datasources, JMS and Mail.

#### Building, testing and installing the modules

`mvn clean install`

#### Deployment of applications in javaee-samples-ear

`mvn wildfly:deploy`


