package com.JJoINT.CamPuzl.domain.reservation.repository;

import com.JJoINT.CamPuzl.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation,Long> {

    @Query("SELECT MAX(r.waitingNum) FROM Reservation r WHERE r.pub.id = :pubId")
    int findLastWaitingNum(@Param("pubId") Long pubId);
}
