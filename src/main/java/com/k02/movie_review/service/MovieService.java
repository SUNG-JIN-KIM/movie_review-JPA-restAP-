package com.k02.movie_review.service;

import com.k02.movie_review.model.Movie;
import com.k02.movie_review.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;

    public Page<Movie> getAll(Pageable pageable){
        return  movieRepository.findAll(pageable);
    }

    public Movie getById(Long id){
        return movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("영화가 없습니다." + id));
    }

    public Movie create(Movie movie){
        return movieRepository.save(movie);
    }
}
