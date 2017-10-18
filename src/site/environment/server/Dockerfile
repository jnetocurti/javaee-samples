FROM jboss/wildfly:10.1.0.Final

WORKDIR /opt/jboss/wildfly

COPY ./modules ./modules/system/layers/base/org

COPY ./scripts ./standalone/tmp

RUN ./bin/add-user.sh admin admin --silent

# TODO -> execute ./standalone/tmp/commands.cli via jboss-cli.sh

CMD ["./bin/standalone.sh", "--server-config", "standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
