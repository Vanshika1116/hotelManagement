package com.example.HotelManagement.Service;

import com.example.HotelManagement.Model.Hotel;
import com.example.HotelManagement.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public boolean existsById(Long hotelId) {
        return hotelRepository.existsById(hotelId);
    }
    public ResponseEntity<Hotel> addHotel(Hotel hotel) {
        try {
            Hotel savedHotel = hotelRepository.save(hotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
        } catch (DataAccessException e) {
            // Handle database-related errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Hotel updateHotel(Hotel updatedHotel) {
        return hotelRepository.save(updatedHotel);
    }

    public ResponseEntity<Void> deleteHotel(Long hotelId) {
        try {
            // Check if hotel exists
            if (!hotelRepository.existsById(hotelId)) {
                return ResponseEntity.notFound().build();
            }

            hotelRepository.deleteById(hotelId);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            // Handle case where hotel with given ID is not found
            return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
            // Handle database-related errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Hotel>> getHotelById(List<Long> hotelId) {
        try {
            List<Hotel> hotels = hotelRepository.findAllById(hotelId);
            if (hotels.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(hotels);
        } catch (DataAccessException e) {
            // Handle database-related errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
    public List<Hotel> getHotelsByRating(Double rating) {
        return hotelRepository.findByRating(rating);
    }
    public List<Hotel> getAllHotelsSortedByRating() {
        return hotelRepository.findAllByOrderByRatingDesc();
    }
}
