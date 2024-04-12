package com.sorune.bootstudy.service;

import com.sorune.bootstudy.dto.ReviewDTO;
import com.sorune.bootstudy.entity.Movie;
import com.sorune.bootstudy.entity.Review;
import com.sorune.bootstudy.entity.User;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getListOfMovie(Long mno);
    Long register(ReviewDTO reviewDTO);
    void modify(ReviewDTO reviewDTO);
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .reviewnum(reviewDTO.getReviewnum())
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .user(User.builder().mid(reviewDTO.getMid()).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();
        return review;
    }

    default ReviewDTO entityToDto(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewnum(review.getReviewnum())
                .mno(review.getMovie().getMno())
                .mid(review.getUser().getMid())
                .nickname(review.getUser().getNickName())
                .email(review.getUser().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModdate())
                .build();
        return reviewDTO;
    }
}
