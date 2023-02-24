import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css'],
})
export class RestaurantDetailsComponent implements OnInit {
  // TODO Task 4 and Task 5
  // For View 3
  RestaurantDets!: Restaurant;
  restaurantName!: String;
  cuisineName!: String;
  commentForm!: FormGroup;

  constructor(
    private rs: RestaurantService,
    private router: Router,
    private aR: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.commentForm = this.fb.group({
      name: this.fb.control<string>('', Validators.required),
      rating: this.fb.control<string>('', [
        Validators.required,
        Validators.min(1),
        Validators.max(5),
      ]),
      text: this.fb.control<string>('', Validators.required),
    });
    this.rs.getRestaurant(this.aR.snapshot.params['restN']).then((data) => {
      this.RestaurantDets.name = data.name;
      this.RestaurantDets.cusisine = data.cusisine;
      this.RestaurantDets.address = data.address;
      this.RestaurantDets.restaurantId = data.restaurantId;
      this.RestaurantDets.coordinates = data.coordinates;
    });
  }

  toCuisine() {
    this.router.navigate;
  }
}
