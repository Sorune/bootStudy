package com.sorune.bootstudy.controller;

import com.sorune.bootstudy.dto.ReviewDTO;
import com.sorune.bootstudy.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> findAll(@PathVariable Long mno) {
        log.info("findAll mno: {}", mno);
        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(mno);
        return ResponseEntity.ok(reviewDTOList);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("add review: {}", reviewDTO);
        Long reviewNum = reviewService.register(reviewDTO);
        return ResponseEntity.ok(reviewNum);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@RequestBody ReviewDTO reviewDTO, @PathVariable Long reviewnum) {
        log.info("modify review: {}", reviewDTO);
        reviewService.modify(reviewDTO);
        return ResponseEntity.ok(reviewnum);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> deleteReview(@PathVariable Long reviewnum) {
        log.info("delete review: {}", reviewnum);
        reviewService.remove(reviewnum);
        return ResponseEntity.ok(reviewnum);
    }
}
