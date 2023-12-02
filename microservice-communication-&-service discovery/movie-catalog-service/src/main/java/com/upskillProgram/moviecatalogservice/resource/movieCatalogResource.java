package com.upskillProgram.moviecatalogservice.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.upskillProgram.moviecatalogservice.models.CatalogItem;
import com.upskillProgram.moviecatalogservice.models.Movie;
import com.upskillProgram.moviecatalogservice.models.Rating;
import com.upskillProgram.moviecatalogservice.models.UserRating;
import com.upskillProgram.moviecatalogservice.service.MovieInfo;
import com.upskillProgram.moviecatalogservice.service.UserRatingInfo;
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
   MovieInfo movieInfo;

   @Autowired
   UserRatingInfo userRatingInfo;

    @Autowired
    WebClient.Builder webClientBuilder;
    @GetMapping("/{userId}")
    //@HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

       UserRating userRating=userRatingInfo.getUserRating(userId);
        return userRating.getRatings().stream().map(rating->
            /*Movie movie = webClientBuilder.build().get().uri("http://localhost:8085/movies/"+ rating.getMovieId())
                    .retrieve().bodyToMono(Movie.class).block();*/

                movieInfo.getCatalogItem(rating)
        ).collect(Collectors.toList());

    }




    /*public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
    return Arrays.asList(new CatalogItem("pride & prejudice","Period Romance",5));
    }*/

}
