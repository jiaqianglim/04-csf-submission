package vttp2022.csf.assessment.server.models;

import org.bson.Document;

import jakarta.json.JsonObject;

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

    public static String toKeyString(LatLng ll) {
        StringBuilder sb = new StringBuilder();
        sb.append(Float.toString(ll.getLatitude()));
        sb.append(Float.toString(ll.getLongitude()));
        return sb.toString();
    }

    public static Document toDocument(Comment comment) {
        Document doc = new Document();
        doc.append("name", comment.getName());
        doc.append("rating", comment.getRating());
        doc.append("text", comment.getText());
        return doc;
    }

    public static Comment toComment(JsonObject o) {
        Comment c = new Comment();
        c.setName(o.getString("name"));
        c.setRating(o.getInt("rating"));
        c.setText(o.getString("text"));
        return c;
    }
}
