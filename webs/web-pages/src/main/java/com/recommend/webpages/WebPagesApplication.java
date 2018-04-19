package com.recommend.webpages;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.config.EnableAxon;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class WebPagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebPagesApplication.class, args);
	}

	@Bean
	public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient,Registration localServiceInstance) {
		return new SpringCloudCommandRouter(discoveryClient,localServiceInstance, new AnnotationRoutingStrategy());
	}
	@Bean
	public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment")CommandBus localSegment, RestOperations restOperations,
															 Serializer serializer) {
		return new SpringHttpCommandBusConnector(localSegment, restOperations, serializer);
	}
	@Primary // to make sure this CommandBus implementation is used for autowiring
	@Bean
	public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter commandRouter, CommandBusConnector commandBusConnector) {
		return new DistributedCommandBus(commandRouter, commandBusConnector);
	}
}
