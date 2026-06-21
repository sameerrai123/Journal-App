package com.edigest.mysecondproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Configuration

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
//journalApp.log        <!-- file name here in which we want to display log , it will create a file of it including logs
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


/*localhost:9000 is your local SonarQube server, only running on your computer, not shared with others.
You run mvn sonar:sonar to send your code to SonarQube via HTTP, not manually upload it.
The Sonar Maven plugin scans your project and pushes results to the server automatically.
You open localhost:9000 only to view analysis reports in the dashboard, not to run analysis.
		Even if many people use port 9000, each machine has its own separate SonarQube instance and data.*/