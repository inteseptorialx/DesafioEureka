package caio.desafio.Appa;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class AppAApplication {

	@Autowired
	@Lazy
	EurekaClient eurekaApps;

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

	public String makeCall(String instanceId) throws URISyntaxException {
		String message = "Olá, tem alguem ai?!";

		List instances = eurekaApps.getInstancesById(instanceId);

		InstanceInfo instance = (InstanceInfo) instances.getFirst();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI("http://"+instance.getIPAddr() + ":" + instance.getPort()+"/random"))
				.POST(HttpRequest.BodyPublishers.ofString(message))
				.build();
		try {
			HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
			return response.body().toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AppAApplication.class, args);
	}

}
