package com.example.HotelManagement.Model;

import jakarta.persistence.*;  // Imports JPA annotations
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Entity //jpa annotation that specify class is an entity.
@Table(name = "hotelTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "address")
    private String address;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "contactDetails")
    private String contactDetails;

    @Column(name="noOfRooms")
    private Integer noOfRooms;

}
