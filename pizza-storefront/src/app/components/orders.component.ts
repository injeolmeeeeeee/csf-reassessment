import { Component, OnInit, inject } from '@angular/core';
import { OrderSummary } from '../models';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  email !: string |null;
  orderSummary: OrderSummary[] = [];
  private route = inject(ActivatedRoute)
  


  constructor() { }

  ngOnInit(): void {
    this.email = this.route.snapshot.paramMap.get('email');
    const ordersString = this.route.snapshot.paramMap.get('orders');
    if (ordersString) {
      this.orderSummary = JSON.parse(ordersString);
    }
  }

}
