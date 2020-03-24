package co.simplon.cinemaapp.service;

import co.simplon.cinemaapp.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    /**
     * Method that lists all existing movies from database.
     * @return the list of all existing movies.
     */
    List<Movie> findAllMovies();

    /**
     * Method that creates a new movie.
     * @param newMovie the new movie to create.
     * @return the new created movie.
     */
    Movie createNewMovie(Movie newMovie);
}
