package com.example.HotelManagement.Controller;

import com.example.HotelManagement.Model.Hotel;
import com.example.HotelManagement.Repository.HotelRepository;
import com.example.HotelManagement.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // Endpoint to onboard a new hotel
    @PostMapping("/add")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        return hotelService.addHotel(hotel);
    }

    // Endpoint to modify hotel information
    @PutMapping("/update/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long hotelId, @RequestBody Hotel updatedHotel) {
        try {
            // Check if the hotel with the given ID exists
            if (!hotelService.existsById(hotelId)) {
                return ResponseEntity.notFound().build();
            }

            // Set the ID of the updated hotel object
            updatedHotel.setId(hotelId);

            // Update the hotel and return the updated entity
            Hotel savedHotel = hotelService.updateHotel(updatedHotel);
            return ResponseEntity.ok(savedHotel);
        } catch (Exception e) {
            // Handle exceptions and return internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to delete hotel information
    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId) {
        ResponseEntity<Void> responseEntity = hotelService.deleteHotel(hotelId);

        if (responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            return ResponseEntity.status(HttpStatus.OK).body("Hotel with ID " + hotelId + " deleted successfully");
        } else if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel with ID " + hotelId + " not found");
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).build();
        }
    }


    // Endpoint to get hotels by IDs
    @GetMapping("/getById")
    public ResponseEntity<List<Hotel>> getHotelById(@RequestParam List<Long> hotelId) {
        return hotelService.getHotelById(hotelId);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Hotel>> listHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hotels);
    }
    @GetMapping("/filterByRating")
    public ResponseEntity<?> filterHotelsByRating(@RequestParam(name = "rating") Double rating) {
        try {
            List<Hotel> filteredHotels = hotelService.getHotelsByRating(rating);
            if (filteredHotels.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found with rating: " + rating);
            }
            return ResponseEntity.ok(filteredHotels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to filter hotels by rating: " + rating);
        }
    }
    @GetMapping("/sort")
    public List<Hotel> sortHotels() {
        return hotelService.getAllHotelsSortedByRating();
    }
}
