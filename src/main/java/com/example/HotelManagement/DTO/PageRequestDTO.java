package com.example.HotelManagement.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class PageRequestDTO {

    private Integer pageNumber = 0;
    private Integer pageSize = 5;
    private String sortBy = "rating";
    private String sortDirection = "DESC";

    public Sort getSort() {
        return Sort.by(Sort.Direction.fromString(sortDirection.toUpperCase()), sortBy);
    }
}
