import {Order, OrderSummary} from "./models";

export class PizzaService {

  constructor() { }

  // TODO Task 3 - Do not change the parameter of this method
  // The method may return any type
  createOrder(order: Order) {
  }

  // TODO Task 3 - You are free to add addtional parameters to this method
  // Do not change the return type
  getOrders(): Promise<OrderSummary[]> {
	  return Promise.resolve<OrderSummary[]>([])
  }

}
