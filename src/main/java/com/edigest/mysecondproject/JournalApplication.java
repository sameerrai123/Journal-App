package com.edigest.mysecondproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
// last edited file me jane ke liye press     ctrl + alt + left arrow key

@SpringBootApplication
@EnableTransactionManagement
@Configuration
@EnableScheduling // tells spring that @scheduled methods are there like on userscheduler

//@EnableWebSecurity is always used with @configuration
//@configuration bean bannane ke liye use karte hai
// ye main class pe lagti hai and fonds whereever transactional anotation is used
//and then waha pe eak transactional context(container) baandega jaha pe uss method se related sare db ke operations honge and eak operation ke
// tarah ytreat hopga yani if one faills sare rolll back

// let if two api run ram and shaym then spring 2 trnsactional container karlega ek me user ram ka and dushre me user shayam ka operations dono isolated yani nalag alag chaleneg
// PlatformtransactionManager basically sare operation of commit or roll back ka  manage karega (sara kaam transaction ka yahi karega)
//and MongoTransactionmanager extends karta hai PlatformTransactionmanager ko

//MongoDatabaseFactory helps in building connection with database

//method- service class me transactional annotaion lagado bthen main aplication clkass me Enabletransactiionalmanagement
//annotation lagado and ek PlatformtransactiionalMangaer ka implementation kardo with help of mongodatabasefactory
//db factory by returning MongoTransactiuonMnager tobbuild connection with database and iss method @Bean banado and @configuration main pe add karna na bhule bcoz


//application proopertioes me kjo localhost and port wagera likhte hai uske wajah se MongoDatabasefactory  ka implementation automatically create hojata jhai

public class JournalApplication {

	public static void main(String[] args) {
		//if we want to check which profile is active then

		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		ConfigurableEnvironment env = context.getEnvironment();
		System.out.println(env.getActiveProfiles()[0]);  // profiles are array bcoz multipole profiles run karsakte hai so
	}

	@Bean
	public PlatformTransactionManager  transactionManager(MongoDatabaseFactory dbfactory) {
		return new MongoTransactionManager(dbfactory);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}



}





//log notess and logback.xml
//
//<configuration>
//    <!--  Appender and logger configuration go here -->
//    <!-- Appender ,means log ko kaha print karwana hai -->
//
//    <appender name = "myConsoleAppender" class = "ch.qos.logback.core.ConsoleAppender">
//        <!-- encoder means kis format yani pattern me log ko print karwana chahte hai -->
//        <encoder>
//            <pattern>
//                %d{yy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} ----- %msg %n
//		</pattern>
//        </encoder>
//    </appender>
//
//    <!--<appender name = "myFileAppender" class = "ch.qos.logback.core.FileAppender"> -->
//
//        <!--rolling of loggs yani rotation achieve kar sakte hai using filesize or other as fileappender is creating log in same file not creating new file  so
//can create file rotatioin to create new file based on new time  ,, and on place of file appender will use rolling.RollingFileAppender
//and will then define rolling policy means kis adhar pe hame logs ko roll karana hai like file size jayad ho , ya size and time ke basisi pe then ya koi or-->
//
//    <appender name = "myFileAppender" class = "ch.qos.logback.core.rolling.RollingFileAppender">
//        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
//            <!-- fileNamePattern → Log files will be created with names like journalApp-26-06-18_14-30.1.log, including date/time and an index number.
//            new log  files will be created every minute helps in deleting old file as all have differnt name due to time
//maxFileSize → When a log file reaches 10 MB, Logback creates a new log file (rollover).
//maxHistory → Logback keeps only the last 10 log files/periods and automatically deletes older logs to save disk space.-->
//            <fileNamePattern> journalApp-%d{yy-MM-dd_HH-mm}.%i.log</fileNamePattern>
//            <maxFileSize> 10MB </maxFileSize>
//            <maxHistory> 10 </maxHistory> <!-- setting maximum file no , if file 10 se jyafda hoti hai then delete old one -->
//        </rollingPolicy>
//        <file>
//          journalApp.log        <!-- file name here in which we want to display log , it will create a file of it including logs
//or else we can use file path whereever we want to save -->
//        </file>
//        <encoder>
//            <pattern>
//                %d{
//            </pattern>
//			</encoder>
//			</appender>
//
//			<!-- logger -->
//			<root level ="INFO">
//			<appender-ref ref = "myConsoleAppender" />   <!-- appender ref means kon kon se appender use karrahe yani kaha kaha log output karana hai -->
//			<appender-ref ref="myFileAppender" />
//			</root>
//
//			</configuration>


