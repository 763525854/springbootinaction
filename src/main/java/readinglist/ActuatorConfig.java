package readinglist;

import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {
	@Bean
	public InMemoryTraceRepository traceRepository() {
		InMemoryTraceRepository traceRepository = new InMemoryTraceRepository();
		traceRepository.setCapacity(1000);
		return traceRepository;
	}
}
