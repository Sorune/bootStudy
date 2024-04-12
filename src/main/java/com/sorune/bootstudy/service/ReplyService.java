package com.sorune.bootstudy.service;

import com.sorune.bootstudy.dto.ReplyDTO;
import com.sorune.bootstudy.entity.Board;
import com.sorune.bootstudy.entity.Reply;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);
    List<ReplyDTO> getList(Long bno);
    void modify(ReplyDTO replyDTO);
    void delete(Long id);

    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();
        return Reply.builder()
               .rno(replyDTO.getRno())
               .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
    }
    default ReplyDTO entityToDto(Reply reply) {
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModdate())
                .build();
    }
}
