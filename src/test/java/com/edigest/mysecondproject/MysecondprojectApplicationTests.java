package com.edigest.mysecondproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest   // comntext wagera load hoga , mongo se connection build hoga it meaans
	/* like jis folder ke class ke method ko  test karna hai use ham same structure me test folder me create karlenge
	  like we have to test method of userservice which is inside service folder
	  so we will create a service folder package inside test section tpoo and that service folder i will create userservice class same as structured in main vice versa*/
	//yani jis section me folder sturcture hai same test me create karenge so that malum pade ki kiss class ka test karahe
class MysecondprojectApplicationTests {

	@Test
	void contextLoads() {

	}

	/*
	Logging is an essential aspect of appln development that allows developers to montior and troubleshoot their application.
	Spring Boot supports various logging frameworks , such as logback , log4j2 , Java Util Logging(JUL)

	Logback :  It is default logging framework in spring
	Log4j2 : it is for advanced logging framework
	JUL : it is already inside java , it not depend on spring but lack advanced features , iot is part of jdk
	generally we use logback everywhere

	Spring boot comes with a default logging configuration that uses Logback as default logging im[lementation. It provides
	good balance btw simplicity and flexibility.

	The default logging configuration is embedded within the spring Boot ibraries , and it may not be visible in ur project source code.

	If u want to customize the logging configuration , you can create own logback-spring.xml or logback.xml file in
	the src/main/resource directory. When spring Boot detects the file in your project , it will use it instead of the
	defualt configuration.

	Logging levels help in categorizing log statements based on their severity. The common logging levels are
	                  TRACE
	                  DEBUG
	                  INFO
	                  WARN
	                  ERROR

	  We can set desired logging levels for specific packages or classes , allowing them to control the amount of
	  information logged at runtime.

	  By default, logging is enabled for INFO , WARN , ERROR  but for TRACE , DEBUG U HAVE TO DOcustomization
	  if u set one log level then log having more severity than that level will enable also
	  e.g. u set debug log then warn , info , run will also enable
	  if u set trace then all will enable

	  Spring Boot provides annotations like @Slf4j and @Log4j2 that u can use to automatically inject logger instances
	   into your classes

	   Spring Boot allows us to configure logging using logback.xml or properties or YAML files like enabling trace or debug
	   ** generally use logback.xml karte hai bcoz ye specifically log ke liye hojayegi handle karega

	   logback.xml

	   The <configuration> element is the root element of logback.xml file.All logback configuration is enclosed within
	   this element.
	 */

}
