FROM jboss/wildfly:10.1.0.Final

WORKDIR /opt/jboss/wildfly

COPY ./modules ./modules/system/layers/base/org

RUN ./bin/add-user.sh admin admin --silent

# TODO -> execute ./scripts/datasource.cli via jboss-cli.sh

CMD ["./bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
