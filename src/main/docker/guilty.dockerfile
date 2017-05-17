FROM resin/raspberry-pi3-openjdk

ADD maven/guilty.jar /guilty.jar
EXPOSE 8080

CMD ["java", "-jar", "/guilty.jar"] 