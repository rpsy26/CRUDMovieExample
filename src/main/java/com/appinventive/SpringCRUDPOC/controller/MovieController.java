package com.appinventive.SpringCRUDPOC.controller;

import com.appinventive.SpringCRUDPOC.model.Movie;
import com.appinventive.SpringCRUDPOC.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepo;

    /**
     * Method use for accept reqest and create movie if 0.5 <= id >=5
     * @param movie
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/movie")
    public ResponseEntity<String> createMovie(@RequestBody Movie movie) {

        if (movie.getRating() <= 5 && movie.getRating() > 0.5) {
            try {
                movieRepo.save(movie);
                return new ResponseEntity("Successfully added movie " + movie.getTitle(), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Give rating between 0.5 and 5", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method use for get all movies from database
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/movie")
    public ResponseEntity getAllMovies() {
        List<Movie> movies = movieRepo.findAll();
        if (movies.size() > 0) {
            return new ResponseEntity(movies, HttpStatus.OK);
        } else {
            return new ResponseEntity("No movies found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method use to get movie by movie id
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/movie/{id}")
    public ResponseEntity getMovieByID(@PathVariable("id") String id) {
        Optional<Movie> movie = movieRepo.findById(id);
        if (movie.isPresent()) {
            return new ResponseEntity(movie.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity("No movies found from this ID", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method use to delete movie by ID
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/movie/{id}")
    public ResponseEntity deleteMovieById(@PathVariable("id") String id) {
        try {
            movieRepo.deleteById(id);
            return new ResponseEntity("Successfully deleted movie with id " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method use to update movie data by movie ID
     * @param id
     * @param newMovie
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/movie/{id}")
    public ResponseEntity updateById(@PathVariable("id") String id, @RequestBody Movie newMovie) {
        Optional<Movie> movieOptional = movieRepo.findById(id);
        if (movieOptional.isPresent()) {
            Movie movieToSave = movieOptional.get();
            movieToSave.setTitle(newMovie.getTitle());
            movieToSave.setCategory(newMovie.getCategory());
            movieToSave.setRating(newMovie.getRating());
            movieRepo.save(movieToSave);
            return new ResponseEntity("Updated Movie with id " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity("No Movie with id " + id + " found", HttpStatus.NOT_FOUND);
        }
    }
}
