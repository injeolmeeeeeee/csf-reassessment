import { Order, OrderSummary } from "./models";
import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { catchError, map, of } from "rxjs";

@Injectable()
export class PizzaService {

  constructor() { }

  private http = inject(HttpClient)

  // TODO Task 3 - Do not change the parameter of this method
  // The method may return any type
  createOrder(order: Order) {
    console.log("sent order to BE:", order)
    return this.http.post<Order>(`/api/order`, order);
  }

  // TODO Task 3 - You are free to add addtional parameters to this method
  // Do not change the return type
  getOrders(email: string): Promise<OrderSummary[]> {
    return new Promise((resolve, reject) => {
      this.http.get<OrderSummary[]>(`/api/order/${email}/all`)
        .pipe(
          catchError(() => of<OrderSummary[]>([])),
          map(data => data || [])
        )
        .subscribe(
          (orders: OrderSummary[]) => {
            resolve(orders);
          },
          (error) => {
            reject(error);
          }
        );
    });
  }
  

  // /api/order/<email>/all
  // accept application/json

  // export interface OrderSummary {
  //   orderId: number
  //   name: string
  //   email: string
  //   amount: number
  // }

}
