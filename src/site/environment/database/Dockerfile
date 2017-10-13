FROM postgres:9.4

ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_PASSWORD postgres

RUN localedef -i pt_BR -c -f UTF-8 -A /usr/share/locale/locale.alias pt_BR.utf8
ENV LANG pt_BR.utf8

ADD init-user-db.sh /docker-entrypoint-initdb.d/init-user-db.sh
