package com.example.HotelManagement.Repository;
import com.example.HotelManagement.Model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.ArrayList;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByRating(Double rating);

    List<Hotel> findAllByOrderByRatingDesc();

    @Query("SELECT h FROM Hotel h WHERE (:minRating IS NULL OR h.rating >= :minRating) " +
            "AND (:maxRating IS NULL OR h.rating <= :maxRating) " +
            "AND (:minRooms IS NULL OR h.noOfRooms >= :minRooms) " +
            "AND (:maxRooms IS NULL OR h.noOfRooms <= :maxRooms)")
    Page<Hotel> findHotelsByCriteria(@Param("minRating") Double minRating,
                                     @Param("maxRating") Double maxRating,
                                     @Param("minRooms") Integer minRooms,
                                     @Param("maxRooms") Integer maxRooms,
                                     Pageable pageable);

}

