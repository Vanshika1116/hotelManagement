package com.example.HotelManagement.Controller;

import com.example.HotelManagement.Model.Hotel;
import com.example.HotelManagement.Service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/save")
    public ResponseEntity<Hotel> saveOrUpdateHotel(@RequestBody Hotel hotel) {
        try {
            if (hotel.getId() != null) {
                if (!hotelService.existsById(hotel.getId())) {
                    return ResponseEntity.notFound().build();
                }
                Hotel updatedHotel = hotelService.updateHotel(hotel);
                return ResponseEntity.ok(updatedHotel);
            } else {
                Hotel newHotel = hotelService.addHotel(hotel);
                return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId) {
        boolean deleted = hotelService.deleteHotel(hotelId);

        if (deleted) {
            return ResponseEntity.ok("Hotel with ID " + hotelId + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel with ID " + hotelId + " not found");
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<List<Hotel>> getHotelById(@RequestParam List<Long> hotelId) {
        List<Hotel> hotels = hotelService.getHotelById(hotelId);
        if (hotels == null || hotels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/list")
    public Page<Hotel> listHotels(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "rating") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        return hotelService.getAllHotels(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/hotelslist")
    public List<Hotel> getHotels(
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false) Integer minRooms,
            @RequestParam(required = false) Integer maxRooms,
            @RequestParam(required = false) String sortBy) {
        return hotelService.getHotelsByCriteria(minRating, maxRating, minRooms, maxRooms, sortBy);
    }
}
