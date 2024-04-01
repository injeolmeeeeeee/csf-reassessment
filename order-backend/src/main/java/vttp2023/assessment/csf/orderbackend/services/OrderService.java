package vttp2023.assessment.csf.orderbackend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.assessment.csf.orderbackend.models.Order;
import vttp2023.assessment.csf.orderbackend.models.OrderSummary;
import vttp2023.assessment.csf.orderbackend.repositories.OrderRepository;
import vttp2023.assessment.csf.orderbackend.repositories.PizzaRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PizzaRepository pizzaRepository;

	@Autowired
	PricingService pricingService;

	// TODO: Task 6 Use this method to persist the Order
	// DO NOT MODIFY THE SIGNATURE OF THIS METHOD. You may only add thrown exceptions
	public String createOrder(Order order) {

		String pizzaId = UUID.randomUUID().toString().substring(0, 6);

		Random random = new Random();
        int min = 10000000;
        int max = 99999999;
        int orderIdInt = random.nextInt(max - min + 1) + min;

		String orderId= Integer.toString(orderIdInt);

		orderRepository.createOrder(order, pizzaId, orderIdInt);
		pizzaRepository.createPizza(order, pizzaId);

		// Return the order_id 
		return orderId;
	}

	// TODO: Task 7 Use this method to get a list of all the orders
	// DO NOT MODIFY THE SIGNATURE OF THIS METHOD. You may only add thrown exceptions
	public List<OrderSummary> getOrdersByEmail(String email) {
        List<OrderSummary> orderSummaries = new ArrayList<>();
        List<String> pizzaIds = orderRepository.getPizzaIdsByEmail(email);
        List<Order> orders = pizzaRepository.getPizzasById(pizzaIds);
        
        for (Order order : orders) {
            OrderSummary orderSummary = new OrderSummary();
            orderSummary.setOrderId(order.getOrderId());
            orderSummary.setName(order.getName());
            orderSummary.setEmail(order.getEmail());
            orderSummary.setAmount(calculateOrderAmount(order));
            orderSummaries.add(orderSummary);
        }

        return orderSummaries;
    }

    private float calculateOrderAmount(Order order) {
        float amount = 0.0f;
        amount += pricingService.size(order.getSize());
        amount += pricingService.sauce(order.getSauce());
        for (String topping : order.getToppings()) {
            amount += pricingService.topping(topping);
        }

        if (order.isThickCrust()) {
            amount += pricingService.thickCrust();
        } else {
            amount += pricingService.thinCrust();
        }

        return amount;
    }
}
