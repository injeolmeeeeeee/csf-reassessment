import { Component, OnInit, inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Order } from '../models';
import { Router } from '@angular/router';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
  'chicken', 'seafood', 'beef', 'vegetables',
  'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  // TODO: Task 2

  form!: FormGroup
  email !: string
  // formArray !: FormArray 
  toppings!: FormArray;

  pizzaSize = SIZES[0]

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private pizzaSvc: PizzaService
  ) { }

  ngOnInit(): void {
    this.form = this.createForm()
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  private createForm(): FormGroup {
    // this.toppings = this.fb.array([], [Validators.required])
    return this.fb.group({
      name: this.fb.control<string>("", [Validators.required]),
      email: this.fb.control<string>("", [Validators.required, Validators.email]),
      size: this.fb.control<string>("Regular" ,[Validators.required]),
      base: this.fb.control<boolean>(false, [Validators.required]),
      sauce: this.fb.control<string>("classic", [Validators.required]),
      // toppings: this.fb.array([], [Validators.required]),
      toppings: this.fb.array([]),
      comments: this.fb.control<string>("")
    })
  }

  placeOrder() {
    const sizeString: string = this.form.value['size'];
    const sizeInches: number = this.getSizeInches(sizeString);
    const thickCrust: boolean = this.form.value['base'] === 'thick';

    const order: Order = {
      name: this.form.value['name'],
      email: this.form.value['email'],
      size: sizeInches,
      thickCrust: this.form.value['base'],
      sauce: this.form.value['sauce'],
      // toppings: this.form.value['toppings'],
      //hard coded
      toppings: ['chicken'],
      comments: this.form.value['comments']
    }

    // console.log("placeOrder", order)
    this.pizzaSvc.createOrder(order)
    const email: string = this.form.value.email
    this.router.navigate(['/orders', email]);
    this.form.reset()
  }

  getSizeInches(sizeString: string): number {
    const parts = sizeString.split('-');
    if (parts.length > 1) {
      const inchesPart = parts[1].trim().split(' ')[0];
      return parseInt(inchesPart);
    }
    return 0;
  }

  listOrders() {
    const email: string = this.form.value.email;
    this.pizzaSvc.getOrders(email)
      .then(orderSummary => {
        this.router.navigate(['/orders', email, { orders: JSON.stringify(orderSummary) }]);
      })
      .catch(error => {
        console.error('Error fetching orders:', error);
      });
  }
  

  checkbox(event: any) {
    const value = event.target.value;
    const toppings = this.form.get('toppings') as FormArray;
    const currentToppings = [...toppings.value];
  
    if (event.target.checked && !currentToppings.includes(value)) {
      currentToppings.push(value);
    } else if (!event.target.checked && currentToppings.includes(value)) {
      const index = currentToppings.indexOf(value);
      currentToppings.splice(index, 1);
    }
  
    this.form.patchValue({
      toppings: currentToppings
    });
  }  
}