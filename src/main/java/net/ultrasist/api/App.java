package net.ultrasist.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.mybatis.spring.annotation.MapperScan;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableEncryptableProperties
@OpenAPIDefinition( 
    servers = {
       @Server(url = "/", description = "Modified Server URL because swagger changes https for an invalid http")
    }
) // https://www.baeldung.com/spring-rest-openapi-documentation
@MapperScan("net.ultrasist.api.mappings")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
