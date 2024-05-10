package com.JJoINT.CamPuzl.domain.booth.repository;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Long> {
    List<Booth> findAllByDeletedAtIsNull();

    Optional<Booth> findByIdAndDeletedAtIsNull(Long boothId);
}
