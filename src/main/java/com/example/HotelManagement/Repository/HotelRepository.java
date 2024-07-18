package com.example.HotelManagement.Repository;

import com.example.HotelManagement.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByRating(Double rating);
    List<Hotel> findAllByOrderByRatingDesc();
}