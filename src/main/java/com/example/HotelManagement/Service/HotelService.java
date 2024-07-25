package com.example.HotelManagement.Service;

import com.example.HotelManagement.Model.Hotel;
import com.example.HotelManagement.Repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.data.domain.*;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@RequiredArgsConstructor
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public boolean existsById(Long hotelId) {
        return hotelRepository.existsById(hotelId);
    }

    public Hotel addHotel(Hotel hotel) {
        // Save and return the hotel entity
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Hotel updatedHotel) {
        // Save and return the updated hotel entity
        return hotelRepository.save(updatedHotel);
    }

    public boolean deleteHotel(Long hotelId) {
        try {
            // Check if hotel exists
            if (!hotelRepository.existsById(hotelId)) {
                return false;
            }
            hotelRepository.deleteById(hotelId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // Handle case where hotel with given ID is not found
            return false;
        } catch (DataAccessException e) {
            // Handle database-related errors
            throw new RuntimeException("Database error", e);
        }
    }

    public List<Hotel> getHotelById(List<Long> hotelId) {
        try {
            List<Hotel> hotels = hotelRepository.findAllById(hotelId);
            if (hotels.isEmpty()) {
                return null; // or throw an exception if you prefer
            }
            return hotels;
        } catch (DataAccessException e) {
            // Handle database-related errors
            throw new RuntimeException("Database error", e);
        }
    }

    public Page<Hotel> getAllHotels(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        // Set the sort direction
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        //The sortDirection parameter is expected to be a string that indicates the direction of sorting, either "ASC" for
        // ascending or "DESC" for descending.

        // Create Pageable instance with sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));

        // Retrieve a page of hotels from the repository
        return hotelRepository.findAll(pageable);
    }

    public Page<Hotel> searchHotels(int rating, int noOfRooms, Pageable pageable) {
        return hotelRepository.findByRatingAndNoOfRoomsGreaterThanEqual(rating, noOfRooms, pageable);
    }

}
