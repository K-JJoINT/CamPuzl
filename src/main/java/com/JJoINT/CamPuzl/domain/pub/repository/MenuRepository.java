package com.JJoINT.CamPuzl.domain.pub.repository;

import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<Menu,Long> {
}
