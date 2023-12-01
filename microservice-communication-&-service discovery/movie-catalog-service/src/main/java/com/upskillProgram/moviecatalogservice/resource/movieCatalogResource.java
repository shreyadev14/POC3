package com.upskillProgram.moviecatalogservice.resource;

import com.upskillProgram.moviecatalogservice.models.CatalogItem;
import com.upskillProgram.moviecatalogservice.models.Movie;
import com.upskillProgram.moviecatalogservice.models.Rating;
import com.upskillProgram.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class movieCatalogResource {
   @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;
    @GetMapping("/{userId}")

    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream().map(rating->{
            Movie movie = webClientBuilder.build().get().uri("http://localhost:8085/movies/"+ rating.getMovieId())
                    .retrieve().bodyToMono(Movie.class).block();
            // Movie movie=restTemplate.getForObject("http://localhost//8081/movies/" +rating.getMovieId() , Movie.class);
            return new CatalogItem(movie.getName() , movie.getDescription() ,rating.getRating());
        }).collect(Collectors.toList());

    }


}
