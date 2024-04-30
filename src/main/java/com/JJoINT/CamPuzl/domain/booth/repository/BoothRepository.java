package com.JJoINT.CamPuzl.domain.booth.repository;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Long> {
    //커밋용 주석
    Optional<Booth> findByTentNum(TentNum tentNum);

    Long findById(BoothDTO boothDTO);
}
