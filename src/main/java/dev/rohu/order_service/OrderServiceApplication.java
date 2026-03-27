package dev.rohu.order_service;

import dev.rohu.order_service.dto.request.CreateOrderRequest;
import dev.rohu.order_service.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedData(OrderService orderService) {
		return args -> {
			CreateOrderRequest r1 = new CreateOrderRequest();
			r1.setCustomerName("Alice");
			r1.setAmount(250.0);

			CreateOrderRequest r2 = new CreateOrderRequest();
			r2.setCustomerName("Bob");
			r2.setAmount(899.99);

			CreateOrderRequest r3 = new CreateOrderRequest();
			r3.setCustomerName("Charlie");
			r3.setAmount(149.0);

			orderService.createOrder(r1);
			orderService.createOrder(r2);
			orderService.createOrder(r3);

			System.out.println("Seeded 3 orders successfully.");
		};
	}

}
