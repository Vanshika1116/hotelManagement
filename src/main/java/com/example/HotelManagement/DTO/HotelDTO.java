package com.example.HotelManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    private String hotelName;
    private String address;
    private Integer rating;
    private String contactDetails;
    private Integer noOfRooms;
}
