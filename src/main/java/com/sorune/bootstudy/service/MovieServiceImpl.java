package com.sorune.bootstudy.service;

import com.sorune.bootstudy.dto.MovieDTO;
import com.sorune.bootstudy.dto.PageRequestDTO;
import com.sorune.bootstudy.dto.PageResultDTO;
import com.sorune.bootstudy.entity.Movie;
import com.sorune.bootstudy.entity.MovieImage;
import com.sorune.bootstudy.repository.MovieImageRepository;
import com.sorune.bootstudy.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;

    private final MovieImageRepository movieImageRepository;
    @Override
    public Long register(MovieDTO movieDTO) {
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("movieImageList");

        movieRepository.save(movie);
        movieImageList.forEach(movieImageRepository::save);
        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);
        log.info(result);
        result.forEach(arr->System.out.println(Arrays.toString(arr)));
        Function<Object[], MovieDTO> fn = (arr -> entityToDTO(
                (Movie)arr[0] ,
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3])
        );
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public MovieDTO get(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr->{
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entityToDTO(movie, movieImageList, avg, reviewCnt);
    }
}
