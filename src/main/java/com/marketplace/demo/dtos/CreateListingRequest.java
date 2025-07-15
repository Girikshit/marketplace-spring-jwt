package com.marketplace.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateListingRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private List<CreateListingImageRequest> images;

}
