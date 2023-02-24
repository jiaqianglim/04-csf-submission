import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CuisineListComponent } from './components/cuisine-list.component';
import { RestaurantCuisineComponent } from './components/restaurant-cuisine.component';
import { RestaurantDetailsComponent } from './components/restaurant-details.component';

const routes: Routes = [
  { path: '', component: CuisineListComponent },
  { path: '/cuisine/:cuisN', component: RestaurantCuisineComponent },
  { path: '/restaurant/:restN', component: RestaurantDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
