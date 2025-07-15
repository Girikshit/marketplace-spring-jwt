package com.marketplace.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "listing_images")
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    @JsonBackReference
    private Listing listing;

    @Column(nullable = false)
    private String imageUrl;

    private Boolean isPrimary;

    public ListingImage(Listing listing, String imageUrl, Boolean isPrimary) {
        this.listing = listing;
        this.imageUrl = imageUrl;
        this.isPrimary = isPrimary;
    }
}
