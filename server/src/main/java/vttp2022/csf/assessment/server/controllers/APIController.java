package vttp2022.csf.assessment.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import vttp2022.csf.assessment.server.services.RestaurantService;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    RestaurantService restsvc;

    @GetMapping
    public ResponseEntity<String> getRestaurantsByCuisine(String cuisine) {
        List<String> namesOfRestaurants = restsvc.getRestaurantsByCuisine(cuisine);
        return ResponseEntity.ok().body(namesOfRestaurants.toString());
    }

    @PostMapping(path = "/comments", consumes="application/json")
    public ResponseEntity<String> postComment(@ResponseBody String payload){
        JsonBody body;
        try (InputStream is = new ByteArrayInputStream(payload.getBytes()) {
            JsonReader reader = Json.createReader(is);
            body = read.readObject();

            //

            return ResponseEntity.Created()
        } catch (Exception ex) {
        body = Json.createObject()
        .add(“error”, ex.getMessage()).build();
        return ResponseEntity.internalServerError().body(body.toString());
}

    }

}
