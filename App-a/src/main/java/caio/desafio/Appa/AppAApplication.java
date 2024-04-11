package caio.desafio.Appa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class AppAApplication {



	@RestController
	public class AppAController {

	    @GetMapping("/sum")
	    public int getSum() {
	        RestTemplate restTemplate = new RestTemplate();
	        try {
	            String url = "http://app-b/random";
	            int numberFromB = restTemplate.getForObject(url, Integer.class);
	            int numberLocal = generateRandomNumber();
	            return numberFromB + numberLocal;
	        } catch (Exception e) {
	            return -1; // APP B não está disponível
	        }
	    }

	    private int generateRandomNumber() {
	        // Gera um número aleatório entre 0 e 99
	        return (int) (Math.random() * 100);
	    }
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AppAApplication.class, args);
	}

}
