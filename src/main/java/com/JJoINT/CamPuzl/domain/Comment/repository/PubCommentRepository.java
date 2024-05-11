package com.JJoINT.CamPuzl.domain.Comment.repository;


import com.JJoINT.CamPuzl.domain.Comment.domain.PubComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PubCommentRepository extends JpaRepository<PubComment, Long> {
    Page<PubComment> findByPubIdAndDeletedAtIsNull(Long pubId, Pageable pageable);
}
