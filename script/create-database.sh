java -jar liquibase/lib/liquibase-core-3.5.3.jar \
    --changeLogFile="liquibase/changeLog-master.xml" \
    --username="fabrick_demo" \
    --password="fabrick_demo" \
    --url="jdbc:mysql://127.0.0.1:3306/fabrick_demo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" \
    --classpath="liquibase/lib/mysql-connector-java-5.1.48.jar" \
    --driver="com.mysql.jdbc.Driver" \
    --logLevel="warning" \
    update