package com.example.multidatasourceexample.repository.secondary;

import com.example.multidatasourceexample.domain.secondary.SecondaryMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryMemberRepository extends JpaRepository<SecondaryMember, Integer> {

}
