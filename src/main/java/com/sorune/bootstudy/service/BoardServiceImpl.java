package com.sorune.bootstudy.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sorune.bootstudy.dto.BoardDTO;
import com.sorune.bootstudy.dto.PageRequestDTO;
import com.sorune.bootstudy.dto.PageResultDTO;
import com.sorune.bootstudy.entity.Board;
import com.sorune.bootstudy.entity.Member;
import com.sorune.bootstudy.entity.QBoard;
import com.sorune.bootstudy.repository.BoardRepository;
import com.sorune.bootstudy.repository.MemberRepository;
import com.sorune.bootstudy.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO boardDTO) {
        log.info("DTO-------");
        log.info(boardDTO);

        Board board = dtoToEntity(boardDTO);
        log.info(board);

        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO) {
        log.info(requestDTO);
        Function<Object[], BoardDTO> fn = (en-> entityToDto((Board)en[0],(Member)en[1],(Long)en[2]));
        //Page<Object[]> result = boardRepository.getBoardWithReplyCount(requestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepository.searchPage(requestDTO.getType(), requestDTO.getKeyword(), requestDTO.getPageable(Sort.by("bno")));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public BoardDTO read(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[])result;
        return entityToDto((Board) arr[0],(Member) arr[1],(Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);

        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
    Board board = boardRepository.getOne(boardDTO.getBno());
    board.changeTitle(boardDTO.getTitle());
    board.changeContent(boardDTO.getContent());
    boardRepository.save(board);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type =requestDTO.getType();
        BooleanBuilder bool = new BooleanBuilder();

        QBoard qBoard = QBoard.board;
        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qBoard.bno.gt(0L); // bno > 0

        bool.and(expression);
        if(type == null || type.trim().length() == 0) {
            return bool;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")){
            conditionBuilder.or(qBoard.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qBoard.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qBoard.writer.name.contains(keyword));
        }

        bool.and(conditionBuilder);
        return bool;

    }
}
