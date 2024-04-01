package vttp2023.assessment.csf.orderbackend.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.assessment.csf.orderbackend.models.Order;

@Repository
public class PizzaRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO Task 6 - you are free to add parameters and change the return type
	// DO NOT CHANGE THE METHOD NAME
	// Write the native MongoDB statement in the commend below. Marks will be
	// give for the native MongoDB statement
	/*
	 * db.pizza.insertOne({
	 * _id: "id",
	 * size: "Personal - 6 inches",
	 * thickCrust: false,
	 * sauce: "Classic Tomate Sauce",
	 * toppings: ["chicken", "seafood", "beef"],
	 * comments: "comments"
	 * })
	 */
	public void createPizza(Order order, String pizzaId) {

		Document doc = new Document();
		doc.append("_id", pizzaId)
				.append("size", order.getSize())
				.append("thickCrust", order.isThickCrust())
				.append("sauce", order.getSauce())
				.append("toppings", order.getToppings())
				.append("comments", order.getComments());
	}

	// TODO Task 7
	// Write the native MongoDB statement in the commend below. Marks will be
	// give for the native MongoDB statement
	/*
	 * db.pizza.find({ "_id": "pizzaId" })
	 */

	public List<Order> getPizzasById(List<String> pizzaIds) {
        List<Order> orders = new ArrayList<>();
        for (String pizzaId : pizzaIds) {
            Query query = new Query(Criteria.where("_id").is(pizzaId));
            List<Order> pizzaOrders = mongoTemplate.find(query, Order.class, "pizza");
            orders.addAll(pizzaOrders);
        }
        return orders;
    }
}
