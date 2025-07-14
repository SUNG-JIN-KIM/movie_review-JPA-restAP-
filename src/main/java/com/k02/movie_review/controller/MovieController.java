package com.k02.movie_review.controller;

import com.k02.movie_review.dto.MovieDto;
import com.k02.movie_review.model.Movie;
import com.k02.movie_review.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;


    @GetMapping
    public ResponseEntity<Page<Movie>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Movie> inquiry =  movieService.getAll(pageable);

        return ResponseEntity.ok(inquiry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> get(@PathVariable Long id){
        Movie get =  movieService.getById(id);

        return ResponseEntity.ok(get);
    }

    @PostMapping
    public ResponseEntity<Movie> create(@Valid @RequestBody MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseYear(movieDto.getReleaseYear());

        Movie createdMovie = movieService.create(movie);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @Valid @RequestBody MovieDto movieDto){
        Movie movie = new Movie();

        movie.setTitle(movieDto.getTitle());
        movie.setReleaseYear(movieDto.getReleaseYear());

        Movie updated = movieService.update(id, movie);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
