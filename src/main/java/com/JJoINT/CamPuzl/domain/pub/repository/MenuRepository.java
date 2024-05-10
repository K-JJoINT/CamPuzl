package com.JJoINT.CamPuzl.domain.pub.repository;

import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    Page<Menu> findByPubIdOrderByDivisionAsc(Long pubId, Pageable pageable);
    List<Menu> findByPubId(Long pubId);
}
