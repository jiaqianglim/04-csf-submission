import { Restaurant, Comment } from './models';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { lastValueFrom, Observable, throwError } from 'rxjs';
import { catchError, retry, take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  constructor(private http: HttpClient) {}

  // TODO Task 2
  // Use the following method to get a list of cuisines
  // You can add any parameters (if any) and the return type
  // DO NOT CHNAGE THE METHOD'S NAME
  public getCuisineList(): String[] {
    // Implememntation in here
    let cuisineList: String[] = [];
    let results$ = lastValueFrom(
      this.http.get<String[]>('/api/cuisine').pipe(take(1))
    ).then((data) => {
      data.forEach(function (i) {
        cuisineList.push(i);
      });
    });
    return cuisineList;
  }

  // TODO Task 3
  // Use the following method to get a list of restaurants by cuisine
  // You can add any parameters (if any) and the return type
  // DO NOT CHNAGE THE METHOD'S NAME
  public getRestaurantsByCuisine(cuisine: string) {
    // Implememntation in here
    let restaurantList: String[] = [];
    let results$ = lastValueFrom(
      this.http.get<String[]>('/api/' + cuisine + '/restaurants').pipe(take(1))
    ).then((data) => {
      data.forEach(function (i) {
        restaurantList.push(i);
      });
    });
    return restaurantList;
  }

  // TODO Task 4
  // Use this method to find a specific restaurant
  // You can add any parameters (if any)
  // DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
  public getRestaurant(name: String): Promise<Restaurant> {
    // Implememntation in here
  }

  // TODO Task 5
  // Use this method to submit a comment
  // DO NOT CHANGE THE METHOD'S NAME OR SIGNATURE
  public postComment(comment: Comment): Promise<any> {
    // Implememntation in here
  }
}
