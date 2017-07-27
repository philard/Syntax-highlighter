Using JaCoCo maven plugin for install time test coverage checks and reports. Unit tests using Mokito. Checked with IDEA's "QAPlug" using FindBugs, Checkstyle & PMD.

**To test**

mvn install

open ./target/site/jacoco/index.html

**To run**

mvn clean install

java -jar ./target/syntax-highlighter-0.0.1-SNAPSHOT.jar

