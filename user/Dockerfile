FROM openjdk:11-jre-slim
LABEL authors="cristobal.infantes@gmail.com"
EXPOSE 8080

ENV TZ=Europe/Madrid
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /usr/agiletv
RUN groupadd -r agiletv && useradd -r -s /bin/false -g agiletv agiletv

COPY --chown=agiletv:agiletv target/user-0.0.1-SNAPSHOT.jar .

USER agiletv
ENTRYPOINT java -jar user-0.0.1-SNAPSHOT.jar