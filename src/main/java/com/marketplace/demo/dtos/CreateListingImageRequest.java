package com.marketplace.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateListingImageRequest {
    private String imageUrl;
    private Boolean isPrimary;

    public Long getListing_id() {
        return null;
    }
}
