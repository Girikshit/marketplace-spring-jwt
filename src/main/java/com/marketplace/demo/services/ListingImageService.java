package com.marketplace.demo.services;


import com.marketplace.demo.dtos.CreateListingImageRequest;
import com.marketplace.demo.models.Listing;
import com.marketplace.demo.models.ListingImage;
import com.marketplace.demo.repositories.ListingImageRepository;
import com.marketplace.demo.repositories.ListingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ListingImageService {

    private ListingImageRepository listingImageRepository;
    private ListingRepository listingRepository;

    public List<ListingImage> getAll() {
        return listingImageRepository.findAll();
    }

    public void createListing(CreateListingImageRequest request) {
        Listing listing = listingRepository.findById(request.getListing_id()).
                orElseThrow(() -> new EntityNotFoundException("Listing not found"));
        ListingImage listingImage= new ListingImage(listing,
                request.getImageUrl(),
                request.getIsPrimary());

        listingImageRepository.save(listingImage);
    }
}