// /*localhost:9000 is your local SonarQube server, only running on your computer, not shared with others.
// You run mvn sonar:sonar to send your code to SonarQube via HTTP, not manually upload it.
// The Sonar Maven plugin scans your project and pushes results to the server automatically.
// You open localhost:9000 only to view analysis reports in the dashboard, not to run analysis.
// 		Even if many people use port 9000, each machine has its own separate SonarQube instance and data.*/



// /* mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=YOUR_TOKEN     // thios command send codes to sonaqube for anlysis and show its resyult there
// // disable ur test if failure shows then run code

// Yes, now that everything is configured correctly, your normal workflow will be:

// 1. Make code changes
// 2. Run tests
// mvn test

// or

// mvn clean verify

// Make sure all tests pass.

// 3. Send analysis to SonarQube
// mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=YOUR_TOKEN

// If you don't want to type the token every time, you can configure it in Maven settings or environment variables later.

// Recommended single command

// Many developers use:

// mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=YOUR_TOKEN

// This:

// Cleans the project
// Runs tests
// Builds the project
// Sends results to SonarQube

// all in one command.

// After pushing to GitHub

// A common workflow is:

// git add .
// git commit -m "Added new feature"
// git push origin main
// mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=YOUR_TOKEN

// or automate it later using GitHub Actions so SonarQube runs automatically whenever you push code.
//  */



// // how api of other website is automate and integrate

// /* Weather API Revision
// 1. Get API Key from Weather API provider
//         ↓
// 2. Create URL
//    API + City + API Key
//         ↓
// 3. Call API using RestTemplate
//         ↓
// 4. API returns JSON
//         ↓
// 5. Look at JSON and create POJO classes
//    (WeatherResponse, Current)
//         ↓
// 6. Use WeatherResponse.class
//    to convert JSON → Java Object
//         ↓
// 7. Access data
//    weatherResponse.getCurrent().getFeelslike()

// Remember:

// JSON response → Create POJO class.
// WeatherResponse.class tells Spring where to store JSON data.      // content of weather reponse.class is fetched fropm
// weather doc api url by passing or api key as access key then convert those json into pojo from any website then paste those pojo datas in this class run website in format by following instruction there like  https://api.weatherstack.com/current?access_key=6cced97ffb56ee6106afad2bf48ba07f&query=Mumbai
// ElevenLabs API Revision
// 1. Get API Key from ElevenLabs
//         ↓
// 2. Read API Documentation
//         ↓
// 3. API URL is fixed
//    (Text-to-Speech endpoint)
//         ↓
// 4. Put API Key in Header(instruction guven in doc )
//    xi-api-key = yourKey
//         ↓
// 5. Create request body
//    { "text": journalText }  // this the entry we want to automate pass by requestbody by converting in httpentity
//         ↓
// 6. Send POST request
//         ↓
// 7. API returns MP3 audio file
//         ↓
// 8. Use byte[].class
//         ↓
// 9. byte[] contains audio file data
//         ↓
// 10. Save as audio.mp3
// Key Differences
// Weather API	ElevenLabs API
// Returns JSON	Returns MP3 file
// Create POJO classes	No POJO needed
// Use WeatherResponse.class	Use byte[].class
// API key in URL (usually)	API key in Header
// Get weather data	Get audio file
// Quick Interview Answer

// What is Header?

// Extra information sent with the request, like API key.

// What is MediaType.APPLICATION_JSON?

// Tells the API that we are sending JSON data.

// What is WeatherResponse.class?

// Converts JSON response into a Java object.

// What is byte[].class?

// Receives raw file data (MP3, PDF, image, etc.).

// Why no class for ElevenLabs?

// Because it returns an audio file, not JSON.

// Where do we know API key location?  // api documentation tells how to use api key and where use and to create api url

// From the API documentation.

// How is text dynamic?

// User's journal text is inserted into the request body before calling the API.*/




// /*
// dto use (data transfer object)

// DTO vs Entity (Revision)
// What is an Entity?
// Represents a database table.
// Used by JPA/Hibernate.
// Saved and fetched from the database.

// Example:

// @Entity
// public class User {
//     private Long id;
//     private String username;
//     private String email;
//     private String password;
// }
// What is a DTO?

// DTO = Data Transfer Object

// Used to transfer data between the client and the server.
// Controls what data is accepted and returned.
// Does not represent a database table.

// Example:

// public class UserResponse {
//     private Long id;
//     private String username;
//     private String email;
// }
// Why use DTOs?
// 1. Security

// Don't expose sensitive fields.

// ❌ Entity

// {
//     "id":1,
//     "username":"john",
//     "password":"$2a$10..."
// }

// ✅ DTO

// {
//     "id":1,
//     "username":"john"
// }
// 2. Validation

// DTOs hold validation annotations.

// @NotBlank
// @Email
// private String email;
// 3. Control API

// Expose only the fields you want clients to see.

// 4. Database Independence

