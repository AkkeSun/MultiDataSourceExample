package com.example.multidatasourceexample.repository.primary;

import com.example.multidatasourceexample.domain.primary.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
