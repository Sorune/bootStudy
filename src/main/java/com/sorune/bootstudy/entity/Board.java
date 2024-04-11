package com.sorune.bootstudy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bno;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 1500,nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    public void changeTitle(String title) {this.title = title;}
    public void changeContent(String content) {this.content = content;}
}