// If the entity changes, the API doesn't have to change.

// Why does Request DTO contain password?

// Because the client must send it.

// Example request:

// {
//     "username":"john",
//     "password":"Password@123"
// }

// Password is needed to create the user.

// Why should Response DTO NOT contain password?

// Even encrypted passwords should never be sent back.

// Instead return

// {
//     "id":1,
//     "username":"john",
//     "email":"john@gmail.com"
// }
// Why different DTOs?

// Each API needs different data.

// CreateUserRequest
//     ↓
// contains password

// LoginRequest
//     ↓
// contains password

// UpdateUserRequest
//     ↓
// contains editable fields

// UserResponse
//     ↓
// does NOT contain password
// Flow while Creating User
// Client JSON
//       │
//       ▼
// CreateUserRequest DTO
//       │
//       ▼
// Controller
//       │
//       ▼
// Service
//       │
//       ▼
// DTO → Entity
//       │
//       ▼
// Repository
//       │
//       ▼
// Database
//       │
//       ▼
// Saved Entity
//       │
//       ▼
// Entity → UserResponse DTO
//       │
//       ▼
// Client
// Flow while Fetching User
// Database
//       │
//       ▼
// Repository
//       │
//       ▼
// Entity
//       │
//       ▼
// Service
//       │
//       ▼
// Entity → UserResponse DTO
//       │
//       ▼
// Controller
//       │
//       ▼
// JSON Response
// How does Service convert DTO ↔ Entity?
// Manual Mapping
// User user = new User();

// user.setUsername(dto.getUsername());
// user.setEmail(dto.getEmail());
// user.setPassword(passwordEncoder.encode(dto.getPassword()));

// Reverse:

// UserResponse response = new UserResponse();

// response.setId(user.getId());
// response.setUsername(user.getUsername());
// response.setEmail(user.getEmail());
// Automatic Mapping

// Use

// ✅ MapStruct (recommended)
// ✅ ModelMapper

// Example

// User user = mapper.toEntity(dto);

// UserResponse response = mapper.toResponse(user);
// Why create a Response DTO after saving?

// After saving, the database generates values like

// id
// createdAt
// updatedAt

// So we return

// {
//     "id":1,
//     "username":"john",
//     "email":"john@gmail.com"
// }

// instead of

// {
//     "password":"Password@123"
// }

// The client gets confirmation of what was created.

// Can we return nothing?

// Yes.

// return ResponseEntity.status(HttpStatus.CREATED).build();

// But most REST APIs return the created resource.

// Common DTOs in a Project
// CreateUserRequest
//     ↓
// Used while registering

// LoginRequest
//     ↓
// Used while logging in

// UpdateUserRequest
//     ↓
// Used while updating profile

// ChangePasswordRequest
//     ↓
// Used while changing password

// UserResponse
//     ↓
// Used while returning user details
// One-line Difference
// Entity	DTO
// Represents database table	Represents API data
// Used by Repository	Used by Controller
// Contains all fields	Contains only required fields
// Can have password	Response DTO should not have password
// Persistent object	Data transfer object
// Golden Rule (Remember This)

// Controller talks to DTOs. Repository talks to Entities. Service sits in the middle and converts between them.

// Controller ⇄ DTO ⇄ Service ⇄ Entity ⇄ Repository ⇄ Database
//  */


// /*
// some important points

// 1. Service Interface = Contract
// public interface UserService {
//     UserDTO createUser(UserDTO userDTO);
//     void verifyEmail(String token);
// }
// Only declares what methods should exist.
// No business logic.
// Like a blueprint.
// 2. Service Implementation = Actual Logic
// @Service
// public class UserServiceImpl implements UserService {

// This class writes the actual code.

// Example:

// @Override
// public UserDTO createUser(UserDTO dto) {
//     User user = userMapper.toEntity(dto);
//     userRepository.save(user);
//     return userMapper.toDTO(user);
// }

// So,

// Interface = What to do

// Implementation = How to do it

// 3. Controller Uses Interface
// private final UserService userService;

// The controller depends on the interface, not the implementation.

// Spring automatically injects:

// UserService
//       ↓
// UserServiceImpl

// This is called Dependency Injection.

// 4. Repository
// public interface UserRepository extends BaseRepository<User>

// BaseRepository eventually extends MongoRepository.

// So you automatically get:

// save()
// findById()
// findAll()
// deleteById()
// existsById()
// count()

// You never implement these.

// 5. Custom Methods

// These are not inside MongoRepository:

// findByUsername()

// findByEmail()

// existsByUsername()

// existsByEmail()

// You only declare them:

// Optional<User> findByUsername(String username);

// You never write their implementation.

// 6. Who Implements Them?

// Spring Data MongoDB.

// It reads the method name:

// findByUsername(String username)

