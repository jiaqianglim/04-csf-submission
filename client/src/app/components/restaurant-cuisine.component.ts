import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css'],
})
export class RestaurantCuisineComponent implements OnInit {
  // TODO Task 3
  // For View 2
  restList!: String[];
  cuisinetag: string = '';

  constructor(
    private rs: RestaurantService,
    private router: Router,
    private aR: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.cuisinetag = this.aR.snapshot.params['cuisN'];
    this.restList = this.rs.getRestaurantsByCuisine(this.cuisinetag);
  }

  toRest(restaurantName: String) {
    this.router.navigate(['/restaurant', restaurantName]);
  }

  toIndex() {
    this.router.navigate(['']);
  }
}
