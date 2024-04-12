package com.sorune.bootstudy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inum;

    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
