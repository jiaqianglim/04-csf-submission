package vttp2022.csf.assessment.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;

public class RestaurantRepository {
	@Autowired
	MongoTemplate mongotemp;

	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//
	public List<String> getCuisines() {
		// Implmementation in here
		// db.tv_shows.distinct(‘cuisine’)
		List<String> cuisineList = mongotemp.findDistinct(new Query(), "cuisine", "restaurants", String.class);
		return cuisineList;
	}

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//
	public List<String> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		Query query = Query.query(Criteria.where("cuisine").in(cuisine));
		List<String> results = mongotemp.find(query, String.class, "restuarants");

	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any)
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//
	public Optional<Restaurant> getRestaurant(String name) {
		// Implmementation in here
		Query query = Query.query(Criteria.where("name").in(name);
		

		
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//
	public void addComment(Comment comment) {
		// Implmementation in here

	}

	// You may add other methods to this class

}
