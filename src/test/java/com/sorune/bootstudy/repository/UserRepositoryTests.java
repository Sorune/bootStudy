package com.sorune.bootstudy.repository;

import com.sorune.bootstudy.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertUsers(){
        IntStream.rangeClosed(1,100).forEach(i->{
           User user = User.builder()
                   .email("r"+i+"@sorune.com")
                   .password("1234")
                   .nickName("reviewer"+i)
                   .build();
           userRepository.save(user);
        });
    }

    @Test
    public void testDeleteUser(){
        Long mid = 1L;

        User user = User.builder().mid(mid).build();
        reviewRepository.deleteByUser(user);
        userRepository.deleteById(mid);
    }
}
