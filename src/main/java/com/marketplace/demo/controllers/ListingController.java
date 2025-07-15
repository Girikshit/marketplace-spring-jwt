package com.marketplace.demo.controllers;

import com.marketplace.demo.dtos.CreateListingRequest;
import com.marketplace.demo.dtos.ListingDto;
import com.marketplace.demo.services.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/api/listing/")
public class ListingController {

    private ListingService listingService;


    @GetMapping()
    public ResponseEntity<List<ListingDto>> getAllListings() {
        List<ListingDto> listings = listingService.getAllListings();
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{id}")
    public ListingDto getListing(@PathVariable("id")Long id){
        return listingService.getListing(id);
    }

    @PostMapping()
    public ResponseEntity<?> createListing(@RequestBody CreateListingRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            listingService.createListing(request, email);
            return ResponseEntity.ok(Map.of("message", "Listing created."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message",("Error creating listing: " + e.getMessage())));
        }
    }



}

