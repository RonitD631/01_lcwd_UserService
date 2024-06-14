package com.user.service.UserService.serviceImpl;

import com.user.service.UserService.entity.Hotel;
import com.user.service.UserService.entity.Rating;
import com.user.service.UserService.entity.User;
import com.user.service.UserService.exception.ResourceNotFoundException;
import com.user.service.UserService.feignconfig.RatingServiceFeign;
import com.user.service.UserService.repository.UserRepository;
import com.user.service.UserService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RatingServiceFeign ratingServiceFeign;

     private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user) {
      String uuid=  UUID.randomUUID().toString();
      user.setUserId(uuid);
        return userRepository.save(user);
    }

    @Override
    public User getSingleUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //GET SINGLE USER WITH USER DETAILS AND RATINGS &HOTEL DETAILS ON WHICH THAT THE USER HAS MADE
    @Override
    public User getSingleUserWithRatingAndHotel(String userId) {
        //fetching user from user repository
       User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found " + userId));

       //fetching the ratings made that user by communicating with RATING MICRO-SERVICE api
        //http://localhost:8083/ratings/users/b93a1c24-f64a-462b-a81c-13566d595a46
        //ITS STATIC NOT DYNAMIC CZ WE ARE GIVING THE USERID AS WELL AS THE URL PORTNO IS ALSO HARD CODED

//        ArrayList<Rating> ratingsOfUser= restTemplate
//                 .getForObject("http://localhost:8083/ratings/users/b93a1c24-f64a-462b-a81c-13566d595a46", ArrayList.class);

        //MAKING IT DYNAMIC
        Rating[] ratingsOfUser= restTemplate
                .getForObject("http://RATING-SERVICE/ratings/users/" +user.getUserId(), Rating [].class);

        logger.info("{}", ratingsOfUser);

      List<Rating> ratings=  Arrays.stream(ratingsOfUser).toList();

      List<Rating> ratingList =  ratings.stream().map(
                 rating -> {
                     //api call to HOTEL SERVICE to fetch the hotels on which ratings given by the user
                     //http://localhost:8082/hotels/2d1e26ae-bc6a-41be-816d-4ec4d2804259
                     // ITS STATIC

//                  ResponseEntity<Hotel> forEntity= restTemplate.getForEntity
//                             ("http://localhost:8082/hotels/2d1e26ae-bc6a-41be-816d-4ec4d2804259", Hotel.class);
//                   Hotel hotel=  forEntity.getBody();

                     //MAKING IT DYNAMIC
                     ResponseEntity<Hotel> forEntity= restTemplate.getForEntity
                             ("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                   Hotel hotel=  forEntity.getBody();

                        //set the hotel to rating
                          rating.setHotel(hotel);

                       //return rating with the hotel
                       return rating;
                 }
         ).collect(Collectors.toList());

      //setting the rating list to user and returning
        user.setRatings(ratingList);

       return user;
    }

    @Override
    public User getSingleUserWithRating(String userId) {
       User user = userRepository .findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found" + userId));

        //fetching the ratings made that user by communicating with RATING MICRO-SERVICE api
        //http://localhost:8083/ratings/users/b93a1c24-f64a-462b-a81c-13566d595a46
        //ITS STATIC NOT DYNAMIC CZ WE ARE GIVING THE USERID AS WELL AS THE URL PORTNO IS ALSO HARD CODED

//        ArrayList<Rating> ratingsOfUser= restTemplate
//                 .getForObject("http://localhost:8083/ratings/users/b93a1c24-f64a-462b-a81c-13566d595a46", ArrayList.class);

        //MAKING IT DYNAMIC
     //   ArrayList<Rating> ratingsOfUser =restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" +user.getUserId(), ArrayList.class);


        //NOW,USING  FEIGN CLIENT INSTEAD OF REST-TEMPLATE
        ArrayList<Rating> ratingsOfUser= (ArrayList<Rating>) ratingServiceFeign.getRatings(userId);
          user.setRatings(ratingsOfUser);

          return user;

    }
}
