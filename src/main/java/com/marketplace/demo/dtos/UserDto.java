package com.marketplace.demo.dtos;

import com.marketplace.demo.models.Listing;
import com.marketplace.demo.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private String fullName;
    private String phoneNumber;
    private String email;

    private LocalDateTime createdAt;
    private List<ListingDto> listings = new ArrayList<>();

    public  UserDto(User user, Boolean isSelf){
        if(isSelf){
            this.fullName=user.getFullName();
            this.phoneNumber=user.getPhoneNumber();
            this.email= user.getEmail();
            this.setCreatedAt(user.getCreatedAt());
            this.setListings(user.getListings());
        }else{
            this.fullName=user.getFullName();
            this.phoneNumber=user.getPhoneNumber();
            this.setCreatedAt(user.getCreatedAt());
            this.setListings(user.getListings());
        }
    }

    private void setListings(List<Listing> userListings) {
        for (Listing listing : userListings) {
            this.listings.add(new ListingDto(listing));
        }
    }
}
