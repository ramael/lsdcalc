package org.aittam.lsdcalc;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LsdCalcApplicationTests {

    /*
    
        Spring interprets the @Autowired annotations, and the beans are injected before the test methods are run.
    
        *** SPRING BOOT TEST ***
        The @SpringBootTest annotation tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance) 
        and use that to start a Spring application context (ioc, di, beans, server, ...). By default does NOT load a server
    
        Spring Test support caches the application context between tests. That way, if you have multiple methods in a test case or multiple test cases 
        with the same configuration, they incur the cost of starting the application only once. You can control the cache by using the @DirtiesContext annotation. 
    
        The @SpringBootTest annotation parameters:
        - MOCK(Default): Loads a web ApplicationContext and provides a mock web environment. Embedded servers are not started when using this annotation. 
          If a web environment is not available on your classpath, this mode transparently falls back to creating a regular non-web ApplicationContext. 
          It can be used in conjunction with @AutoConfigureMockMvc or @AutoConfigureWebTestClient for mock-based testing of your web application.
        - RANDOM_PORT: Loads a WebServerApplicationContext and provides a real web environment. 
          Embedded servers are started and listen on a random port.
        - DEFINED_PORT: Loads a WebServerApplicationContext and provides a real web environment. 
          Embedded servers are started and listen on a defined port (from your application.properties) or on the default port of 8080.
        - NONE: Loads an ApplicationContext by using SpringApplication but does not provide any web environment (mock or otherwise).

        Using RANDOM_PORT or DEFINED_PORT make it possibile to test rest endpoint using TestRestTemplate.
        @LocalServerPort to inject the calculated port
    
        *** MOCK MVC ***
        Another useful approach is to NOT start the server at all but to test only the layer below that, where Spring handles the incoming HTTP request and 
        hands it off to your controller. That way, almost of the full stack is used (ioc, di, beans), and your code will be called in exactly the same way 
        as if it were processing a real HTTP request but without the cost of starting the server.
        
        @SpringBootTest + @AutoConfigureMockMvc annotation on the test class to inject (@Autowired) MockMvc instance
        @SpringBootTest + @AutoConfigureWebTestClient annotation on the test class to inject (@Autowired) WebTestClient instance
        
        Testing within a mocked environment is usually faster than running with a full Servlet container. However, since mocking occurs at the Spring MVC layer, 
        code that relies on lower-level Servlet container behavior cannot be directly tested with MockMvc.
    
        *** WEB MVC TEST ***
        In this case it is possible to test ONLY the web layer. NO CONTEXT is loaded!
        Regular @Component, @Service and @ConfigurationProperties beans are not scanned
        @WebMvcTest annotation on the test class to inject (@Autowired) MockMvc instance. 
        @WebMvcTest can be limited to a single controller
    
        If the controller uses services (@Service or @Component) these are not loaded because the context is off, so they need to be explicitly created
        using @MockBean. 
        @MockBean is used to create and inject a mock of the service bean.
        Mockito is then used to set expectations on that bean (when(...).thenReturn(...))
        
    */
    
    @Autowired
    private CalcRestController calcController;
    
    @Test
    void contextLoads() {
        //Sanity check
        assertThat(calcController).isNotNull();
    }

}
