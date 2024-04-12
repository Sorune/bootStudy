package com.sorune.bootstudy.service;

import com.sorune.bootstudy.dto.MovieDTO;
import com.sorune.bootstudy.dto.MovieImageDTO;
import com.sorune.bootstudy.dto.PageRequestDTO;
import com.sorune.bootstudy.dto.PageResultDTO;
import com.sorune.bootstudy.entity.Movie;
import com.sorune.bootstudy.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDTO movieDTO);
    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    MovieDTO get(Long mno);

    default MovieDTO entityToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt){
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModdate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder().imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;

    }

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
        Map<String, Object> map = new HashMap<>();
        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();
        map.put("movie", movie);
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        if(imageDTOList != null && !imageDTOList.isEmpty()) {
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            map.put("movieImageList", movieImageList);
        }
        return map;
    }

}
