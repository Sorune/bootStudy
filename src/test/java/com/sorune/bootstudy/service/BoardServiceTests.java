package com.sorune.bootstudy.service;

import com.sorune.bootstudy.dto.BoardDTO;
import com.sorune.bootstudy.dto.PageRequestDTO;
import com.sorune.bootstudy.dto.PageResultDTO;
import com.sorune.bootstudy.entity.Board;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO boardDTO = new BoardDTO().builder()
                .title("Sample Guest Book")
                .content("Sample Guest Book")
                /*.writer("user1")*/
                .build();
        System.out.println(boardService.register(boardDTO));
    }

    @Test
    public void testRegister2(){
        BoardDTO boardDTO = BoardDTO.builder()
                .title("Test")
                .content("Test")
                .writerEmail("user55@aaa.com")
                .build();
        Long bno = boardService.register(boardDTO);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testRead(){
        BoardDTO boardDTO = boardService.read(1L);
        System.out.println(boardDTO);
    }

    @Transactional
    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("change title")
                .content("change content")
                .build();
        boardService.modify(boardDTO);
    }

    @Test
    public void testSearch(){

    }
}
