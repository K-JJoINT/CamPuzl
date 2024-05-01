package com.JJoINT.CamPuzl.domain.pub.repository;

import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PubRepository extends CrudRepository<Pub,Long> {
}
