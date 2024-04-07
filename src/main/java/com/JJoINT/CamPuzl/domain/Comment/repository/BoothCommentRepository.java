package com.JJoINT.CamPuzl.domain.Comment.repository;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.BoothCommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BoothCommentRepository extends JpaRepository<BoothComment, Long> {
    ArrayList<BoothComment> findByBoothId(Long id);
}
