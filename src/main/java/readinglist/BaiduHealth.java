package readinglist;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BaiduHealth implements HealthIndicator {

	@Override
	public Health health() {
		try {
			RestTemplate rest = new RestTemplate();
			rest.getForObject("www.baidubaidubaidu.com", String.class);
			return Health.up().build();
		} catch (Exception e) {
			return Health.down().withDetail("reason", e.getMessage()).build();
		}
	}

}
