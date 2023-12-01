package com.upskillingProgram.ratingsdataservice.resource;

import com.upskillingProgram.ratingsdataservice.model.Rating;
import com.upskillingProgram.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratingsdata")

public class RatingsResource {
    @GetMapping("/movies/{movieId}")
    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping("/user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;

    }
}
