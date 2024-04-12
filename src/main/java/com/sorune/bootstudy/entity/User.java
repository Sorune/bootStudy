package com.sorune.bootstudy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "m_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mid;

    private String email;
    private String password;
    private String nickName;
}
