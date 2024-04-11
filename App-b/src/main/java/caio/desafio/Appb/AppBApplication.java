package caio.desafio.Appb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class AppBApplication {

	@GetMapping("/random")
	public int getRandomNumber() {
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	        String url = "http://app-b/random";
	        int result = restTemplate.getForObject(url, Integer.class);
	        Random random = new Random();
	        return random.nextInt(100) + result;
	    } catch (Exception e) {
	        return -2; // APP B não está disponível
	    }
	}
		
	public static void main(String[] args) {
		SpringApplication.run(AppBApplication.class, args);
	}

}