// and automatically creates a MongoDB query like:

// db.users.find({
//    username: "sameer"
// })

// No implementation needed.

// Complete Flow
// HTTP Request
//       ↓
// Controller
//       ↓
// UserService (Interface)
//       ↓
// UserServiceImpl (Business Logic)
//       ↓
// UserRepository
//       ↓
// MongoRepository
//       ↓
// MongoDB
// One Line to Remember

// Controller calls the Service Interface, Spring injects the Service Implementation, the Service uses the Repository,
//  and Spring Data MongoDB automatically implements repository query methods based on their names. 🚀
//  */


// /*
// @value use
// In Spring Boot, the @Value annotation is used to inject values into fields, constructor parameters, or method parameters. These values usually come from configuration files like application.properties or application.yml, environment variables, or system properties.

// Why use @Value?

// It lets you avoid hardcoding configuration values in your code. Instead, you keep them in configuration files, making the application easier to configure for different environments (development, testing, production).

// Example hiding api key in .yml file while uploading to github and inject value of api key using value annotation in api key
//  */

/* debug
1. First you set breakpoint
Click on left side of line in IntelliJ
Red dot appears ⛔
This tells: “stop here when code runs”
2. Then click Debug (🐞)
Spring Boot starts in debug mode
It waits for requests
3. Then send request (Postman/browser)
API gets triggered
Code reaches breakpoint
Execution pauses there

 Why we “stop” at a breakpoint

When your Spring Boot app runs, everything happens very fast. Without stopping, you cannot see:

What values variables have
Whether data from Postman is correct
Where exactly error is happening
How code is flowing step by step
 Simple idea

 Breakpoint = “freeze the program here for inspection”
 */

/* @postconstruct    == mainly jab bean banne ke just baad koi kaam karna ho to use postconstruct annot
🧠 @PostConstruct in Spring Boot (simple 5–6 lines)
@PostConstruct is used to run a method after Spring creates the bean (object).
It runs only once automatically when the application starts.
It is used for initial setup tasks like loading data or checking connections.
It runs after dependency injection is completed (after @Autowired objects are set).
You don’t need to call it manually — Spring calls it automatically.
Example: initializing cache, loading config, or starting background setup.

👉 Simple meaning:
“Run this method once after object is created and ready.”
 */

/*
@Autowired works only on Spring-managed beans.

✅ If a class is annotated with @Component, @Service, @Repository, @Controller, or is created with @Bean,
then you can use @Autowired inside that class.
❌ If the class is a normal Java class (created with new), @Autowired will not work.

Example:

@Service
public class UserService {

    @Autowired
    private UserRepository repo;   // ✅ Works
}
public class Test {

    @Autowired
    private UserRepository repo;   // ❌ Doesn't work (not a Spring bean)
}

So, the class must be a Spring bean—not the individual method. Once the class is managed by Spring,
you can use @Autowired on fields, constructors, or setter methods inside it.
Constructor injection is generally the recommended approach.
 */



/*
Redis Cloud is used in industry-level applications to handle millions of requests by caching frequently accessed
 data in memory. It reduces database load, improves response time, and provides high availability through replication
 and automatic failover if a Redis server fails. However,
the primary backup and permanent storage of data are still handled by databases like MySQL or PostgreSQL.

Redis Cloud performs the same caching operations as Redis, but it is a fully managed cloud service designed for
 industry-level applications. It helps handle millions of requests with high availability, scalability, and
 minimal maintenance.
 */



/*
AWS provides the cloud infrastructure to host and run your Spring Boot application, databases, storage,
 networking, and other services.

 AWS owns the servers, networking, storage, and data centers.
Redis provides and manages the Redis software running on those servers.

So:

AWS provides the infrastructure (hardware + cloud platform).
Redis Cloud provides the managed Redis service on top of that infrastructure.

AWS/Azure provide the cloud infrastructure (servers, networking, storage) to host Spring Boot applications and other services.
Redis Cloud is a managed Redis service that runs on AWS, Azure, or Google Cloud infrastructure., rdis cloud need cloud unfra to hosted on that aws provide
The Redis team manages the Redis servers, scaling, maintenance, monitoring, backups, and high availability.
Your Spring Boot application connects to Redis Cloud to cache frequently accessed data, improving performance
 and handling millions of requests efficiently.

 Why AWS instead of your own server?
24/7 availability – Your application stays online all the time.
Scalability – Easily handle thousands or millions of users.
Reliability – If one server fails, AWS can provide replacements or failover options.
Security – Built-in networking, firewalls, IAM, and encryption.
No hardware maintenance – You don't buy or maintain physical servers.

So, you absolutely can use your own database, but for large-scale production systems, cloud platforms like AWS
or Azure make running and scaling the application much easier and more reliable.
 */