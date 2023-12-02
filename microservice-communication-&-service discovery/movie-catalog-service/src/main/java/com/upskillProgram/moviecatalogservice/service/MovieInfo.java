package com.upskillProgram.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.upskillProgram.moviecatalogservice.models.CatalogItem;
import com.upskillProgram.moviecatalogservice.models.Movie;
import com.upskillProgram.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class MovieInfo {
    @Autowired
    RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem"
   /* threadPoolKey = "movieInfoPool",
    threadPoolProperties = {@HystrixProperty(name="coreSize",value="20"),
            @HystrixProperty(name="maxQueueSize",value="10")

    }*/)
    public CatalogItem getCatalogItem(Rating rating){
        Movie movie=restTemplate.getForObject("http://localhost//8081/movies/" +rating.getMovieId() , Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie Not Found","Movie Service down",rating.getRating());
    }
}
