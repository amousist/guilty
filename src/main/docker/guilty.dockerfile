FROM hypriot/rpi-java

ADD maven/guilty.jar /guilty.jar
EXPOSE 8080

CMD ["java", "-jar", "/guilty.jar"] 