package br.com.bureau.gateway.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQUserConfig {
	
	@Value("${queue.get.user}")
    private String getUserQueue;
	
	@Bean
	public Queue user() {
		return new Queue(this.getUserQueue, true);
	}
}
