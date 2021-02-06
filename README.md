# gaming-wallet

# Getting Started
    In order run the app you need to:
        1. start the Axon Server: 
             a.  docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
             or               
             b. by downloading the jar https://download.axoniq.io/axonserver/AxonServer.zip and running it with java -jar
        2. start the app with  mvn spring-boot:run from the roor folder
    
# Testing 
   * You got a Swagger-ui interface : http://localhost:8080/swagger-ui.html
   * You got H2 Console: http://localhost:8080/h2-console/
   * The Axon server can  also be checked: http://localhost:8024/    
 # Frameworks
   * Spring boot
   * Axon Framework
   * H2
   * Swagger
    
 # Choices
    I chose to go with CQRS and ES and chose for that Axon Framework and Axon Server
    CQRS + ES comes with some inherent advantages:
   * extremely get accurate audit logging for free
   * the ability to run temporal queries
   * it extremely easy to reconstruct the historical state of the object
   * we can clearly see the sequence of events occurring on the wallet account
   * Axon is a lightweight framework that helps in building microservices
   * 
    
    
    
 
    
 