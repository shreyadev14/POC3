package com.upskillProgram.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.upskillProgram.moviecatalogservice.models.Rating;
import com.upskillProgram.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Properties;

@Service

public class UserRatingInfo {
    @Autowired
    RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
      commandProperties={ @HystrixProperty(name =" execution.isolation.thread.timeoutInMilliSeconds" ,value="2000"),
              @HystrixProperty(name ="circuitBreaker.requestVolumeThreshold" ,value="5"),
              @HystrixProperty(name ="circuitBreaker.errorThresholdPercentage" ,value="50"),
              @HystrixProperty(name ="circuitBreaker.sleepWindowInMilliseconds" ,value="5000")
              

    }
    )
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);
        return userRating;
    }

    public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        UserRating userRating= new  UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(new Rating("0",0)));
        return userRating;
    }
}
