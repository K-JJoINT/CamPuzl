package com.JJoINT.CamPuzl.domain.reservation.repository;

import com.JJoINT.CamPuzl.domain.reservation.domain.ConfirmedReservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmedReservationRepository extends CrudRepository<ConfirmedReservation,Long> {
}
