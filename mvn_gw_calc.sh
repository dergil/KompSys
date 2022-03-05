cd "$(pwd)/Calculator"
mvn clean install -DskipTests=true

cd "$(pwd)/Gateway"
mvn clean install -DskipTests=true

cd "$(pwd)/KompSys"
mvn clean install -DskipTests=true
