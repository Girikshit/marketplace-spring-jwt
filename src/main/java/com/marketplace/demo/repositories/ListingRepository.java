package com.marketplace.demo.repositories;

import com.marketplace.demo.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ListingRepository extends JpaRepository<Listing,Long> {
}
