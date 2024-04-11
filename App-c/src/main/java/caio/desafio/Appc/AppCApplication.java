package caio.desafio.Appc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class AppCApplication {

	
	@GetMapping("/random")
    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(100);
	}// Retorna um número aleatório entre 0 e 99
	
	public static void main(String[] args) {
		SpringApplication.run(AppCApplication.class, args);
	}

}
