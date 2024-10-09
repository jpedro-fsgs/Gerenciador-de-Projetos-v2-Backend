package dev.jpfsgs.gerenciadordeprojetosv2backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GerenciadorDeProjetosV2BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorDeProjetosV2BackendApplication.class, args);
	}

}
