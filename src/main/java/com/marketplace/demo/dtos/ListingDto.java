package com.marketplace.demo.dtos;

import com.marketplace.demo.models.Listing;
import com.marketplace.demo.models.ListingImage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ListingDto {
    private Long id;
    private Long sellerId;
    private String sellerName;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private LocalDateTime createdTime;
    private Boolean isActive;
    private List<ListingImage> images;

    public ListingDto(Listing listing){
        this.setId(listing.getId());
        this.setSellerId(listing.getSeller().getId());
        this.setSellerName(listing.getSeller().getFullName());
        this.setTitle(listing.getTitle());
        this.setDescription(listing.getDescription());
        this.setPrice(listing.getPrice());
        this.setLocation(listing.getLocation());
        this.setIsActive(listing.getIsActive());
        this.setCreatedTime(listing.getCreatedAt());
        this.setImages(listing.getImages());

    }


}
