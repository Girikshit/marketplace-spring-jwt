package com.marketplace.demo.repositories;

import com.marketplace.demo.models.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ListingImageRepository extends JpaRepository<ListingImage,Long> {
}
