package vttp2023.assessment.csf.orderbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import vttp2023.assessment.csf.orderbackend.models.Order;
import vttp2023.assessment.csf.orderbackend.models.OrderSummary;
import vttp2023.assessment.csf.orderbackend.services.OrderService;

@RequestMapping(path = "/api")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrderRestController {

	@Autowired
	OrderService orderService;

	// TODO Task 6
	@PostMapping("/order")
	public ResponseEntity<String> postOrder(@RequestBody Order order) {

		System.out.println("OrderRestController:" + order);

		String orderId = orderService.createOrder(order);

		try {
			orderService.createOrder(order);
			return ResponseEntity.ok(
					Json.createObjectBuilder()
							.add("orderId", orderId)
							.build()
							.toString());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					Json.createObjectBuilder()
							.add("message", e.getMessage())
							.build()
							.toString());
		}
	}

	// TODO Task 7
	@GetMapping("/order/{email}/all")
	public ResponseEntity<List<OrderSummary>> getOrderAll(@PathVariable String email) {
        List<OrderSummary> orderSummaries = orderService.getOrdersByEmail(email);
        return ResponseEntity.ok(orderSummaries);
    }
}