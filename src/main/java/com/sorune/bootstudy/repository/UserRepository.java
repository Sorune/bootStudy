package com.sorune.bootstudy.repository;

import com.sorune.bootstudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
