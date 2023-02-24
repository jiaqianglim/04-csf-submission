package vttp2022.csf.assessment.server.models;

import org.bson.Document;
import java.util.List;

public class ModelConversion {


    public static Restaurant toRestaurant(Document d) {

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(d.getString("restaurant_id"));
        restaurant.setName(d.getString("name"));
        restaurant.setCuisine(d.getString("cuisine"));
        restaurant.setAddress(d.getString("address"));
        LatLng ll = new LatLng();
        List<Float> lll = d.getList("address.coord", float.class);
        ll.setLatitude(lll.get(0));
        ll.setLongitude(lll.get(1));
        restaurant.setCoordinates(ll);
        return restaurant;
    }
}
