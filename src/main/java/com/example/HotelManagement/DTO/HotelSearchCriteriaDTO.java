package com.example.HotelManagement.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelSearchCriteriaDTO {

    private Double minRating;
    private Double maxRating;
    private Integer minRooms;
    private Integer maxRooms;
    private String sortBy;
}
