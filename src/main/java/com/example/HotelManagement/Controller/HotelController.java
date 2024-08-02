package com.example.HotelManagement.Controller;

import com.example.HotelManagement.DTO.HotelDTO;
import com.example.HotelManagement.DTO.PageRequestDTO;
import com.example.HotelManagement.DTO.HotelSearchCriteriaDTO;
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
    public ResponseEntity<HotelDTO> saveOrUpdateHotel(@RequestBody HotelDTO hotelDTO) {
        try {
            if (hotelDTO.getId() != null) {
                if (!hotelService.existsById(hotelDTO.getId())) {
                    return ResponseEntity.notFound().build();
                }
                HotelDTO updatedHotel = hotelService.updateHotel(hotelDTO);
                return ResponseEntity.ok(updatedHotel);
            } else {
                HotelDTO newHotel = hotelService.addHotel(hotelDTO);
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
    public ResponseEntity<List<HotelDTO>> getHotelById(@RequestParam List<Long> hotelId) {
        List<HotelDTO> hotels = hotelService.getHotelById(hotelId);
        if (hotels == null || hotels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<HotelDTO>> listHotels(
            @ModelAttribute PageRequestDTO pageRequestDTO) {
        Page<HotelDTO> hotelsPage = hotelService.getAllHotels(pageRequestDTO);
        return ResponseEntity.ok(hotelsPage);
    }

    @GetMapping("/hotelslist")
    public ResponseEntity<Page<HotelDTO>> getHotels(
            @ModelAttribute HotelSearchCriteriaDTO searchCriteriaDTO,
            @ModelAttribute PageRequestDTO pageRequestDTO) {
        Page<HotelDTO> hotelsPage = hotelService.getHotelsByCriteria(searchCriteriaDTO, pageRequestDTO);
        return ResponseEntity.ok(hotelsPage);
    }
}
