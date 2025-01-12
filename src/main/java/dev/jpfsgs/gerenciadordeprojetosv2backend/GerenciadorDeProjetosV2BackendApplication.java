package dev.jpfsgs.gerenciadordeprojetosv2backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(
		servers = {
				@Server(url = "/", description = "Default Server URL")
		}
)
@SpringBootApplication
@EnableCaching
public class GerenciadorDeProjetosV2BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorDeProjetosV2BackendApplication.class, args);
	}

}
