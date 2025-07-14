package com.k02.movie_review.service;

import com.k02.movie_review.model.Movie;
import com.k02.movie_review.model.Review;
import com.k02.movie_review.repository.MovieRepository;
import com.k02.movie_review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final MovieRepository movieRepository;

    public Page<Review> getByMovie(Long movieId, Pageable pageable){
        return reviewRepository.findByMovieId(movieId, pageable);
    }

    public Review create(Long movieId, Review review) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("영화가 없습니다: " + movieId));

        review.setMovie(movie);

        return reviewRepository.save(review);
    }

    public Review update(Long id, Review review) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("리뷰가 없습니다: " + id));

        existingReview.setReviewer(review.getReviewer());
        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());

        return reviewRepository.save(existingReview);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

}
