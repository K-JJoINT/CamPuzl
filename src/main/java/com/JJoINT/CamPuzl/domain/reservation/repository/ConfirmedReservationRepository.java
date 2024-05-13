package com.JJoINT.CamPuzl.domain.reservation.repository;

import com.JJoINT.CamPuzl.domain.reservation.domain.PubTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmedReservationRepository extends CrudRepository<PubTable, Long> {
}
