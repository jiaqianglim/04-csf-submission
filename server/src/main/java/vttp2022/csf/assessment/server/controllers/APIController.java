package vttp2022.csf.assessment.server.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.Response;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.ModelConversion;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    RestaurantService restsvc;

    @GetMapping(path = "/cuisine")
    public ResponseEntity<String> getCuisines() {
        JsonObjectBuilder jo = Json.createObjectBuilder();
        List<String> cuisines = restsvc.getCuisines();
        JsonArrayBuilder ja = Json.createArrayBuilder();
        cuisines.stream().map(c -> ja.add(c));
        jo.add("cuisine", ja);
        return ResponseEntity.ok().body(jo.build().toString());
    }

    @GetMapping(path = "/{cuisine}/restaurants")
    public ResponseEntity<String> getRestaurantsByCuisine(@PathVariable String cuisine) {
        List<String> namesOfRestaurants = restsvc.getRestaurantsByCuisine(cuisine);
        JsonObjectBuilder jo = Json.createObjectBuilder();
        JsonArrayBuilder ja = Json.createArrayBuilder();
        namesOfRestaurants.stream().map(c -> ja.add(c));
        return ResponseEntity.ok().body(jo.build().toString());
    }

    @GetMapping(path = "/restaurant")
    public ResponseEntity<String> getRestaurantByName(@RequestParam String name) {
        Optional<Restaurant> rest = restsvc.getRestaurant(name);
        if (rest.isPresent()) {
            Restaurant re = rest.get();
            JsonObject resp = Json.createObjectBuilder()
                    .add("restaurant_id", re.getRestaurantId())
                    .add("name", re.getName())
                    .add("cuisine", re.getCuisine())
                    .add("address", re.getAddress())
                    .add("coordinates",
                            Json.createArrayBuilder().add(re.getCoordinates().getLatitude())
                                    .add(re.getCoordinates().getLongitude()))
                    .build();
            return ResponseEntity.ok().body(resp.toString());
        } else {
            return ResponseEntity.badRequest().body("bad request");
        }
    }

    @PostMapping(path = "/comments", consumes = "application/json")
    public ResponseEntity<String> postComment(@RequestBody String payload) {
        try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject body = reader.readObject();
            Comment comment = ModelConversion.toComment(body);
            restsvc.addComment(comment);
            JsonObject resp = Json.createObjectBuilder().add("message", "Comment posted").build();
            return ResponseEntity.created(null).body(resp.toString());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("error");
        }

    }

}
