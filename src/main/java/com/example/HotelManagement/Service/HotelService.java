package com.example.HotelManagement.Service;

import com.example.HotelManagement.DTO.HotelDTO;
import com.example.HotelManagement.DTO.PageRequestDTO;
import com.example.HotelManagement.DTO.HotelSearchCriteriaDTO;
import com.example.HotelManagement.Model.Hotel;
import com.example.HotelManagement.Repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public boolean existsById(Long hotelId) {
        return hotelRepository.existsById(hotelId);
    }

    public HotelDTO addHotel(HotelDTO hotelDTO) {
        Hotel hotel = convertToEntity(hotelDTO);
        Hotel savedHotel = hotelRepository.save(hotel);
        return convertToDTO(savedHotel);
    }

    public HotelDTO updateHotel(HotelDTO hotelDTO) {
        Hotel hotel = convertToEntity(hotelDTO);
        Hotel updatedHotel = hotelRepository.save(hotel);
        return convertToDTO(updatedHotel);
    }

    public boolean deleteHotel(Long hotelId) {
        try {
            if (!hotelRepository.existsById(hotelId)) {
                return false;
            }
            hotelRepository.deleteById(hotelId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public List<HotelDTO> getHotelById(List<Long> ids) {
        try {
            List<Hotel> hotels = hotelRepository.findAllById(ids);
            if (hotels.isEmpty()) {
                return null;
            }
            return hotels.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public Page<HotelDTO> getAllHotels(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), pageRequestDTO.getSort());
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        return hotelPage.map(this::convertToDTO);
    }

    public Page<HotelDTO> getHotelsByCriteria(HotelSearchCriteriaDTO searchCriteria, PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.ASC, searchCriteria.getSortBy() != null ? searchCriteria.getSortBy() : "id");
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        Page<Hotel> hotelPage = hotelRepository.findHotelsByCriteria(searchCriteria.getMinRating(), searchCriteria.getMaxRating(), searchCriteria.getMinRooms(), searchCriteria.getMaxRooms(), pageable);
        return hotelPage.map(this::convertToDTO);
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        return new HotelDTO(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getAddress(),
                hotel.getRating(),
                hotel.getContactDetails(),
                hotel.getNoOfRooms()
        );
    }

    private Hotel convertToEntity(HotelDTO hotelDTO) {
        return new Hotel(
                hotelDTO.getId(),
                hotelDTO.getHotelName(),
                hotelDTO.getAddress(),
                hotelDTO.getRating(),
                hotelDTO.getContactDetails(),
                hotelDTO.getNoOfRooms()
        );
    }
}
