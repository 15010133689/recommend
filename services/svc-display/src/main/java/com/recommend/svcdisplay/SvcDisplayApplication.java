package com.recommend.svcdisplay;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.serialization.Serializer;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestOperations;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.recommend.svcdisplay"})
@EnableJpaRepositories(basePackages = {"com.recommend.svcdisplay"})
@EnableMongoRepositories(basePackages = {"com.recommend.svcdisplay"})
@EnableAutoConfiguration
public class SvcDisplayApplication {



	public static void main(String[] args) {
		SpringApplication.run(SvcDisplayApplication.class, args);
	}
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
//		c.setIgnoreUnresolvablePlaceholders(true);
//		return c;
//	}
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
