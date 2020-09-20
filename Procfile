#web: java -jar target/LoanCalculator-1.0-SNAPSHOT.jar
#web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/*.jar 165000 18125 + = 3.5 15 450000
web: java $JAVA_OPTS -jar target/dependency/jetty-runner.jar --port $PORT target/*.war
#web: java -agentlib:jdwp=transport=dt_socket,server=y,address=9090,suspend=n $JAVA_OPTS -jar target/dependency/jetty-runner.jar target/*.war