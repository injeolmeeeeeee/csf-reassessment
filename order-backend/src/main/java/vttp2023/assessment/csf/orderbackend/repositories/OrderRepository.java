package vttp2023.assessment.csf.orderbackend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2023.assessment.csf.orderbackend.models.Order;
import vttp2023.assessment.csf.orderbackend.models.OrderSummary;

@Repository
public class OrderRepository {

	@Autowired
	private JdbcTemplate template;

	// TODO Task 6 - you are free to add parameters and change the return type
	// DO NOT CHANGE THE METHOD NAME
	public void createOrder(Order order, String pizzaId, Integer orderId) {

		int insertOrderRows = template.update("INSERT INTO orders VALUES (?, ?, ?, ?)",
				orderId,
				order.getName(),
				order.getEmail(),
				pizzaId
		);
	
		if (insertOrderRows < 1) {
			throw new RuntimeException("Failed to insert into order_data.");
		}
	}
	

	// TODO Task 7
	@SuppressWarnings("deprecation")
	public List<OrderSummary> getOrdersByEmail(String email) {
        return template.query(
                "SELECT orderId, name, email, amount FROM orders WHERE email = ?",
                new Object[]{email},
                (rs, rowNum) -> {
                    OrderSummary orderSummary = new OrderSummary();
                    orderSummary.setOrderId(rs.getInt("orderId"));
                    orderSummary.setName(rs.getString("name"));
                    orderSummary.setEmail(rs.getString("email"));
                    orderSummary.setAmount(rs.getFloat("amount"));
                    return orderSummary;
                }
        );
    }

	public List<String> getPizzaIdsByEmail(String email) {
        return template.queryForList(
            "SELECT pizza_id FROM orders WHERE email = ?",
            String.class,
            email
        );
    }
}

// private Integer orderId;
// 	private String name;
// 	private String email;
// 	private Integer size;
// 	private String sauce;
// 	private Boolean thickCrust;
// 	private List<String> toppings = new LinkedList<>();
// 	private String comments;