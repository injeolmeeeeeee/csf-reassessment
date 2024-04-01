import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './components/main.component';
import { OrdersComponent } from './components/orders.component';
import { RouterModule, Routes } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { PizzaService } from './pizza.service';
import { HttpClientModule } from '@angular/common/http';

const appRoutes: Routes = [
  { path: '', component: MainComponent },
  { path: 'orders/:email', component: OrdersComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
]

@NgModule({
  declarations: [ AppComponent, MainComponent, OrdersComponent ],
  imports: [ BrowserModule, RouterModule, ReactiveFormsModule, HttpClientModule, 
    RouterModule.forRoot(appRoutes, { useHash: true }) ],
  providers: [  PizzaService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
