package vttp2022.csf.assessment.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.bson.Document;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.ModelConversion;
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
		return results;
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any)
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	// db.restaurant.aggregrate([{$match:{name: "name"}}, {$project:{_id:-1,
	// restaurant_id:1, name:1, cuisine:1, address:1, address.coord:1}}])
	public Optional<Restaurant> getRestaurant(String name) {
		// Implmementation in here
		MatchOperation matchName = Aggregation.match(Criteria.where("name").is(name));
		ProjectionOperation projectFields = Aggregation
				.project("restaurant_id", "name", "cuisine", "address").and("address.coord").as("coordinates")
				.andExclude("_id");
		LimitOperation limitToOne = Aggregation.limit(1);
		Aggregation pipeline = Aggregation.newAggregation(matchName, projectFields, limitToOne);
		List<Document> results = mongotemp.aggregate(pipeline, "restaurants", Document.class).getMappedResults();
		Document result = results.get(0);
		return Optional.ofNullable(ModelConversion.toRestaurant(result));
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//
	public void addComment(Comment comment) {
		// Implmementation in here
		String name = comment.getName();
		Query query = Query.query(Criteria.where("name").is(comment.getName()));
		Update update = new Update().set("comments", ModelConversion.toDocument(comment));
		mongotemp.updateMulti(query, update, Document.class, "restaurants");
	}

	// You may add other methods to this class

}
