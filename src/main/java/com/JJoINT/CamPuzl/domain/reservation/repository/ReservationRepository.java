package com.JJoINT.CamPuzl.domain.reservation.repository;

import com.JJoINT.CamPuzl.domain.reservation.domain.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation,Long> {
}
