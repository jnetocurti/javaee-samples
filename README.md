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

3. `./bin/jboss-cli.sh --connect` to launch the command line interface.

4. On Wildfly CLI `/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql, driver-module-name=org.postgresql, driver-class-name=org.postgresql.Driver)` and `/subsystem=datasources/data-source=javaeesamplesDS:add(jndi-name=java:/javaeesamplesDS, driver-name=postgresql, user-name=javaeesamples, password=javaeesamples, connection-url=jdbc:postgresql://postgres:5432/javaeesamples)` to create jdbc driver and data source.

#### Building, testing and installing the modules

`mvn clean install`

#### Deployment jax-rs-sample application

`mvn wildfly:deploy`


