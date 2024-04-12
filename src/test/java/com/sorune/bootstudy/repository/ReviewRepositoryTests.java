package com.sorune.bootstudy.repository;

import com.sorune.bootstudy.entity.Movie;
import com.sorune.bootstudy.entity.Review;
import com.sorune.bootstudy.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews(){
        IntStream.rangeClosed(1,200).forEach(i -> {
           Long mno =(long)(Math.random()*100)+1;

           Long mid = ((long)(Math.random()*100)+1);
            User user = User.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .user(user)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("이 영화에 대한 느낌..."+i)
                    .build();
            reviewRepository.save(movieReview);
        });
    }

    @Transactional
    @Commit
    @Test
    public void testGetMovieReviews(){
        Movie movie = Movie.builder().mno(50L).build();

        List<Review> movieReviews = reviewRepository.findByMovie(movie);

        movieReviews.forEach(System.out::println);
    }
}
