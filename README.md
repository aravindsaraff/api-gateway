Gateway (Facade/Proxy) API Service Sample

Technologies used:
* Java 8
* Cryptography with JASYPT/BouncyCastle
  * Requires strong JCE libraries- local_policy.jar, US_export_policy.jar to be downloaded from oracle.com
  * (Re)place the libraries in <JRE_INSTALL>/lib/security by above
  * Ex. For Mac: The location could be: /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/security
* Spring Boot 2.x
* Swagger
* Integration Test using RestAssured


How to Build: <br/>
* mvn clean install
* The above builds the application **Jar** with embedded Tomcat

How to Run: <br/>

* Intellij
  * Add a <b>Spring Boot configuration </b> with <i>GatewayServiceApplication</i> as the main class
* bash-console 
  * java -jar api-portal-service.jar
  
* The application should start up at default 8080 port

* Then start the swagger at
  * <p>http://localhost:8080/swagger-ui.html</p>
  * Then click on the **gateway-controller** link
    * Go to the API **/v1/story**
    * Hit **Try-It-Out** button. It should reveal the **Execute** button
    * Click on the execute and you should see the Results/Response body
    
* Alternatively, you can also run the integration test
 