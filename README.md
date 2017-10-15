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

4. `./bin/jboss-cli.sh --connect` to launch the Wildfly command line interface.

5. Add JDBC Driver: `/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql, driver-module-name=org.postgresql, driver-class-name=org.postgresql.Driver)`

6. Creating dev datasource: `/subsystem=datasources/data-source=javaeesamplesDS:add(jndi-name=java:/javaeesamplesDS, driver-name=postgresql, user-name=javaeesamples, password=@jeedeve17, connection-url=jdbc:postgresql://postgres:5432/javaeesamples)`

7. Creating test datasource: `/subsystem=datasources/data-source=javaeesamplestestDS:add(jndi-name=java:/javaeesamplestestDS, driver-name=postgresql, user-name=javaeesamplestest, password=@jeetest17, connection-url=jdbc:postgresql://postgres:5432/javaeesamplestest)`

#### Building, testing and installing the modules

`mvn clean install`

#### Deployment jax-rs-sample application

`mvn wildfly:deploy`


