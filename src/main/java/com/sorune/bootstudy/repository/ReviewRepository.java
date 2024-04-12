package com.sorune.bootstudy.repository;

import com.sorune.bootstudy.entity.Movie;
import com.sorune.bootstudy.entity.Review;
import com.sorune.bootstudy.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"user"},type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.user = :user")
    void deleteByUser(User user);
}
