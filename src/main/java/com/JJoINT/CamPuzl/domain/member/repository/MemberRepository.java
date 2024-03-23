package com.JJoINT.CamPuzl.domain.member.repository;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member,Long> {
}
