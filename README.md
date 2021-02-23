# gaming-wallet

# Getting Started
    In order run the app you need to:
        1. start the Axon Server: 
             a.  docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
             or               
             b. download the jar from https://download.axoniq.io/axonserver/AxonServer.zip 
                and run it with  java -jar
        2. start the app with  mvn spring-boot:run from the root folder
    
# Testing 
   * Swagger interface: http://localhost:8080/swagger-ui.html
   * An additional create wallet action is implemented:  http://localhost:8080/wallet .
   * H2 Console: http://localhost:8080/h2-console/
   * Axon server can  also be checked: http://localhost:8024/
       
 # Frameworks
   * Spring boot
   * Axon Framework + Server
   * H2
   * Swagger
    
 # Choices
    I chose CQRS + Event Sourcing approach. For that I used SpringBoot, Axon Framework and Axon Server.
    CQRS + ES comes with some inherent advantages:
   * extremely get accurate audit logging for free
   * the ability to run temporal queries
   * it extremely easy to reconstruct the historical state of the object
   * we can clearly see the sequence of events occurring on the wallet account
   * Axon is a lightweight framework that helps in building microservices
    
    
 #  Command examples
   * Create wallet command:
       
    curl -X POST "http://localhost:8080/wallet" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"initialBalance\": 100, \"username\": \"Player1\"}"
    curl -X POST "http://localhost:8080/wallet" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"initialBalance\": 100, \"username\": \"Player2\"}"
  
   * Credit commands 
    
    curl -X PUT "http://localhost:8080/wallet/credit/yourWalletID" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 5, \"transactionId\": \"1\"}"
    curl -X PUT "http://localhost:8080/wallet/debit/yourWalletID" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 5, \"transactionId\": \"2\"}"
   
   * Query commands
    
    curl -X GET "http://localhost:8080/wallet/yourWalletID" -H "accept: */*"
    curl -X GET "http://localhost:8080/wallet/yourWalletID/events" -H "accept: */*"
    
 # What else needs to be done
   * Pagination
   * Logging
   * Security 
   * Containerization
   * Load testing 
   * The existing transaction check is a sensitive issue,as it will need another approach as the table grows. For example: implement
   a caching solution: https://hazelcast.com/use-cases/jcache-provider/
     Also the unique transaction id would be ideally dealt with in the calling service. 
   * More detailed error handling  
   * Split the Query and Command into 2 different microservices that can be scaled separately. 
    
 # Application flow
    @RestController
        => @Service
                => @Aggregate
                    => @CommandHandler
                            => @EventHandler
                                    => operations on @Aggregate 
                                    => *Projection classes => @Repository
