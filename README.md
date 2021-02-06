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
   * An additional http://localhost:8080/wallet action was created. It creates a new wallet account.
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
    
 #  Command examples:
   * Create wallet command:
       
    curl -X POST "http://localhost:8080/wallet" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"initialBalance\": 100, \"username\": \"Player1\"}"
    curl -X POST "http://localhost:8080/wallet" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"initialBalance\": 100, \"username\": \"Player2\"}"
  
   * Credit commands 
    
    curl -X PUT "http://localhost:8080/wallet/credit/yourWalletID/yourTransactionId" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 110}"
    curl -X PUT "http://localhost:8080/wallet/debit/yourWalletID/yourTransactionId" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 50}"
   
   * Query commands
    
    curl -X GET "http://localhost:8080/wallet/yourWalletID" -H "accept: */*"
    curl -X GET "http://localhost:8080/wallet/yourWalletID/events" -H "accept: */*"
    
 # What else needs to be done:
   * Logging
   * Security 
   * Containerization
   * Load testing 
   * The existing transaction check is a sensitive issue at it will need another approach as the table grows. For example, implement
   a caching solution: https://hazelcast.com/use-cases/jcache-provider/
   * More detailed error handling  
    
 # Application flow
    @RestController
        => @Service
                => @Aggregate
                    => @CommandHandler
                            => @EventHandler
                                    => operations on @Aggregate 
                                    => *Projection => @Repository