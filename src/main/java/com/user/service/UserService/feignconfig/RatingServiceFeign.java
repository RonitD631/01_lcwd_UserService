package com.user.service.UserService.feignconfig;

import com.user.service.UserService.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingServiceFeign {


    @GetMapping("/ratings/users/{userId}")
    public List<Rating> getRatings(@PathVariable String userId);
}
