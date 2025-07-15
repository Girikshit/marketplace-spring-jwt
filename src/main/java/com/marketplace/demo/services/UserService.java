package com.marketplace.demo.services;

import com.marketplace.demo.configs.UsernameNotValidException;
import com.marketplace.demo.dtos.UserDto;
import com.marketplace.demo.models.User;
import com.marketplace.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDto getPublicUser(Long id) {
        Optional<User> UserOptional = userRepository.findById(id);
        if(UserOptional.isEmpty()){
            throw new EntityNotFoundException("Cannot find User with id: "+ id );
        }
        return new UserDto(UserOptional.get(), false);
    }


    public void addUser(User user)  {
        if(user.getFullName()==null){
            System.out.println("Invalid username detected");
            throw new UsernameNotValidException();
        } else if (user.getFullName().isBlank()) {
            System.out.println("Invalid username detected");
            throw new UsernameNotValidException();
        }
        System.out.println("full name" + user.getFullName());
        userRepository.save(user);
    }

    public UserDto getMyUser(String email) {
        Optional<User> UserOptional = userRepository.findByEmail(email);
        if(UserOptional.isEmpty()){
            throw new EntityNotFoundException("Cannot find User with email: "+ email );
        }
        return new UserDto(UserOptional.get(), true);

    }
}
