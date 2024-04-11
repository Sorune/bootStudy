package com.sorune.bootstudy.service;

import com.sorune.bootstudy.dto.BoardDTO;
import com.sorune.bootstudy.dto.PageRequestDTO;
import com.sorune.bootstudy.dto.PageResultDTO;
import com.sorune.bootstudy.entity.Board;
import com.sorune.bootstudy.entity.Member;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO);

    BoardDTO read(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        Member member = Member.builder().email(boardDTO.getWriterEmail()).name(boardDTO.getWriterName()).build();

        Board board = new Board().builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDto(Board board, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .regDate(board.getRegDate())
                .modDate(board.getModdate())
                .replyCount(replyCount.intValue())
                .build();
        return boardDTO;
    }
}
