package com.sorune.bootstudy.repository;

import com.sorune.bootstudy.entity.Board;
import com.sorune.bootstudy.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply(){
        IntStream.rangeClosed(1,300).forEach(i->{
            long bno = (long)(Math.random()*100)+1;

            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply..."+i)
                    .board(board)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    public void readReply1(){
        Optional<Reply> reply = replyRepository.findById(1L);
        Reply reply1 = reply.get();
        System.out.println(reply1);
        System.out.println(reply1.getBoard());
    }
}
