package com.JJoINT.CamPuzl.domain.pub.repository;

import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PubRepository extends JpaRepository<Pub, Long> {

}
