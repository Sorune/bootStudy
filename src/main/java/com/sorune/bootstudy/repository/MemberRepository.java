package com.sorune.bootstudy.repository;

import com.sorune.bootstudy.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
