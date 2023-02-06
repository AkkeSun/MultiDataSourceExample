package com.example.multidatasourceexample;

import com.example.multidatasourceexample.config.SecondaryDataSourceConfig;
import com.example.multidatasourceexample.domain.primary.Member;
import com.example.multidatasourceexample.domain.secondary.SecondaryMember;
import com.example.multidatasourceexample.repository.primary.MemberRepository;
import com.example.multidatasourceexample.repository.secondary.SecondaryMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MultiDataSourceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SecondaryMemberRepository secondaryMemberRepository;


    @Test
    public void memberRepositoryTest(){
        for (Member member : memberRepository.findAll()) {
            log.info("Member id : {} || Member Name : {}", member.getId(), member.getUserName());
        }
    }

    @Test
    public void secondaryMemberRepositoryTest(){
        for (SecondaryMember member : secondaryMemberRepository.findAll()) {
            log.info("Member id : {} || Member Name : {}", member.getId(), member.getName());
        }
    }

}
