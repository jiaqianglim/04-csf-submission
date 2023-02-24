import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css'],
})
export class CuisineListComponent implements OnInit {
  // TODO Task 2
  // For View 1
  constructor(private rs: RestaurantService, private router: Router) {}
  cuisines!: String[];
  
  ngOnInit() {
    this.cuisines = this.rs.getCuisineList();
  }

  getListOfCuisine(cusine:String) {
    return this.rs.getCuisineList();
  }

  toCuisine(cus: String) {
    this.router.navigate(['cuisine', cus]);
  }
}
