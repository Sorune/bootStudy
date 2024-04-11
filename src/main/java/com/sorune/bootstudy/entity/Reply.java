package com.sorune.bootstudy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rno;

    private String text;
    private String replyer;

    @ManyToOne
    private Board board;
}
