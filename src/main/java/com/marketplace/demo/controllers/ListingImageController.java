package com.marketplace.demo.controllers;

import com.marketplace.demo.dtos.CreateListingImageRequest;
import com.marketplace.demo.models.ListingImage;
import com.marketplace.demo.services.ListingImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/listingimage/")
public class ListingImageController {
    
    private ListingImageService listingImageService;

    @GetMapping
    public List<ListingImage> getAll(){
        return listingImageService.getAll();
    }

    @PostMapping()
    public ResponseEntity<?> createListing(@RequestBody CreateListingImageRequest request) {
        try {
            listingImageService.createListing(request);
            return ResponseEntity.ok("Image added.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message",("Error creating listing image: " + e.getMessage())));
        }
    }
}
