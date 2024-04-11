package com.sorune.bootstudy.repository;

import com.sorune.bootstudy.entity.Board;
import com.sorune.bootstudy.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying
    @Query("delete  from Reply r where r.board.bno = :bno")
    void deleteByBno(@Param("bno") long bno);

    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
