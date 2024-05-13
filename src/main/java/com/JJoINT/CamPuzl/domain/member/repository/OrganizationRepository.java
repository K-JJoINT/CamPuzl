package com.JJoINT.CamPuzl.domain.member.repository;

import com.JJoINT.CamPuzl.domain.member.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByRepresentativeId (Long representativeId);
}
