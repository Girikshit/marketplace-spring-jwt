package com.marketplace.demo.services;

import com.marketplace.demo.dtos.CreateListingImageRequest;
import com.marketplace.demo.dtos.CreateListingRequest;
import com.marketplace.demo.dtos.ListingDto;
import com.marketplace.demo.models.Listing;
import com.marketplace.demo.models.ListingImage;
import com.marketplace.demo.models.User;
import com.marketplace.demo.repositories.ListingImageRepository;
import com.marketplace.demo.repositories.ListingRepository;
import com.marketplace.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ListingService {
    private ListingRepository listingRepository;
    private UserRepository userRepository;
    private ListingImageRepository listingImageRepository;

    public List<ListingDto> getAllListings() {
        List<Listing> listingList= listingRepository.findAll();
        return listingList.stream()
                .map(ListingDto::new) // lambda function
                .collect(Collectors.toList());
    }

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public void createListing(CreateListingRequest request, String email) {
        User seller = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Listing listing = new Listing(
                seller,
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getLocation()
        );
        listingRepository.save(listing);

        List<CreateListingImageRequest> images= request.getImages();
        if(!images.isEmpty()){
            for(CreateListingImageRequest imageRequest:images){
                ListingImage image= new ListingImage(listing,imageRequest.getImageUrl(), imageRequest.getIsPrimary());
                listingImageRepository.save(image);
            }
        }

    }

    public ListingDto getListing(Long id) {
        Listing listing= listingRepository.findById(id).
                orElseThrow(()->new EntityNotFoundException("Listing not found: "+id));
        return new ListingDto(listing);
    }
}
